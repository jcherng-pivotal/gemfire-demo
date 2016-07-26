package io.pivotal.gemfire.demo.customerorder.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import io.pivotal.gemfire.demo.db.CustomerOrderDBApplication;
import io.pivotal.gemfire.demo.server.boot.GemFireServerBootApplication;

@SpringBootApplication
@Import({ GemFireServerBootApplication.class, CustomerOrderDBApplication.class })
@PropertySource("classpath:application-gfs.properties")
public class GemFireCustomerOrderServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GemFireCustomerOrderServerApplication.class, args);
	}
}
