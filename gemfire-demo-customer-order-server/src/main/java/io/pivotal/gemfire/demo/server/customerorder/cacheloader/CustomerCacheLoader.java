package io.pivotal.gemfire.demo.server.customerorder.cacheloader;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.geode.cache.CacheLoader;
import org.apache.geode.cache.CacheLoaderException;
import org.apache.geode.cache.LoaderHelper;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import io.pivotal.gemfire.demo.db.repository.CustomerOrderRepository;
import io.pivotal.gemfire.demo.db.repository.CustomerRepository;
import io.pivotal.gemfire.demo.model.gf.key.CustomerKey;
import io.pivotal.gemfire.demo.model.gf.key.CustomerOrderKey;
import io.pivotal.gemfire.demo.model.gf.key.ItemKey;
import io.pivotal.gemfire.demo.model.gf.pdx.Customer;
import io.pivotal.gemfire.demo.model.gf.pdx.CustomerOrder;
import io.pivotal.gemfire.demo.model.gf.pdx.Item;
import io.pivotal.gemfire.demo.model.orm.CustomerEntity;
import io.pivotal.gemfire.demo.model.orm.CustomerOrderEntity;
import io.pivotal.gemfire.demo.model.orm.ItemEntity;
import io.pivotal.gemfire.demo.server.customerorder.db.ICustomerOrderDBService;

public class CustomerCacheLoader implements CacheLoader<CustomerKey, Customer> {
	
	@Autowired
	private ICustomerOrderDBService customerOrderDBService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerOrderRepository customerOrderRepository;

	@Resource(name = "customer-order")
	private Region<CustomerOrderKey, CustomerOrder> customerOrderRegion;

	@Resource(name = "item")
	private Region<ItemKey, Item> itemRegion;

	@Override
	public void close() {
		// NOOP
	}

	@Override
	@Transactional
	public Customer load(LoaderHelper<CustomerKey, Customer> helper) throws CacheLoaderException {
		customerOrderDBService.loadDB();
		CustomerKey customerKey = helper.getKey();
		CustomerEntity customerEntity = customerRepository.findOne(customerKey.getId());
		Customer customer = null;
		if (customerEntity != null) {
			customer = new Customer();
			customer.setName(customerEntity.getName());
			Set<CustomerOrderEntity> customerOrderEntitySet = customerOrderRepository.findByCustomer(customerEntity);
			for (CustomerOrderEntity customerOrderEntity : customerOrderEntitySet) {
				CustomerOrderKey customerOrderKey = new CustomerOrderKey(customerOrderEntity.getId(), customerKey);
				CustomerOrder customerOrder = new CustomerOrder();
				customerOrder.setShippingAddress(customerOrderEntity.getShippingAddress());
				customerOrder.setOrderDate(customerOrderEntity.getOrderDate().getTime());

				Set<String> itemSet = new HashSet<String>();
				for (ItemEntity itemEntity : customerOrderEntity.getItemSet()) {
					ItemKey itemKey = new ItemKey(itemEntity.getId());
					itemSet.add(itemKey.getId());
					Item item = new Item();
					item.setName(itemEntity.getName());
					item.setDescription(itemEntity.getDescription());
					item.setPrice(itemEntity.getPrice().toString());

					System.out.println("itemKey: " + itemKey + " item: " + item);
					itemRegion.put(itemKey, item);
				}
				customerOrder.setItemSet(itemSet);

				System.out.println("customerOrderKey: " + customerOrderKey + " customerOrder: " + customerOrder);
				customerOrderRegion.put(customerOrderKey, customerOrder);
			}
		}
		System.out.println("customerKey: " + customerKey + " customer: " + customer);
		return customer;
	}

}
