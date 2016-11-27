package io.pivotal.gemfire.demo.cloud.stream.sink;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.geode.cache.Region;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.Bindings;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { GemfireSinkApplication.class })
@DirtiesContext
public abstract class GemfireSinkApplicationTest {

	@Autowired
	@Bindings(GemfireSinkConfiguration.class)
	protected Sink sink;

	@Autowired
	protected MessageCollector messageCollector;

	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("customer")
	protected Region customerRegion;

	@SpringBootTest("gemfire.regionName=customer")
	public static class CustomerTests extends GemfireSinkApplicationTest {

		@Test
		public void testAddCustomer() throws InterruptedException {
			customerRegion.clear();
			Map<String, Object> dataMap = new HashMap<>();

			dataMap.put("customer.id", "customer1");
			dataMap.put("customer.name", "Krikor Garegin");

			sink.input().send(MessageBuilder.withPayload(dataMap).build());
			assertEquals(1, customerRegion.keySet().size());
		}

		@Test
		public void testAddCustomers() throws InterruptedException {
			customerRegion.clear();
			List<Map<String, Object>> dataList = new ArrayList<>();

			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("customer.id", "customer1");
			dataMap.put("customer.name", "Krikor Garegin");
			dataList.add(dataMap);

			dataMap = new HashMap<>();
			dataMap.put("customer.id", "customer2");
			dataMap.put("customer.name", "Ararat Avetis");
			dataList.add(dataMap);

			sink.input().send(MessageBuilder.withPayload(dataList).build());
			assertEquals(2, customerRegion.keySet().size());
		}

	}
}
