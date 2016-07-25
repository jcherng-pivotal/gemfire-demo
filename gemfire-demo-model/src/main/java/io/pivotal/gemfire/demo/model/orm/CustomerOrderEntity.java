package io.pivotal.gemfire.demo.model.orm;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class CustomerOrderEntity {

	@Id
	private String id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CUST_ID", nullable = false)
	private CustomerEntity customer;
	private String shippingAddress;
	private Date orderDate;
	@ManyToMany(targetEntity = ItemEntity.class, fetch = FetchType.LAZY)
	private Set<ItemEntity> itemSet;

	protected CustomerOrderEntity() {
		super();
	}

	public CustomerOrderEntity(String id, CustomerEntity customer) {
		super();
		this.id = id;
		this.customer = customer;
	}

	public CustomerOrderEntity(String id, CustomerEntity customer, String shippingAddress, Date orderDate, Set<ItemEntity> itemSet) {
		super();
		this.id = id;
		this.customer = customer;
		this.shippingAddress = shippingAddress;
		this.orderDate = orderDate;
		this.itemSet = itemSet;
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

	public Set<ItemEntity> getItemSet() {
		return itemSet;
	}

	public void setItemSet(Set<ItemEntity> itemSet) {
		this.itemSet = itemSet;
	}

	public String getId() {
		return id;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itemSet == null) ? 0 : itemSet.hashCode());
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
		CustomerOrderEntity other = (CustomerOrderEntity) obj;
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
		if (itemSet == null) {
			if (other.itemSet != null)
				return false;
		} else if (!itemSet.equals(other.itemSet))
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
		return "CustomerOrderEntity [id=" + id + ", customer=" + customer + ", shippingAddress=" + shippingAddress
				+ ", orderDate=" + orderDate + ", itemSet=" + itemSet + "]";
	}

}
