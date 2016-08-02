package io.pivotal.gemfire.demo.client.customerorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ImportResource("classpath:customer-order-gfc-context.xml")
@PropertySource("classpath:application-gfc.properties")
@EnableSwagger2
public class GemFireCustomerOrderClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(GemFireCustomerOrderClientApplication.class, args);
	}
}
