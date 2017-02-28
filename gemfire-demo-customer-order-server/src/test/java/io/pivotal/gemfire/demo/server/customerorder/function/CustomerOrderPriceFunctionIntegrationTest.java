package io.pivotal.gemfire.demo.server.customerorder.function;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.Execution;
import org.apache.geode.cache.execute.FunctionService;
import org.apache.geode.cache.execute.ResultCollector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.pivotal.gemfire.demo.model.gf.key.CustomerKey;
import io.pivotal.gemfire.demo.model.gf.key.CustomerOrderKey;
import io.pivotal.gemfire.demo.model.gf.key.ItemKey;
import io.pivotal.gemfire.demo.model.gf.pdx.Customer;
import io.pivotal.gemfire.demo.model.gf.pdx.CustomerOrder;
import io.pivotal.gemfire.demo.model.gf.pdx.Item;
import io.pivotal.gemfire.demo.server.config.GemFireServerBootConfig;
import io.pivotal.gemfire.demo.server.customerorder.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GemFireServerBootConfig.class })
public class CustomerOrderPriceFunctionIntegrationTest {
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
		testUtil.loadGemFireData();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testExecute() {
		CustomerKey customerKey = new CustomerKey("customer1");
		Set<CustomerKey> filter = new HashSet<CustomerKey>();
		filter.add(customerKey);

		Execution execution = FunctionService.onRegion(customerOrderRegion).withFilter(filter);
		ResultCollector<?, ?> rc = execution.execute(CustomerOrderPriceFunction.class.getSimpleName());
		List<BigDecimal> result = (List<BigDecimal>) rc.getResult();
		Assert.assertEquals(1, result.size());
		BigDecimal totalPrice = result.get(0);
		// 1.59 + 2.58 = 4.17
		Assert.assertEquals(new BigDecimal("4.17"), totalPrice);

		customerKey = new CustomerKey("customer2");
		filter = new HashSet<CustomerKey>();
		filter.add(customerKey);

		execution = FunctionService.onRegion(customerOrderRegion).withFilter(filter);
		rc = execution.execute(CustomerOrderPriceFunction.class.getSimpleName());
		result = (List<BigDecimal>) rc.getResult();
		Assert.assertEquals(1, result.size());
		totalPrice = result.get(0);
		// 2.48
		Assert.assertEquals(new BigDecimal("2.48"), totalPrice);

		// No such customer
		customerKey = new CustomerKey("customer3");
		filter = new HashSet<CustomerKey>();
		filter.add(customerKey);

		execution = FunctionService.onRegion(customerOrderRegion).withFilter(filter);
		rc = execution.execute(CustomerOrderPriceFunction.class.getSimpleName());
		result = (List<BigDecimal>) rc.getResult();
		Assert.assertEquals(1, result.size());
		totalPrice = result.get(0);
		Assert.assertEquals(null, totalPrice);
	}

}
