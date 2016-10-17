package io.pivotal.gemfire.demo.server.initializer;

import org.springframework.data.gemfire.support.SpringContextBootstrappingInitializer;

import com.gemstone.gemfire.internal.ClassPathLoader;

import io.pivotal.gemfire.demo.server.GemFireServerBootApplication;

public class GemFireInitializer extends SpringContextBootstrappingInitializer {

	public GemFireInitializer() {
		super();
		setBeanClassLoader(ClassPathLoader.getLatestAsClassLoader());
		register(GemFireServerBootApplication.class);
	}

}
