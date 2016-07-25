package io.pivotal.gemfire.demo.customerorder.server.cacheloader;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.gemstone.gemfire.cache.Region;

import io.pivotal.gemfire.demo.customerorder.server.GemFireCustomerOrderServerApplication;
import io.pivotal.gemfire.demo.customerorder.server.TestUtil;
import io.pivotal.gemfire.demo.model.gf.key.CustomerKey;
import io.pivotal.gemfire.demo.model.gf.key.CustomerOrderKey;
import io.pivotal.gemfire.demo.model.gf.key.ItemKey;
import io.pivotal.gemfire.demo.model.gf.pdx.Customer;
import io.pivotal.gemfire.demo.model.gf.pdx.CustomerOrder;
import io.pivotal.gemfire.demo.model.gf.pdx.Item;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { GemFireCustomerOrderServerApplication.class })
@Transactional
@Rollback
public class CustomerCacheLoaderTest {
	@Autowired
	private TestUtil testUtil;

	@Resource(name = "customer")
	private Region<CustomerKey, Customer> customerRegion;

	@Resource(name = "customer-order")
	private Region<CustomerOrderKey, CustomerOrder> customerOrderRegion;

	@Resource(name = "item")
	private Region<ItemKey, Item> itemRegion;

	@Before
	public void setUp() throws Exception {
		testUtil.loadDatabaseData();
	}

	@Test
	public void testLoad() {
		// Testing customer cache loader loaded all three regions based on the
		// database relationship
		Assert.assertNotNull(customerRegion.get(new CustomerKey("customer1")));
		Assert.assertNotEquals(0, customerOrderRegion.keySet());
		Assert.assertNotEquals(0, itemRegion.keySet());
	}

}
