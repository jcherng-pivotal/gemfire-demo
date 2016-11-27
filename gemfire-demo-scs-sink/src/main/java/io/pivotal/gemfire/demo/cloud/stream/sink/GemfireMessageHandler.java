package io.pivotal.gemfire.demo.cloud.stream.sink;

import java.util.Map;

import org.apache.geode.cache.Region;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class GemfireMessageHandler extends AbstractMessageHandler {

	@SuppressWarnings("rawtypes")
	private final Region clientRegion;

	@SuppressWarnings("rawtypes")
	public GemfireMessageHandler(Region clientRegion) {
		super();
		this.clientRegion = clientRegion;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void handleMessageInternal(Message<?> message) throws Exception {
		Object payload = message.getPayload();
		Assert.state(payload instanceof Map, "payload must be a Map");
		clientRegion.putAll((Map) payload);
	}

}
