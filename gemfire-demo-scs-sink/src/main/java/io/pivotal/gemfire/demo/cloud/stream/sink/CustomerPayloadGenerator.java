package io.pivotal.gemfire.demo.cloud.stream.sink;

import java.util.Map;

import io.pivotal.gemfire.demo.model.gf.key.CustomerKey;
import io.pivotal.gemfire.demo.model.gf.pdx.Customer;

public class CustomerPayloadGenerator extends AbstractPayloadGenerator<CustomerKey, Customer> {

	@Override
	protected CustomerKey getKey(Map<String, ?> dataMap) {
		CustomerKey customerKey = new CustomerKey((String) dataMap.get("customer.id"));
		return customerKey;
	}

	@Override
	protected Customer getValue(Map<String, ?> dataMap) {
		Customer customer = new Customer();
		customer.setName((String) dataMap.get("customer.name"));
		return customer;
	}

}
