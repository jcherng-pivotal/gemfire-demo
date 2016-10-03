package io.pivotal.gemfire.demo.autoconfigure;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.ServiceConnectorConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.client.ClientCacheFactoryBean;

import com.gemstone.gemfire.cache.CacheClosedException;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.pdx.PdxSerializer;

import io.pivotal.spring.cloud.service.gemfire.GemfireServiceConnectorConfig;

public class GemfireClientAutoConfiguration {

	@Configuration
	@ConditionalOnMissingBean({ ClientCache.class })
	@ImportResource(value = { "classpath*:*-gfc-context.xml" })
	@Profile("default")
	protected static class DefaultConfiguration {
		@Autowired(required = false)
		@Qualifier("gemfireProperties")
		private Properties gemfireProperties;

		@Autowired(required = false)
		@Qualifier("autoPdxSerializer")
		private PdxSerializer autoPdxSerializer;

		@Bean
		@Primary
		public ClientCacheFactoryBean gemfireCache() {
			ClientCacheFactoryBean gemfireCache = new ClientCacheFactoryBean();
			gemfireCache.setClose(true);
			gemfireCache.setProperties(gemfireProperties);
			gemfireCache.setPdxSerializer(autoPdxSerializer);
			return gemfireCache;
		}
	}

	@Configuration
	@EnableConfigurationProperties(GemfireProperties.class)
	@ImportResource(value = { "classpath*:*-gfc-context.xml" })
	@Profile("cloud")
	protected static class CloudConfiguration extends AbstractCloudConfig {

		@Autowired(required = false)
		private GemfireProperties gemfireProperties;

		@Autowired(required = false)
		@Qualifier("autoPdxSerializer")
		private PdxSerializer autoPdxSerializer;

		public ServiceConnectorConfig createGemfireConnectorConfig() {
			GemfireServiceConnectorConfig gemfireConfig = null;

			if (autoPdxSerializer != null) {
				gemfireConfig = new GemfireServiceConnectorConfig();
				gemfireConfig.setPdxSerializer(autoPdxSerializer);
				gemfireConfig.setPdxReadSerialized(false);
				if(gemfireProperties != null){
					//setup gemfireConfig
				}
			}
			return gemfireConfig;
		}

		@Bean
		@Primary
		ClientCache gemfireCache() {
			ClientCache gemfireCache = null;
			ServiceConnectorConfig serviceConnectorConfig = createGemfireConnectorConfig();
			if (serviceConnectorConfig != null) {
				try {
					gemfireCache = ClientCacheFactory.getAnyInstance();
					if (gemfireCache != null && !gemfireCache.isClosed()) {
						gemfireCache.close();
					}
				} catch (CacheClosedException ex) {
					// NOOP
				}
			}

			// Let Spring Cloud create a service connector for you.
			gemfireCache = cloud().getSingletonServiceConnector(ClientCache.class, createGemfireConnectorConfig());
			return gemfireCache;
		}
	}

}
