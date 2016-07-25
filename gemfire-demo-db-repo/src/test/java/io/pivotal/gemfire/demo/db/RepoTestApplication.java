package io.pivotal.gemfire.demo.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = { "io.pivotal.gemfire.demo.model.orm" })
@EnableJpaRepositories(basePackages = {"io.pivotal.gemfire.demo.db.repository"})
public class RepoTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepoTestApplication.class, args);
	}
}
