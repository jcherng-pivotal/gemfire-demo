package io.pivotal.gemfire.demo.cloud.stream.sink;

import java.util.Map;
import java.util.Set;

import io.pivotal.gemfire.demo.model.gf.key.CustomerKey;
import io.pivotal.gemfire.demo.model.gf.key.CustomerOrderKey;
import io.pivotal.gemfire.demo.model.gf.pdx.CustomerOrder;

public class CustomerOrderPayloadGenerator extends AbstractPayloadGenerator<CustomerOrderKey, CustomerOrder> {

	@Override
	protected CustomerOrderKey getKey(Map<String, ?> dataMap) {
		CustomerKey customerKey = new CustomerKey((String) dataMap.get("customer.id"));
		CustomerOrderKey customerOrderKey = new CustomerOrderKey((String) dataMap.get("customerOrder.id"), customerKey);

		return customerOrderKey;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected CustomerOrder getValue(Map<String, ?> dataMap) {
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setItemSet((Set<String>) dataMap.get("customerOrder.itemSet"));
		customerOrder.setOrderDate((Long) dataMap.get("customerOrder.orderDate"));
		customerOrder.setShippingAddress((String) dataMap.get("customerOrder.shippingAddress"));

		return customerOrder;
	}

}
