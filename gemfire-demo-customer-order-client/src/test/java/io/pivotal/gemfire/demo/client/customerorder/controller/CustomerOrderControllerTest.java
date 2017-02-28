package io.pivotal.gemfire.demo.client.customerorder.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.pivotal.gemfire.demo.client.customerorder.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { TestConfig.class })
public class CustomerOrderControllerTest {
	@Autowired
	CustomerOrderController customerOrderController;

	@Before
	public void setUp() throws Exception {
		customerOrderController.loadDataByRegionPutAll();
	}

	@Test
	public void testGetCustomerOrderList() {
		customerOrderController.getCustomerOrderList("customer1");
	}

}
