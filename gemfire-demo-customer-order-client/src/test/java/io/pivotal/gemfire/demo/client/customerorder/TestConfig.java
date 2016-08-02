package io.pivotal.gemfire.demo.client.customerorder;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import io.pivotal.gemfire.demo.server.GemFireCustomerOrderServerApplication;
import io.pivotal.gemfire.demo.server.GemFireServerBootApplication;

@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = {
		"io.pivotal.gemfire.demo.client.customerorder.GemFireCustomerOrderClientApplication" }))
@Import({ GemFireServerBootApplication.class })
public class TestConfig {

}
