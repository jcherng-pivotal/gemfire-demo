package io.pivotal.gemfire.demo.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import io.pivotal.gemfire.demo.model.CustomerOrderModelApplication;

@SpringBootApplication
@Import({CustomerOrderModelApplication.class})
public class CustomerOrderDBApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerOrderDBApplication.class, args);
	}
}
