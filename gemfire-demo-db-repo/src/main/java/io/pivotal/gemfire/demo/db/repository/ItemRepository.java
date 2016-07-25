package io.pivotal.gemfire.demo.db.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import io.pivotal.gemfire.demo.model.orm.ItemEntity;

public interface ItemRepository extends CrudRepository<ItemEntity, String> {
	Set<ItemEntity> findByName(String name);
}
