package io.pivotal.gemfire.demo.cloud.stream.sink;

import java.util.List;
import java.util.Map;

public interface IPayloadGenerator<K,V> {

	Map<K, V> generatePayloadFromList(List<Map<String, ?>> list);

	Map<K, V> generatePayloadFromMap(Map<String,?> map);
}
