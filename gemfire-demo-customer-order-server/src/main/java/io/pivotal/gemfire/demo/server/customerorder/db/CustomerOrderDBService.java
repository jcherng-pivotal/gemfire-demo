package io.pivotal.gemfire.demo.server.customerorder.db;

import org.springframework.stereotype.Component;

@Component
public class CustomerOrderDBService implements ICustomerOrderDBService {
	@Override
	public void loadDB() {

	}

//	@Autowired
//	private CustomerRepository customerRepository;
//
//	@Autowired
//	private ItemRepository itemRepository;
//
//	@Autowired
//	private CustomerOrderRepository customerOrderRepository;
//
//	private boolean isDBLoaded = false;
//
//	@Override
//	@Transactional
//	public void loadDB() {
//		synchronized (this) {
//			if (!isDBLoaded) {
//				CustomerEntity customer1 = new CustomerEntity("customer1");
//				customer1.setName("Krikor Garegin");
//				customerRepository.save(customer1);
//
//				CustomerEntity customer2 = new CustomerEntity("customer2");
//				customer2.setName("Ararat Avetis");
//				customerRepository.save(customer2);
//
//				ItemEntity pencil = new ItemEntity("pencil");
//				pencil.setName("pencil");
//				pencil.setDescription("pencil decription");
//				pencil.setPrice(new BigDecimal("0.99"));
//				itemRepository.save(pencil);
//
//				ItemEntity pen = new ItemEntity("pen");
//				pen.setName("pen");
//				pen.setDescription("pen description");
//				pen.setPrice(new BigDecimal("1.49"));
//				itemRepository.save(pen);
//
//				ItemEntity paper = new ItemEntity("paper");
//				paper.setName("pen");
//				paper.setDescription("paper description");
//				paper.setPrice(new BigDecimal("0.10"));
//				itemRepository.save(paper);
//
//				Set<ItemEntity> itemSet = new HashSet<ItemEntity>();
//				itemSet.add(pen);
//				itemSet.add(paper);
//				CustomerOrderEntity customerOrder = new CustomerOrderEntity("order1", customer1, "address1", new Date(),
//						itemSet);
//				customerOrderRepository.save(customerOrder);
//
//				itemSet = new HashSet<ItemEntity>();
//				itemSet.add(pencil);
//				itemSet.add(pen);
//				itemSet.add(paper);
//				customerOrder = new CustomerOrderEntity("order2", customer1, "address1", new Date(), itemSet);
//				customerOrderRepository.save(customerOrder);
//
//				itemSet = new HashSet<ItemEntity>();
//				itemSet.add(pencil);
//				itemSet.add(pen);
//				customerOrder = new CustomerOrderEntity("order3", customer2, "address2", new Date(), itemSet);
//				customerOrderRepository.save(customerOrder);
////				isDBLoaded = true;
//			}
//		}
//	}

}
