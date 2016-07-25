package io.pivotal.gemfire.demo.db.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import io.pivotal.gemfire.demo.model.orm.CustomerEntity;
import io.pivotal.gemfire.demo.model.orm.CustomerOrderEntity;

public interface CustomerOrderRepository extends CrudRepository<CustomerOrderEntity, String> {
	Set<CustomerOrderEntity> findByCustomer(CustomerEntity customer);
}
