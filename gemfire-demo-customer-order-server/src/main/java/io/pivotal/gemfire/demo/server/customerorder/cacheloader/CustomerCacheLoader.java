package io.pivotal.gemfire.demo.server.customerorder.cacheloader;

import io.pivotal.gemfire.demo.model.gf.key.CustomerKey;
import io.pivotal.gemfire.demo.model.gf.pdx.Customer;
import org.apache.geode.cache.CacheLoader;
import org.apache.geode.cache.CacheLoaderException;
import org.apache.geode.cache.LoaderHelper;
import org.apache.geode.pdx.PdxInstance;

public class CustomerCacheLoader implements CacheLoader<CustomerKey, Customer> {
	
//	@Autowired
//	private ICustomerOrderDBService customerOrderDBService;
//
//	@Autowired
//	private CustomerRepository customerRepository;

//	@Autowired
//	private CustomerOrderRepository customerOrderRepository;
//
//	@Resource(name = "customer-order")
//	private Region<CustomerOrderKey, CustomerOrder> customerOrderRegion;
//
//	@Resource(name = "item")
//	private Region<ItemKey, Item> itemRegion;

	@Override
	public void close() {
		// NOOPm
	}

	@Override
	public Customer load(LoaderHelper<CustomerKey, Customer> helper) throws CacheLoaderException {
//		customerOrderDBService.loadDB();
//		CustomerKey customerKey = helper.getKey();
//		CustomerEntity customerEntity = customerRepository.findOne(customerKey.getId());
//		Customer customer = null;
//		if (customerEntity != null) {
//			customer = new Customer();
//			customer.setName(customerEntity.getName());
//			Set<CustomerOrderEntity> customerOrderEntitySet = customerOrderRepository.findByCustomer(customerEntity);
//			for (CustomerOrderEntity customerOrderEntity : customerOrderEntitySet) {
//				CustomerOrderKey customerOrderKey = new CustomerOrderKey(customerOrderEntity.getId(), customerKey);
//				CustomerOrder customerOrder = new CustomerOrder();
//				customerOrder.setShippingAddress(customerOrderEntity.getShippingAddress());
//				customerOrder.setOrderDate(customerOrderEntity.getOrderDate().getTime());
//
//				Set<String> itemSet = new HashSet<String>();
//				for (ItemEntity itemEntity : customerOrderEntity.getItemSet()) {
//					ItemKey itemKey = new ItemKey(itemEntity.getId());
//					itemSet.add(itemKey.getId());
//					Item item = new Item();
//					item.setName(itemEntity.getName());
//					item.setDescription(itemEntity.getDescription());
//					item.setPrice(itemEntity.getPrice().toString());
//
//					System.out.println("itemKey: " + itemKey + " item: " + item);
//					itemRegion.put(itemKey, item);
//				}
//				customerOrder.setItemSet(itemSet);
//
//				System.out.println("customerOrderKey: " + customerOrderKey + " customerOrder: " + customerOrder);
//				customerOrderRegion.put(customerOrderKey, customerOrder);
//			}
//		}
//		System.out.println("customerKey: " + customerKey + " customer: " + customer);

		Customer customer = new Customer();
		customer.setName("customer1");
		return customer;
	}

}
