package io.pivotal.gemfire.demo.server.customerorder.function;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.gemfire.LazyWiringDeclarableSupport;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.Region.Entry;
import com.gemstone.gemfire.cache.execute.Function;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import com.gemstone.gemfire.cache.execute.FunctionException;
import com.gemstone.gemfire.cache.execute.RegionFunctionContext;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;

import io.pivotal.gemfire.demo.model.gf.key.CustomerOrderKey;
import io.pivotal.gemfire.demo.model.gf.key.ItemKey;
import io.pivotal.gemfire.demo.model.gf.pdx.CustomerOrder;
import io.pivotal.gemfire.demo.model.gf.pdx.Item;

public class CalculateCustomerOrderPriceFunction extends LazyWiringDeclarableSupport implements Function, Declarable {

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

				Set<String> itemSet = entry.getValue().getItemSet();
				for (String itemId : itemSet) {
					ItemKey itemKey = new ItemKey(itemId);
					Item item = itemRegion.get(itemKey);
					totalPrice = totalPrice.add(new BigDecimal(item.getPrice()));
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
