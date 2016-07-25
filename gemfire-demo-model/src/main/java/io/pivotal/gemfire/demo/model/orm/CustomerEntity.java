package io.pivotal.gemfire.demo.model.orm;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class CustomerEntity {

	@Id
	private String id;
	private String name;
	@OneToMany(targetEntity = CustomerOrderEntity.class, mappedBy = "customer", fetch=FetchType.LAZY)
	private Set<CustomerOrderEntity> customerOrderSet;

	protected CustomerEntity() {
		super();
	}

	public CustomerEntity(String id) {
		super();
		this.id = id;
	}

	public CustomerEntity(String id, String name, Set<CustomerOrderEntity> customerOrderSet) {
		super();
		this.id = id;
		this.name = name;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		CustomerEntity other = (CustomerEntity) obj;
		if (customerOrderSet == null) {
			if (other.customerOrderSet != null)
				return false;
		} else if (!customerOrderSet.equals(other.customerOrderSet))
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
		return true;
	}

	@Override
	public String toString() {
		return "CustomerEntity [id=" + id + ", name=" + name + ", customerOrderSet=" + customerOrderSet + "]";
	}

}
