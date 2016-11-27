package io.pivotal.spring.cloud.service.gemfire.autoconfigure;

import java.net.URI;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.pdx.PdxSerializer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.ServiceConnectorConfig;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.client.ClientCacheFactoryBean;

import io.pivotal.spring.cloud.service.common.GemfireServiceInfo;
import io.pivotal.spring.cloud.service.gemfire.GemfireServiceConnectorConfig;

public class GemfireClientAutoConfiguration {

	@Configuration
	@ConditionalOnMissingBean({ Cache.class, ClientCache.class })
	@ConditionalOnClass({ ClientCache.class })
	@ImportResource(value = { "classpath*:*-gfc-context.xml" })
	@Profile("default")
	protected static class DefaultConfiguration {
		@Autowired(required = false)
		@Qualifier("gemfireProperties")
		private Properties gemfireProperties;

		@Autowired(required = false)
		private PdxSerializer autoPdxSerializer;

		@Bean
		@Primary
		ClientCacheFactoryBean gemfireCache() {
			ClientCacheFactoryBean gemfireCache = new ClientCacheFactoryBean();
			if (autoPdxSerializer != null) {
				gemfireCache.setClose(true);
				gemfireCache.setPdxSerializer(autoPdxSerializer);
			}
			gemfireCache.setProperties(gemfireProperties);
			return gemfireCache;
		}
	}

	@Configuration
	@EnableConfigurationProperties(GemfireProperties.class)
	@ConditionalOnMissingBean({ Cache.class, ClientCache.class })
	@ConditionalOnClass({ ClientCache.class })
	@ImportResource(value = { "classpath*:*-gfc-context.xml" })
	@Profile("cloud")
	protected static class CloudConfiguration extends AbstractCloudConfig {

		@Autowired(required = false)
		private GemfireProperties gemfireProperties;

		@Autowired(required = false)
		private PdxSerializer autoPdxSerializer;

		@Autowired(required = false)
		private ClientCacheFactory factory;

		@Bean
		ClientCacheFactory clientCacheFactory() {
			return new ClientCacheFactory();
		}

		@Bean
		@Primary
		ClientCache gemfireCache() {

			GemfireServiceInfo gemfireServiceInfo = null;
			for (ServiceInfo serviceInfo : cloud().getServiceInfos()) {
				if (serviceInfo instanceof GemfireServiceInfo) {
					gemfireServiceInfo = (GemfireServiceInfo) serviceInfo;
				}
			}

			ClientCache gemfireCache = null;
			if (gemfireServiceInfo != null) {
				gemfireCache = create(gemfireServiceInfo, createGemfireConnectorConfig());
			}

			return gemfireCache;
		}

		public ServiceConnectorConfig createGemfireConnectorConfig() {
			GemfireServiceConnectorConfig gemfireConfig = new GemfireServiceConnectorConfig();

			if (autoPdxSerializer != null) {
				gemfireConfig.setPdxSerializer(autoPdxSerializer);
				gemfireConfig.setPdxReadSerialized(false);
			}

			if (gemfireProperties != null) {
				// setup gemfireConfig
			}

			return gemfireConfig;
		}

		private ClientCache create(GemfireServiceInfo serviceInfo, ServiceConnectorConfig serviceConnectorConfig) {
			for (URI locator : serviceInfo.getLocators()) {
				factory.addPoolLocator(locator.getHost(), locator.getPort());
			}
			if (serviceInfo.getUsername() != null) {
				factory.set("security-client-auth-init",
						"io.pivotal.spring.cloud.service.gemfire.UserAuthInitialize.create");
				factory.set("security-username", serviceInfo.getUsername());
			}
			if (serviceInfo.getPassword() != null) {
				factory.set("security-password", serviceInfo.getPassword());
			}
			if (serviceConnectorConfig != null
					&& serviceConnectorConfig.getClass().isAssignableFrom(GemfireServiceConnectorConfig.class)) {
				apply((GemfireServiceConnectorConfig) serviceConnectorConfig);
			}
			return factory.create();
		}

		private void apply(GemfireServiceConnectorConfig config) {
			BeanUtils.copyProperties(config, factory, getNullPropertyNames(config));
		}

		private static String[] getNullPropertyNames(Object source) {
			final BeanWrapper src = new BeanWrapperImpl(source);
			java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

			Set<String> emptyNames = new HashSet<String>();
			for (java.beans.PropertyDescriptor pd : pds) {
				Object srcValue = src.getPropertyValue(pd.getName());
				if (srcValue == null)
					emptyNames.add(pd.getName());
			}
			String[] result = new String[emptyNames.size()];
			return emptyNames.toArray(result);
		}
	}
}
