package io.pivotal.gemfire.demo.server.boot;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gemstone.gemfire.cache.Cache;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { GemFireServerBootApplication.class })
public class GemFireServerBootApplicationTest {
	@Autowired
	private Cache cache;

	@Test
	public void test() {
		Assert.assertNotNull(cache);
	}

}
