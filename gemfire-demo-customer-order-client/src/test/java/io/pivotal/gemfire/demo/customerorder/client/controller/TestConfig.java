package io.pivotal.gemfire.demo.customerorder.client.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import io.pivotal.gemfire.demo.customerorder.client.GemFireCustomerOrderClientApplication;
import io.pivotal.gemfire.demo.customerorder.server.GemFireCustomerOrderServerApplication;

@SpringBootApplication(exclude = { GemFireCustomerOrderClientApplication.class })
@Import({ GemFireCustomerOrderServerApplication.class })
public class TestConfig {

}
