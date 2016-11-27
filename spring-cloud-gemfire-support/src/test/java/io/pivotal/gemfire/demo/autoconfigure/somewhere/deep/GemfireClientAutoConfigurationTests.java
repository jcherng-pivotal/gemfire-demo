package io.pivotal.gemfire.demo.autoconfigure.somewhere.deep;

import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GemfireClientAutoConfigurationTests.GemfireClientAutoConfigurationApplication.class })
@DirtiesContext
public abstract class GemfireClientAutoConfigurationTests {

	@Autowired
	protected ClientCache clientCache;

	@Resource(name = "test-local-region")
	protected Region testLocalRegion;

	@Resource(name = "test-proxy-region")
	protected Region testProxyRegion;

	@Resource(name = "test-caching-proxy-region")
	protected Region testCachingProxyRegion;

	public static class DefaultConfigurationTests extends GemfireClientAutoConfigurationTests {
		@Test
		public void test() {
			assertNotNull(clientCache);
			assertNotNull(testLocalRegion);
			assertNotNull(testProxyRegion);
			assertNotNull(testCachingProxyRegion);
		}
	}

	@ActiveProfiles(profiles = { "cloud" })
	public static class CloudConfigurationTests extends GemfireClientAutoConfigurationTests {
		@Test
		public void test() {
			assertNotNull(clientCache);
			assertNotNull(testLocalRegion);
			assertNotNull(testProxyRegion);
			assertNotNull(testCachingProxyRegion);
		}
	}

	@SpringBootApplication()
	public static class GemfireClientAutoConfigurationApplication {

	}

}
