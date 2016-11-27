package io.pivotal.gemfire.demo.server.config;

import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.CacheFactoryBean;

@Configuration
@ComponentScan(basePackages = { "io.pivotal.gemfire.demo.server" })
public class GemFireServerBootConfig {

	@Bean
	CacheFactoryBean gemfireCache(PdxSerializer pdxSerializer) {
		CacheFactoryBean gemfireCache = new CacheFactoryBean();
		gemfireCache.setPdxSerializer(pdxSerializer);
		return gemfireCache;
	}

	@Bean
	PdxSerializer pdxSerializer() {
		PdxSerializer pdxSerializer = new ReflectionBasedAutoSerializer("io.pivotal.gemfire.demo.model.gf.pdx.*");
		return pdxSerializer;
	}
}
