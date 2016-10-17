package io.pivotal.gemfire.demo.cloud.stream.sink;

import java.util.List;
import java.util.Map;

import org.springframework.integration.transformer.MessageTransformationException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.MessageBuilder;

class GemfireSinkHandler {
	private final IPayloadGenerator<?, ?> payloadGenerator;
	private final MessageHandler messageHandler;

	GemfireSinkHandler(IPayloadGenerator<?, ?> payloadGenerator, MessageHandler messageHandler) {
		super();
		this.payloadGenerator = payloadGenerator;
		this.messageHandler = messageHandler;
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	public void transform(Message<?> message) {
		// IPayloadGenerator payloadGenerator = applicationContext
		Object payload = message.getPayload();
		Map<?, ?> transformedPayload = null;
		if (payload instanceof Map) {
			transformedPayload = payloadGenerator.generatePayloadFromMap((Map<String, ?>) payload);
		} else if (payload instanceof List) {
			transformedPayload = payloadGenerator.generatePayloadFromList((List<Map<String, ?>>) payload);
		} else {
			throw new MessageTransformationException(message, "message does not contain proper payload");
		}

		Message<?> transformedMessage = MessageBuilder.fromMessage(message).withPayload(transformedPayload).build();

		messageHandler.handleMessage(transformedMessage);
	}

}
