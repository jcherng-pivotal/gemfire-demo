package io.pivotal.gemfire.demo.server.customerorder.function;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.FunctionException;
import org.apache.geode.cache.execute.RegionFunctionContext;
import org.apache.geode.cache.query.Query;
import org.apache.geode.cache.query.QueryService;
import org.apache.geode.cache.query.SelectResults;
import org.springframework.beans.factory.annotation.Autowired;

import io.pivotal.gemfire.demo.model.gf.key.CustomerKey;
import io.pivotal.gemfire.demo.model.gf.key.CustomerOrderKey;
import io.pivotal.gemfire.demo.model.gf.key.ItemKey;
import io.pivotal.gemfire.demo.model.gf.pdx.Customer;
import io.pivotal.gemfire.demo.model.gf.pdx.CustomerOrder;
import io.pivotal.gemfire.demo.model.gf.pdx.Item;
import io.pivotal.gemfire.demo.model.io.CustomerIO;
import io.pivotal.gemfire.demo.model.io.CustomerOrderIO;
import io.pivotal.gemfire.demo.model.io.ItemIO;

public class CustomerOrderListFunction extends AbstractDataAwareFunction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2567344131414543904L;

	@Autowired
	private GemFireCache cache;

	@Resource(name = "customer")
	private Region<CustomerKey, Customer> customerRegion;

	@Resource(name = "customer-order")
	private Region<CustomerOrderKey, CustomerOrder> customerOrderRegion;

	@Resource(name = "item")
	private Region<ItemKey, Item> itemRegion;

	@Override
	boolean validateFilters(Set<?> filters) {
		Optional.ofNullable(filters).filter(s -> !s.isEmpty())
				.map(s -> s.stream().filter(v -> !(v instanceof CustomerKey)).count()).filter(count -> count == 0)
				.orElseThrow(() -> new IllegalArgumentException("invalid filters for CustomerOrderListFunction"));
		return true;
	}

	@Override
	boolean validateRequest(Object request) {
		// request validation is not required for this function
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	void process(RegionFunctionContext regionFunctionContext) {
		QueryService queryService = cache.getQueryService();
		String qstr = "SELECT * FROM /customer-order.entries entry";

		try {
			Query query = queryService.newQuery(qstr);
			SelectResults<Entry<CustomerOrderKey, CustomerOrder>> results = (SelectResults<Entry<CustomerOrderKey, CustomerOrder>>) query
					.execute(regionFunctionContext);
			List<Entry<CustomerOrderKey, CustomerOrder>> entryList = results.asList();

			CustomerOrderIO customerOrderIO = null;
			for (Entry<CustomerOrderKey, CustomerOrder> entry : entryList) {
				if (customerOrderIO != null) {
					regionFunctionContext.getResultSender().sendResult(customerOrderIO);
				}

				if (regionFunctionContext.getFilter().contains(entry.getKey().getCustomerKey())) {
					Customer customer = customerRegion.get(entry.getKey().getCustomerKey());
					CustomerIO customerIO = new CustomerIO(entry.getKey().getCustomerKey().getId(), customer.getName());

					customerOrderIO = new CustomerOrderIO();
					customerOrderIO.setId(entry.getKey().getId());
					customerOrderIO.setCustomer(customerIO);
					customerOrderIO.setShippingAddress(entry.getValue().getShippingAddress());
					customerOrderIO.setOrderDate(new Date(entry.getValue().getOrderDate()));

					Set<ItemIO> itemIOSet = new HashSet<ItemIO>();
					Set<String> itemSet = entry.getValue().getItemSet();
					for (String itemId : itemSet) {
						ItemKey itemKey = new ItemKey(itemId);
						Item item = itemRegion.get(itemKey);
						ItemIO itemIO = new ItemIO(itemId, item.getName(), item.getDescription(),
								new BigDecimal(item.getPrice()));

						itemIOSet.add(itemIO);
					}
					customerOrderIO.setItems(itemIOSet);
				}
			}

			// Send the result to function caller node.
			regionFunctionContext.getResultSender().lastResult(customerOrderIO);

		} catch (Exception e) {
			throw new FunctionException(e);
		}
	}

}
