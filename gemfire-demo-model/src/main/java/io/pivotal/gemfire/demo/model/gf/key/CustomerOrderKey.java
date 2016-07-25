package io.pivotal.gemfire.demo.model.gf.key;

import java.io.Serializable;

import com.gemstone.gemfire.cache.EntryOperation;
import com.gemstone.gemfire.cache.PartitionResolver;

import io.pivotal.gemfire.demo.model.gf.pdx.CustomerOrder;

public class CustomerOrderKey implements PartitionResolver<CustomerOrderKey, CustomerOrder>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4937920522869283674L;
	private final String id;
	private final CustomerKey customerKey;

	public CustomerOrderKey(String id, CustomerKey customerKey) {
		super();
		this.id = id;
		this.customerKey = customerKey;
	}

	public String getId() {
		return id;
	}

	public CustomerKey getCustomerKey() {
		return customerKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerKey == null) ? 0 : customerKey.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerOrderKey other = (CustomerOrderKey) obj;
		if (customerKey == null) {
			if (other.customerKey != null)
				return false;
		} else if (!customerKey.equals(other.customerKey))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomerOrderKey [id=" + id + ", customerKey=" + customerKey + "]";
	}

	@Override
	public void close() {
		// NOOP
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}

	@Override
	public Object getRoutingObject(EntryOperation<CustomerOrderKey, CustomerOrder> op) {
		return customerKey;
	}

}
