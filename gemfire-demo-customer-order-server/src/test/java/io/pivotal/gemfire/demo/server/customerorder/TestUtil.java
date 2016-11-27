package io.pivotal.gemfire.demo.server.customerorder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.geode.cache.Region;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.pivotal.gemfire.demo.db.repository.CustomerOrderRepository;
import io.pivotal.gemfire.demo.db.repository.CustomerRepository;
import io.pivotal.gemfire.demo.db.repository.ItemRepository;
import io.pivotal.gemfire.demo.model.gf.key.CustomerKey;
import io.pivotal.gemfire.demo.model.gf.key.CustomerOrderKey;
import io.pivotal.gemfire.demo.model.gf.key.ItemKey;
import io.pivotal.gemfire.demo.model.gf.pdx.Customer;
import io.pivotal.gemfire.demo.model.gf.pdx.CustomerOrder;
import io.pivotal.gemfire.demo.model.gf.pdx.Item;
import io.pivotal.gemfire.demo.model.orm.CustomerEntity;
import io.pivotal.gemfire.demo.model.orm.CustomerOrderEntity;
import io.pivotal.gemfire.demo.model.orm.ItemEntity;

@Component
public class TestUtil {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private CustomerOrderRepository customerOrderRepository;

	@Resource(name = "customer")
	private Region<CustomerKey, Customer> customerRegion;

	@Resource(name = "customer-order")
	private Region<CustomerOrderKey, CustomerOrder> customerOrderRegion;

	@Resource(name = "item")
	private Region<ItemKey, Item> itemRegion;

	public void loadDatabaseData() {
		Assert.assertNotNull(customerRepository);
		Assert.assertNotNull(itemRepository);
		Assert.assertNotNull(customerOrderRepository);

		CustomerEntity customer1 = new CustomerEntity("customer1");
		customer1.setName("Krikor Garegin");
		customerRepository.save(customer1);

		CustomerEntity customer2 = new CustomerEntity("customer2");
		customer2.setName("Ararat Avetis");
		customerRepository.save(customer2);

		ItemEntity pencil = new ItemEntity("pencil");
		pencil.setName("pencil");
		pencil.setDescription("pencil decription");
		pencil.setPrice(new BigDecimal("0.99"));
		itemRepository.save(pencil);

		ItemEntity pen = new ItemEntity("pen");
		pen.setName("pen");
		pen.setDescription("pen description");
		pen.setPrice(new BigDecimal("1.49"));
		itemRepository.save(pen);

		ItemEntity paper = new ItemEntity("paper");
		paper.setName("pen");
		paper.setDescription("paper description");
		paper.setPrice(new BigDecimal("0.10"));
		itemRepository.save(paper);

		Set<ItemEntity> itemSet = new HashSet<ItemEntity>();
		itemSet.add(pen);
		itemSet.add(paper);
		CustomerOrderEntity customerOrder = new CustomerOrderEntity("order1", customer1, "address1", new Date(),
				itemSet);
		customerOrderRepository.save(customerOrder);

		itemSet = new HashSet<ItemEntity>();
		itemSet.add(pencil);
		itemSet.add(pen);
		itemSet.add(paper);
		customerOrder = new CustomerOrderEntity("order2", customer1, "address1", new Date(), itemSet);
		customerOrderRepository.save(customerOrder);

		itemSet = new HashSet<ItemEntity>();
		itemSet.add(pencil);
		itemSet.add(pen);
		customerOrder = new CustomerOrderEntity("order3", customer2, "address2", new Date(), itemSet);
		customerOrderRepository.save(customerOrder);
	}

	public void loadGemFireData() {
		Assert.assertNotNull(customerRegion);
		Assert.assertNotNull(customerOrderRegion);
		Assert.assertNotNull(itemRegion);

		CustomerKey customerKey1 = new CustomerKey("customer1");
		Customer customer1 = new Customer();
		customer1.setName("Krikor Garegin");
		customerRegion.put(customerKey1, customer1);

		CustomerKey customerKey2 = new CustomerKey("customer2");
		Customer customer2 = new Customer();
		customer2.setName("Ararat Avetis");
		customerRegion.put(customerKey2, customer2);

		ItemKey pencilKey = new ItemKey("pencil");
		Item pencil = new Item();
		pencil.setName("pencil");
		pencil.setDescription("pencil decription");
		pencil.setPrice("0.99");
		itemRegion.put(pencilKey, pencil);

		ItemKey penKey = new ItemKey("pen");
		Item pen = new Item();
		pen.setName("pen");
		pen.setDescription("pen description");
		pen.setPrice("1.49");
		itemRegion.put(penKey, pen);

		ItemKey paperKey = new ItemKey("paper");
		Item paper = new Item();
		paper.setName("paper");
		paper.setDescription("paper description");
		paper.setPrice("0.10");
		itemRegion.put(paperKey, paper);

		Set<String> itemSet = new HashSet<String>();
		itemSet.add(penKey.getId());
		itemSet.add(paperKey.getId());
		// 1.49 + 0.10 = 1.59
		CustomerOrderKey customerOrderKey = new CustomerOrderKey("order1", customerKey1);
		CustomerOrder customerOrder = new CustomerOrder("address1", (new Date()).getTime(), itemSet);
		customerOrderRegion.put(customerOrderKey, customerOrder);

		itemSet = new HashSet<String>();
		itemSet.add(pencilKey.getId());
		itemSet.add(penKey.getId());
		itemSet.add(paperKey.getId());
		// 1.59 + 0.99 = 2.58
		customerOrderKey = new CustomerOrderKey("order2", customerKey1);
		customerOrder = new CustomerOrder("address1", (new Date()).getTime(), itemSet);
		customerOrderRegion.put(customerOrderKey, customerOrder);

		itemSet = new HashSet<String>();
		itemSet.add(pencilKey.getId());
		itemSet.add(penKey.getId());
		// 0.99 + 1.49 = 2.48
		customerOrderKey = new CustomerOrderKey("order3", customerKey2);
		customerOrder = new CustomerOrder("address2", (new Date()).getTime(), itemSet);
		customerOrderRegion.put(customerOrderKey, customerOrder);
	}

}
