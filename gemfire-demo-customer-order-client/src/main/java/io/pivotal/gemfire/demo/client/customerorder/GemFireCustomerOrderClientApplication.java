package io.pivotal.gemfire.demo.client.customerorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class GemFireCustomerOrderClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(GemFireCustomerOrderClientApplication.class, args);
	}
}
