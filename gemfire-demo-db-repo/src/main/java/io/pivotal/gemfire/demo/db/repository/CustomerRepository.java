package io.pivotal.gemfire.demo.db.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import io.pivotal.gemfire.demo.model.orm.CustomerEntity;

public interface CustomerRepository extends CrudRepository<CustomerEntity, String> {
	Set<CustomerEntity> findByName(String name);
}
