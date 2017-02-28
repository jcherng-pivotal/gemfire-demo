package io.pivotal.gemfire.demo.server.customerorder.function;

import java.util.Set;

import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.FunctionException;
import org.apache.geode.cache.execute.RegionFunctionContext;
import org.springframework.data.gemfire.LazyWiringDeclarableSupport;

abstract class AbstractDataAwareFunction extends LazyWiringDeclarableSupport implements Function, Declarable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7096388001078867167L;

	abstract boolean validateFilters(Set<?> filters);

	abstract boolean validateRequest(Object request);

	abstract void process(RegionFunctionContext regionFunctionContext);

	@Override
	public void execute(FunctionContext functionContext) {
		if (!(functionContext instanceof RegionFunctionContext)) {
			throw new FunctionException(
					"This is a data aware function, and has to be called using FunctionService.onRegion.");
		}
		RegionFunctionContext regionFunctionContext = (RegionFunctionContext) functionContext;
		Set<?> filters = regionFunctionContext.getFilter();
		Object request = regionFunctionContext.getArguments();
		if (validateFilters(filters) && validateRequest(request)) {
			process(regionFunctionContext);
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
