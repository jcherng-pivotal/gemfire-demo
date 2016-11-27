package io.pivotal.gemfire.demo.client.customerorder.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.Execution;
import org.apache.geode.cache.execute.FunctionService;
import org.apache.geode.cache.execute.ResultCollector;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.gemfire.demo.model.gf.key.CustomerKey;
import io.pivotal.gemfire.demo.model.gf.key.CustomerOrderKey;
import io.pivotal.gemfire.demo.model.gf.pdx.Customer;
import io.pivotal.gemfire.demo.model.gf.pdx.CustomerOrder;
import io.pivotal.gemfire.demo.model.io.CustomerOrderIO;
import io.pivotal.gemfire.demo.server.customerorder.function.CustomerOrderListFunction;

@RestController
public class CustomerOrderController {

	@Resource(name = "customer")
	private Region<CustomerKey, Customer> customerRegion;

	@Resource(name = "customer-order")
	private Region<CustomerOrderKey, CustomerOrder> customerOrderRegion;

	@RequestMapping(value = "/loadData", method = RequestMethod.POST)
	public void loadData() {
		CustomerKey customerKey1 = new CustomerKey("customer1");
		Customer customer1 = new Customer();
		customer1.setName("Krikor Garegin");
		customerRegion.get(customerKey1);

		CustomerKey customerKey2 = new CustomerKey("customer2");
		Customer customer2 = new Customer();
		customer2.setName("Ararat Avetis");
		customerRegion.get(customerKey2);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCustomerOrderList", method = RequestMethod.GET)
	public List<CustomerOrderIO> getCustomerOrderList(@RequestParam(value = "customerId") String customerId) {
		CustomerKey customerKey = new CustomerKey(customerId);
		Set<CustomerKey> filter = new HashSet<CustomerKey>();
		filter.add(customerKey);

		Execution execution = FunctionService.onRegion(customerOrderRegion).withFilter(filter);
		ResultCollector<?, ?> rc = execution.execute(CustomerOrderListFunction.class.getSimpleName());
		List<CustomerOrderIO> result = (List<CustomerOrderIO>) rc.getResult();
		return result;
	}

}
