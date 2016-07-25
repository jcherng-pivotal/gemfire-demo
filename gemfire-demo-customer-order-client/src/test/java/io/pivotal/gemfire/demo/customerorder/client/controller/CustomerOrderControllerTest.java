package io.pivotal.gemfire.demo.customerorder.client.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { TestConfig.class })
public class CustomerOrderControllerTest {
	@Autowired
	CustomerOrderController customerOrderController;

	@Before
	public void setUp() throws Exception {
		customerOrderController.loadData();
	}

	@Test
	public void testGetCustomerOrderList() {
		customerOrderController.getCustomerOrderList("customer1");
	}

}
