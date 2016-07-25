package io.pivotal.gemfire.demo.customerorder.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ImportResource("classpath:customer-order-gfc-context.xml")
@PropertySource("classpath:application-gfc.properties")
public class GemFireCustomerOrderClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(GemFireCustomerOrderClientApplication.class, args);
	}
}
