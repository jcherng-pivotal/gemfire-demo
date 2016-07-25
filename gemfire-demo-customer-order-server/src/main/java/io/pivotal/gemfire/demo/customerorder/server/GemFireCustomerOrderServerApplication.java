package io.pivotal.gemfire.demo.customerorder.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = { "io.pivotal.gemfire.demo.model.orm" })
@EnableJpaRepositories(basePackages = {"io.pivotal.gemfire.demo.db.repository"})
@ImportResource("classpath*:gfs-context.xml")
@PropertySource("classpath:application-gfs.properties")
public class GemFireCustomerOrderServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GemFireCustomerOrderServerApplication.class, args);
	}
}
