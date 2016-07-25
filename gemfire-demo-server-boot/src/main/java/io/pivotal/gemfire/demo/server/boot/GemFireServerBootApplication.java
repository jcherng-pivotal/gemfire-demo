package io.pivotal.gemfire.demo.server.boot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:gfs-context.xml")
public class GemFireServerBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(GemFireServerBootApplication.class, args);
	}
}
