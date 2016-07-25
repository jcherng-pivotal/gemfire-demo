package io.pivotal.gemfire.demo.model.orm;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class ItemEntity {

	@Id
	private String id;
	private String name;
	private String description;
	private BigDecimal price;
	@ManyToMany(targetEntity = CustomerOrderEntity.class, mappedBy = "itemSet", fetch = FetchType.LAZY)
	private Set<CustomerOrderEntity> customerOrderSet;

	protected ItemEntity() {
		super();
	}

	public ItemEntity(String id) {
		super();
		this.id = id;
	}

	public ItemEntity(String id, String name, String description, BigDecimal price, Set<CustomerOrderEntity> customerOrderSet) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.customerOrderSet = customerOrderSet;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Set<CustomerOrderEntity> getCustomerOrderSet() {
		return customerOrderSet;
	}

	public void setCustomerOrderSet(Set<CustomerOrderEntity> customerOrderSet) {
		this.customerOrderSet = customerOrderSet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerOrderSet == null) ? 0 : customerOrderSet.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		ItemEntity other = (ItemEntity) obj;
		if (customerOrderSet == null) {
			if (other.customerOrderSet != null)
				return false;
		} else if (!customerOrderSet.equals(other.customerOrderSet))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItemEntity [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", customerOrderSet=" + customerOrderSet + "]";
	}

}
