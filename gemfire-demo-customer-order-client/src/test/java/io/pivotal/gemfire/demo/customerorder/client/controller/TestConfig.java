package io.pivotal.gemfire.demo.customerorder.client.controller;



import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.pivotal.gemfire.demo.customerorder.client.GemFireCustomerOrderClientApplication;

@SpringBootApplication(exclude = GemFireCustomerOrderClientApplication.class)
@EntityScan(basePackages = { "io.pivotal.gemfire.demo.model.orm" })
@EnableJpaRepositories(basePackages = {"io.pivotal.gemfire.demo.db.repository"})
@ImportResource("classpath:gfs-context.xml")
public class TestConfig {

}
