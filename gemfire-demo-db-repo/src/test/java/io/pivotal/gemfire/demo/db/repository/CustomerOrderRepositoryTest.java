package io.pivotal.gemfire.demo.db.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import io.pivotal.gemfire.demo.db.RepoTestApplication;
import io.pivotal.gemfire.demo.model.orm.CustomerEntity;
import io.pivotal.gemfire.demo.model.orm.CustomerOrderEntity;
import io.pivotal.gemfire.demo.model.orm.ItemEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { RepoTestApplication.class })
@Transactional
@Rollback
public class CustomerOrderRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private CustomerOrderRepository customerOrderRepository;

	@Before
	public void setUp() throws Exception {
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
		CustomerOrderEntity customerOrder = new CustomerOrderEntity("order1", customer1, "address1", new Date(), itemSet);
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

	@Test
	public void testCount() {
		Assert.assertEquals(3, customerOrderRepository.count());
	}

	@Test
	public void testFindByCustomer() {
		CustomerEntity customer1 = new CustomerEntity("customer1");
		customer1.setName("Krikor Garegin");
		Set<CustomerOrderEntity> customerOrderSet = customerOrderRepository.findByCustomer(customer1);
		Assert.assertEquals(2, customerOrderSet.size());
		for (CustomerOrderEntity order : customerOrderSet) {
			Assert.assertEquals(customer1, order.getCustomer());
			Assert.assertNotEquals(0, order.getItemSet().size());
		}

		CustomerEntity customer2 = new CustomerEntity("customer2");
		customer2.setName("Ararat Avetis");
		customerOrderSet = customerOrderRepository.findByCustomer(customer2);
		Assert.assertEquals(1, customerOrderSet.size());
		for (CustomerOrderEntity order : customerOrderSet) {
			Assert.assertEquals(customer2, order.getCustomer());
			Assert.assertNotEquals(0, order.getItemSet().size());
		}

	}

}
