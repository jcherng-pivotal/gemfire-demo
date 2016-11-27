package io.pivotal.gemfire.demo.server.customerorder.function;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.Region.Entry;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.FunctionException;
import org.apache.geode.cache.execute.RegionFunctionContext;
import org.apache.geode.cache.query.Query;
import org.apache.geode.cache.query.QueryService;
import org.apache.geode.cache.query.SelectResults;
import org.springframework.data.gemfire.LazyWiringDeclarableSupport;

import io.pivotal.gemfire.demo.model.gf.key.CustomerOrderKey;
import io.pivotal.gemfire.demo.model.gf.key.ItemKey;
import io.pivotal.gemfire.demo.model.gf.pdx.CustomerOrder;
import io.pivotal.gemfire.demo.model.gf.pdx.Item;

public class CustomerOrderPriceFunction extends LazyWiringDeclarableSupport implements Function, Declarable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6444873328262716046L;

	@Resource(name = "customer-order")
	private Region<CustomerOrderKey, CustomerOrder> customerOrderRegion;

	@Resource(name = "item")
	private Region<ItemKey, Item> itemRegion;

	@Override
	@SuppressWarnings("unchecked")
	public void execute(FunctionContext fc) {
		if (!(fc instanceof RegionFunctionContext)) {
			throw new FunctionException(
					"This is a data aware function, and has to be called using FunctionService.onRegion.");
		}
		RegionFunctionContext rfc = (RegionFunctionContext) fc;
		QueryService queryService = customerOrderRegion.getRegionService().getQueryService();
		String qstr = "SELECT * FROM /customer-order.entries entry";

		try {
			Query query = queryService.newQuery(qstr);
			SelectResults<Entry<CustomerOrderKey, CustomerOrder>> results = (SelectResults<Entry<CustomerOrderKey, CustomerOrder>>) query
					.execute(rfc);
			List<Entry<CustomerOrderKey, CustomerOrder>> entryList = results.asList();

			BigDecimal totalPrice = null;
			for (Entry<CustomerOrderKey, CustomerOrder> entry : entryList) {
				if (totalPrice == null) {
					totalPrice = new BigDecimal("0.00");
				}

				if (rfc.getFilter().contains(entry.getKey().getCustomerKey())) {
					Set<String> itemSet = entry.getValue().getItemSet();
					for (String itemId : itemSet) {
						ItemKey itemKey = new ItemKey(itemId);
						Item item = itemRegion.get(itemKey);
						totalPrice = totalPrice.add(new BigDecimal(item.getPrice()));
					}
				}
			}

			// Send the result to function caller node.
			fc.getResultSender().lastResult(totalPrice);

		} catch (Exception e) {
			throw new FunctionException(e);
		}
	}

	@Override
	public String getId() {
		return this.getClass().getSimpleName();
	}

	@Override
	public boolean hasResult() {
		return true;
	}

	@Override
	public boolean isHA() {
		return false;
	}

	@Override
	public boolean optimizeForWrite() {
		return true;
	}

}
