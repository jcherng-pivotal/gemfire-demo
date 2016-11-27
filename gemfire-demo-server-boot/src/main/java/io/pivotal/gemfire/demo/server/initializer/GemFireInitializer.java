package io.pivotal.gemfire.demo.server.initializer;

import org.apache.geode.internal.ClassPathLoader;
import org.springframework.data.gemfire.support.SpringContextBootstrappingInitializer;

import io.pivotal.gemfire.demo.server.GemFireServerBootApplication;

public class GemFireInitializer extends SpringContextBootstrappingInitializer {

	public GemFireInitializer() {
		super();
		setBeanClassLoader(ClassPathLoader.getLatestAsClassLoader());
		register(GemFireServerBootApplication.class);
	}

}
