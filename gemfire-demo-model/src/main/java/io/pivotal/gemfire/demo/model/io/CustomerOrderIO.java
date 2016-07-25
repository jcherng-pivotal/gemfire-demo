package io.pivotal.gemfire.demo.model.io;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class CustomerOrderIO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3551141213229998640L;
	private String id;
	private CustomerIO customer;
	private String shippingAddress;
	private Date orderDate;
	private Set<ItemIO> items;

	public CustomerOrderIO() {
		super();
	}

	public CustomerOrderIO(String id, CustomerIO customer, String shippingAddress, Date orderDate, Set<ItemIO> items) {
		super();
		this.id = id;
		this.customer = customer;
		this.shippingAddress = shippingAddress;
		this.orderDate = orderDate;
		this.items = items;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CustomerIO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerIO customer) {
		this.customer = customer;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Set<ItemIO> getItems() {
		return items;
	}

	public void setItems(Set<ItemIO> items) {
		this.items = items;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
		result = prime * result + ((shippingAddress == null) ? 0 : shippingAddress.hashCode());
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
		CustomerOrderIO other = (CustomerOrderIO) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (orderDate == null) {
			if (other.orderDate != null)
				return false;
		} else if (!orderDate.equals(other.orderDate))
			return false;
		if (shippingAddress == null) {
			if (other.shippingAddress != null)
				return false;
		} else if (!shippingAddress.equals(other.shippingAddress))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomerOrderIO [id=" + id + ", customer=" + customer + ", shippingAddress=" + shippingAddress
				+ ", orderDate=" + orderDate + ", items=" + items + "]";
	}

}
