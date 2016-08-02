package io.pivotal.gemfire.demo.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = {
		"io.pivotal.gemfire.demo.server" }, excludeFilters = @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = {
				"io.pivotal.gemfire.demo.*.client.*" }))
@ImportResource({ "classpath:gfs-context.xml", "classpath*:*-gfs-context.xml" })
@PropertySource("classpath:gemfire.properties")
public class GemFireServerBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(GemFireServerBootApplication.class, args);
	}
}
