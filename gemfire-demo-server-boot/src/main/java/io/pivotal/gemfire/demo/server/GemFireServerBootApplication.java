package io.pivotal.gemfire.demo.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ImportResource({ "classpath*:gfs-context.xml", "classpath*:*-gfs-context.xml" })
@PropertySource("classpath:gemfire.properties")
public class GemFireServerBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(GemFireServerBootApplication.class, args);
	}
}
