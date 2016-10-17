package io.pivotal.gemfire.demo.cloud.stream.sink;

import javax.annotation.Resource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

import com.gemstone.gemfire.cache.Region;

@EnableBinding(Sink.class)
@EnableConfigurationProperties(GemfireSinkProperties.class)
public class GemfireSinkConfiguration {

	@Resource(name = "${gemfire.regionName}-payload-generator")
	IPayloadGenerator iPayloadGenerator;

	@SuppressWarnings("rawtypes")
	@Resource(name = "${gemfire.regionName}")
	Region clientRegion;

	@ServiceActivator(inputChannel = Sink.INPUT)
	@Bean
	public GemfireSinkHandler gemfireSinkHandler() {
		return new GemfireSinkHandler(iPayloadGenerator, messageHandler());
	}

	@Bean
	public MessageHandler messageHandler() {
		GemfireMessageHandler messageHandler = new GemfireMessageHandler(clientRegion);
		return messageHandler;
	}

	@Bean(name = "customer-payload-generator")
	public IPayloadGenerator customerPayloadGenerator() {
		IPayloadGenerator payloadGenerator = new CustomerPayloadGenerator();
		return payloadGenerator;
	}

	@Bean(name = "customer-order-payload-generator")
	public IPayloadGenerator customerOrderPayloadGenerator() {
		IPayloadGenerator payloadGenerator = new CustomerOrderPayloadGenerator();
		return payloadGenerator;
	}

	@Bean(name = "item-payload-generator")
	public IPayloadGenerator itemPayloadGenerator() {
		IPayloadGenerator payloadGenerator = new ItemPayloadGenerator();
		return payloadGenerator;
	}

}
