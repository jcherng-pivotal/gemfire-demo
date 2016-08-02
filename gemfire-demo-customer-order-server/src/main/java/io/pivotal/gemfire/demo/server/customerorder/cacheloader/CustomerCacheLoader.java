package io.pivotal.gemfire.demo.server.customerorder.cacheloader;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.gemfire.LazyWiringDeclarableSupport;

import com.gemstone.gemfire.cache.CacheLoader;
import com.gemstone.gemfire.cache.CacheLoaderException;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.LoaderHelper;
import com.gemstone.gemfire.cache.Region;

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

public class CustomerCacheLoader extends LazyWiringDeclarableSupport
		implements CacheLoader<CustomerKey, Customer>, Declarable {

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
	public Customer load(LoaderHelper<CustomerKey, Customer> helper) throws CacheLoaderException {
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

					itemRegion.put(itemKey, item);
				}
				customerOrder.setItemSet(itemSet);

				customerOrderRegion.put(customerOrderKey, customerOrder);
			}
		}
		return customer;
	}

}
