package io.pivotal.gemfire.demo.cloud.stream.sink;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractPayloadGenerator<K, V> implements IPayloadGenerator<K, V> {

	@Override
	public Map<K, V> generatePayloadFromList(List<Map<String, ?>> list) {
		Map<K, V> resultMap = new HashMap<K, V>();
		for (Map<String, ?> map : list) {
			resultMap.putAll(generatePayloadFromMap(map));
		}
		return resultMap;
	}

	@Override
	public Map<K, V> generatePayloadFromMap(Map<String, ?> map) {
		Map<K, V> resultMap = new HashMap<K, V>();
		resultMap.put(getKey((Map<String, ?>) map), getValue((Map<String, ?>) map));
		return resultMap;
	}

	abstract protected K getKey(Map<String, ?> dataMap);

	abstract protected V getValue(Map<String, ?> dataMap);

}
