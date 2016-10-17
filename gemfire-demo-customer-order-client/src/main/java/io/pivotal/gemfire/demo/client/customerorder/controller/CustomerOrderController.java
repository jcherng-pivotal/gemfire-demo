package io.pivotal.gemfire.demo.client.customerorder.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.execute.Execution;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;

import io.pivotal.gemfire.demo.model.gf.key.CustomerKey;
import io.pivotal.gemfire.demo.model.gf.key.CustomerOrderKey;
import io.pivotal.gemfire.demo.model.gf.key.ItemKey;
import io.pivotal.gemfire.demo.model.gf.pdx.Customer;
import io.pivotal.gemfire.demo.model.gf.pdx.CustomerOrder;
import io.pivotal.gemfire.demo.model.gf.pdx.Item;
import io.pivotal.gemfire.demo.model.io.CustomerOrderIO;
import io.pivotal.gemfire.demo.server.customerorder.function.GetCustomerOrderListFunction;

@RestController
public class CustomerOrderController {

	@Resource(name = "customer")
	private Region<CustomerKey, Customer> customerRegion;

	@Resource(name = "customer-order")
	private Region<CustomerOrderKey, CustomerOrder> customerOrderRegion;

	@Resource(name = "item")
	private Region<ItemKey, Item> itemRegion;

	@RequestMapping(value = "/loadData", method = RequestMethod.POST)
	public void loadData() {

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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCustomerOrderList", method = RequestMethod.GET)
	public List<CustomerOrderIO> getCustomerOrderList(@RequestParam(value = "customerId") String name) {
		CustomerKey customerKey = new CustomerKey("customer1");
		Set<CustomerKey> filter = new HashSet<CustomerKey>();
		filter.add(customerKey);

		Execution execution = FunctionService.onRegion(customerOrderRegion).withFilter(filter);
		ResultCollector<?, ?> rc = execution.execute(GetCustomerOrderListFunction.class.getSimpleName());
		List<CustomerOrderIO> result = (List<CustomerOrderIO>) rc.getResult();
		return result;
	}

}
