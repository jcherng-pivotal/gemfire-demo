package io.pivotal.gemfire.demo.client.customerorder.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.pivotal.gemfire.demo.client.customerorder.TestConfig;
import io.pivotal.gemfire.demo.client.customerorder.controller.CustomerOrderController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { TestConfig.class })
// @WebAppConfiguration // 3
// @IntegrationTest("server.port:0") // 4
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
