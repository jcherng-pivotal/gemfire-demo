package io.pivotal.gemfire.demo.db.repository;

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

import io.pivotal.gemfire.demo.db.CustomerOrderDBApplication;
import io.pivotal.gemfire.demo.model.orm.CustomerEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { CustomerOrderDBApplication.class})
@Transactional
@Rollback
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Before
	public void setUp() throws Exception {
		CustomerEntity customer1 = new CustomerEntity("customer1");
		customer1.setName("Krikor Garegin");
		customerRepository.save(customer1);

		CustomerEntity customer2 = new CustomerEntity("customer2");
		customer2.setName("Ararat Avetis");
		customerRepository.save(customer2);
	}

	@Test
	public void testCount() {
		Assert.assertEquals(2, customerRepository.count());
	}

	@Test
	public void testFindByName() {
		Set<CustomerEntity> customerSet = customerRepository.findByName("Krikor Garegin");
		Assert.assertEquals(1, customerSet.size());
	}

}
