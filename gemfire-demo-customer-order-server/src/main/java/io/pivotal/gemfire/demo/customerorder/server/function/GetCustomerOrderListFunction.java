package io.pivotal.gemfire.demo.customerorder.server.function;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.gemfire.LazyWiringDeclarableSupport;

import com.gemstone.gemfire.cache.Cache;
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

import io.pivotal.gemfire.demo.model.gf.key.CustomerKey;
import io.pivotal.gemfire.demo.model.gf.key.CustomerOrderKey;
import io.pivotal.gemfire.demo.model.gf.key.ItemKey;
import io.pivotal.gemfire.demo.model.gf.pdx.Customer;
import io.pivotal.gemfire.demo.model.gf.pdx.CustomerOrder;
import io.pivotal.gemfire.demo.model.gf.pdx.Item;
import io.pivotal.gemfire.demo.model.io.CustomerIO;
import io.pivotal.gemfire.demo.model.io.CustomerOrderIO;
import io.pivotal.gemfire.demo.model.io.ItemIO;

public class GetCustomerOrderListFunction extends LazyWiringDeclarableSupport implements Function, Declarable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2567344131414543904L;
	
	@Autowired
	private Cache cache;

	@Resource(name = "customer")
	private Region<CustomerKey, Customer> customerRegion;

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
		QueryService queryService = cache.getQueryService();
		String qstr = "SELECT * FROM /customer-order.entries entry";

		try {
			Query query = queryService.newQuery(qstr);
			SelectResults<Entry<CustomerOrderKey, CustomerOrder>> results = (SelectResults<Entry<CustomerOrderKey, CustomerOrder>>) query
					.execute(rfc);
			List<Entry<CustomerOrderKey, CustomerOrder>> entryList = results.asList();

			CustomerOrderIO customerOrderIO = null;
			for (Entry<CustomerOrderKey, CustomerOrder> entry : entryList) {
				if (customerOrderIO != null) {
					fc.getResultSender().sendResult(customerOrderIO);
				}

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

			// Send the result to function caller node.
			fc.getResultSender().lastResult(customerOrderIO);

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
