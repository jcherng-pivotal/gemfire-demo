package io.pivotal.gemfire.demo.server.customerorder.function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.Region.Entry;
import org.apache.geode.cache.execute.RegionFunctionContext;
import org.apache.geode.cache.execute.ResultSender;
import org.apache.geode.cache.query.Query;
import org.apache.geode.cache.query.QueryService;
import org.apache.geode.cache.query.SelectResults;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import io.pivotal.gemfire.demo.model.gf.key.CustomerKey;
import io.pivotal.gemfire.demo.model.gf.key.CustomerOrderKey;
import io.pivotal.gemfire.demo.model.gf.key.ItemKey;
import io.pivotal.gemfire.demo.model.gf.pdx.Customer;
import io.pivotal.gemfire.demo.model.gf.pdx.CustomerOrder;
import io.pivotal.gemfire.demo.model.gf.pdx.Item;
import io.pivotal.gemfire.demo.model.io.CustomerOrderIO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { CustomerOrderListFunctionTest.TestConfig.class })
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CustomerOrderListFunctionTest {

	@MockBean
	private GemFireCache cache;

	@Autowired
	private CustomerOrderListFunction customerOrderListFunction;

	@MockBean(name = "customer")
	private Region<CustomerKey, Customer> customerRegion;

	@MockBean(name = "customer-order")
	private Region<CustomerOrderKey, CustomerOrder> customerOrderRegion;

	@MockBean(name = "item")
	private Region<ItemKey, Item> itemRegion;

	@Mock
	private QueryService queryService;

	@Mock
	private Query query;

	@Mock
	private SelectResults<Entry<CustomerOrderKey, CustomerOrder>> results;

	@Mock
	private List<Entry<CustomerOrderKey, CustomerOrder>> entryList;

	@Mock
	private RegionFunctionContext regionFunctionContext;

	@Before
	public void setUp() throws Exception {
		given(cache.getQueryService()).willReturn(queryService);
		given(queryService.newQuery(anyString())).willReturn(query);
		given(query.execute(regionFunctionContext)).willReturn(results);

		setUpCustomerRegion();
		setUpItemRegion();
	}

	private void setUpCustomerRegion() {
		CustomerKey customerKey1 = new CustomerKey("customer1");
		Customer customer1 = new Customer();
		customer1.setName("Krikor Garegin");

		CustomerKey customerKey2 = new CustomerKey("customer2");
		Customer customer2 = new Customer();
		customer2.setName("Ararat Avetis");

		given(customerRegion.get(customerKey1)).willReturn(customer1);
		given(customerRegion.get(customerKey2)).willReturn(customer2);
	}

	private void setUpItemRegion() {
		ItemKey pencilKey = new ItemKey("pencil");
		Item pencil = new Item();
		pencil.setName("pencil");
		pencil.setDescription("pencil decription");
		pencil.setPrice("0.99");

		ItemKey penKey = new ItemKey("pen");
		Item pen = new Item();
		pen.setName("pen");
		pen.setDescription("pen description");
		pen.setPrice("1.49");

		ItemKey paperKey = new ItemKey("paper");
		Item paper = new Item();
		paper.setName("paper");
		paper.setDescription("paper description");
		paper.setPrice("0.10");

		given(itemRegion.get(pencilKey)).willReturn(pencil);
		given(itemRegion.get(penKey)).willReturn(pen);
		given(itemRegion.get(paperKey)).willReturn(paper);
	}

	private List<Entry<CustomerOrderKey, CustomerOrder>> getCustomer1OrderEntryList() {
		CustomerKey customerKey1 = new CustomerKey("customer1");

		ItemKey pencilKey = new ItemKey("pencil");
		ItemKey penKey = new ItemKey("pen");
		ItemKey paperKey = new ItemKey("paper");

		Map<CustomerOrderKey, CustomerOrder> dataMap = new HashMap<>();
		Set<String> itemSet = new HashSet<String>();
		itemSet.add(penKey.getId());
		itemSet.add(paperKey.getId());
		// 1.49 + 0.10 = 1.59
		CustomerOrderKey customerOrderKey = new CustomerOrderKey("order1", customerKey1);
		CustomerOrder customerOrder = new CustomerOrder("address1", (new Date()).getTime(), itemSet);
		dataMap.put(customerOrderKey, customerOrder);

		itemSet = new HashSet<String>();
		itemSet.add(pencilKey.getId());
		itemSet.add(penKey.getId());
		itemSet.add(paperKey.getId());
		// 1.59 + 0.99 = 2.58
		customerOrderKey = new CustomerOrderKey("order2", customerKey1);
		customerOrder = new CustomerOrder("address1", (new Date()).getTime(), itemSet);
		dataMap.put(customerOrderKey, customerOrder);

		return new ArrayList(dataMap.entrySet());
	}

	private List<Entry<CustomerOrderKey, CustomerOrder>> getCustomer2OrderEntryList() {
		CustomerKey customerKey2 = new CustomerKey("customer2");

		ItemKey pencilKey = new ItemKey("pencil");
		ItemKey penKey = new ItemKey("pen");

		Map<CustomerOrderKey, CustomerOrder> dataMap = new HashMap<>();
		Set<String> itemSet = new HashSet<String>();
		itemSet.add(pencilKey.getId());
		itemSet.add(penKey.getId());
		// 0.99 + 1.49 = 2.48
		CustomerOrderKey customerOrderKey = new CustomerOrderKey("order3", customerKey2);
		CustomerOrder customerOrder = new CustomerOrder("address2", (new Date()).getTime(), itemSet);
		dataMap.put(customerOrderKey, customerOrder);

		return new ArrayList(dataMap.entrySet());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullFilters() {
		customerOrderListFunction.validateFilters(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyFilters() {
		customerOrderListFunction.validateFilters(new HashSet<>());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidFilters() {
		CustomerKey customerKey = new CustomerKey("customer");
		ItemKey itemKey = new ItemKey("item");
		CustomerOrderKey customerOrderKey = new CustomerOrderKey("order", customerKey);
		Set<Object> filters = new HashSet<>();
		filters.add(customerKey);
		filters.add(itemKey);
		filters.add(customerOrderKey);
		customerOrderListFunction.validateFilters(filters);
	}

	@Test
	public void testValidFilters() {
		Set<CustomerKey> filters = new HashSet<>();
		filters.add(new CustomerKey("customer1"));
		filters.add(new CustomerKey("customer2"));
		filters.add(new CustomerKey("customer3"));
		assertThat(customerOrderListFunction.validateFilters(filters), is(true));
	}

	@Test
	public void testValidRequest() {
		assertThat(customerOrderListFunction.validateRequest(null), is(true));
	}

	@Test
	public void testProcessCustomer1() {
		Set filters = new HashSet<>();
		filters.add(new CustomerKey("customer1"));
		given(regionFunctionContext.getFilter()).willReturn(filters);
		ResultSender resultSender = mock(ResultSender.class);
		given(regionFunctionContext.getResultSender()).willReturn(resultSender);

		given(results.asList()).willReturn(getCustomer1OrderEntryList());
		customerOrderListFunction.process(regionFunctionContext);
		
		//customer 1 should have 2 order
		Mockito.verify(resultSender, times(1)).sendResult(any(CustomerOrderIO.class));
		Mockito.verify(resultSender, times(1)).lastResult(any(CustomerOrderIO.class));
	}

	@Test
	public void testProcessCustomer2() {
		Set filters = new HashSet<>();
		filters.add(new CustomerKey("customer2"));
		given(regionFunctionContext.getFilter()).willReturn(filters);
		ResultSender resultSender = mock(ResultSender.class);
		given(regionFunctionContext.getResultSender()).willReturn(resultSender);

		given(results.asList()).willReturn(getCustomer2OrderEntryList());
		customerOrderListFunction.process(regionFunctionContext);
		
		//customer 2 should have 1 order
		Mockito.verify(resultSender, times(0)).sendResult(any(CustomerOrderIO.class));
		Mockito.verify(resultSender, times(1)).lastResult(any(CustomerOrderIO.class));
	}
	
	@Test
	public void testProcessCustomer3() {
		Set filters = new HashSet<>();
		filters.add(new CustomerKey("customer3"));
		given(regionFunctionContext.getFilter()).willReturn(filters);
		ResultSender resultSender = mock(ResultSender.class);
		given(regionFunctionContext.getResultSender()).willReturn(resultSender);

		given(results.asList()).willReturn(new ArrayList());
		customerOrderListFunction.process(regionFunctionContext);
		
		//customer 3 should have 0 order
		Mockito.verify(resultSender, times(0)).sendResult(any(CustomerOrderIO.class));
		Mockito.verify(resultSender, times(1)).lastResult(null);
	}

	@Configuration
	static class TestConfig {
		@Bean
		CustomerOrderListFunction customerOrderListFunction() {
			CustomerOrderListFunction customerOrderListFunction = new CustomerOrderListFunction();
			return customerOrderListFunction;
		}
	}

}
