package io.pivotal.gemfire.demo.cloud.stream.sink;

import java.util.Map;

import io.pivotal.gemfire.demo.model.gf.key.ItemKey;
import io.pivotal.gemfire.demo.model.gf.pdx.Item;

public class ItemPayloadGenerator extends AbstractPayloadGenerator<ItemKey, Item> {

	@Override
	protected ItemKey getKey(Map<String, ?> dataMap) {
		ItemKey itemKey = new ItemKey((String) dataMap.get("item.id"));
		return itemKey;
	}

	@Override
	protected Item getValue(Map<String, ?> dataMap) {
		Item item = new Item();
		item.setDescription((String) dataMap.get("item.description"));
		item.setName((String) dataMap.get("item.name"));
		item.setPrice((String) dataMap.get("item.price"));
		return item;
	}


}
