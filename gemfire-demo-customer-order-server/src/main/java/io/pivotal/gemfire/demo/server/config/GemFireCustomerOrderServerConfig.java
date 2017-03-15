package io.pivotal.gemfire.demo.server.config;

import java.util.Arrays;

import org.apache.geode.cache.CacheLoader;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.execute.Function;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.gemfire.FunctionServiceFactoryBean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.pivotal.gemfire.demo.db.CustomerOrderDBApplication;
import io.pivotal.gemfire.demo.model.gf.key.CustomerKey;
import io.pivotal.gemfire.demo.model.gf.key.CustomerOrderKey;
import io.pivotal.gemfire.demo.model.gf.key.ItemKey;
import io.pivotal.gemfire.demo.model.gf.pdx.Customer;
import io.pivotal.gemfire.demo.model.gf.pdx.CustomerOrder;
import io.pivotal.gemfire.demo.model.gf.pdx.Item;
import io.pivotal.gemfire.demo.server.customerorder.cacheloader.CustomerCacheLoader;
import io.pivotal.gemfire.demo.server.customerorder.function.CustomerOrderListFunction;
import io.pivotal.gemfire.demo.server.customerorder.function.CustomerOrderPriceFunction;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"io.pivotal.gemfire.demo.server"})
@Import({CustomerOrderDBApplication.class})
@EnableTransactionManagement
public class GemFireCustomerOrderServerConfig {

    @Bean("customer")
    PartitionedRegionFactoryBean<CustomerKey, Customer> customerRegion(final GemFireCache cache,
                                                                       final CacheLoader<CustomerKey, Customer> customerCacheLoader) {
        PartitionedRegionFactoryBean<CustomerKey, Customer> customerRegion = new PartitionedRegionFactoryBean<>();
        customerRegion.setCache(cache);
        customerRegion.setClose(false);
        customerRegion.setName("customer");
        customerRegion.setCacheLoader(customerCacheLoader);
        return customerRegion;
    }

    @Bean("customer-order")
    PartitionedRegionFactoryBean<CustomerOrderKey, CustomerOrder> customerOrderRegion(final GemFireCache cache) {
        PartitionedRegionFactoryBean<CustomerOrderKey, CustomerOrder> customerOrderRegion = new PartitionedRegionFactoryBean<>();
        customerOrderRegion.setCache(cache);
        customerOrderRegion.setClose(false);
        customerOrderRegion.setName("customer-order");
        return customerOrderRegion;
    }

    @Bean("item")
    PartitionedRegionFactoryBean<ItemKey, Item> itemRegion(final GemFireCache cache) {
        PartitionedRegionFactoryBean<ItemKey, Item> itemRegion = new PartitionedRegionFactoryBean<>();
        itemRegion.setCache(cache);
        itemRegion.setClose(false);
        itemRegion.setName("item");
        return itemRegion;
    }

    //We can programatically add regions to any base server by deploying jar. For example if I wanted to create a test region
    @Bean("sizer")
    PartitionedRegionFactoryBean<String, String> sizerRegion(final GemFireCache cache) {
        PartitionedRegionFactoryBean<String, String> sizerRegion = new PartitionedRegionFactoryBean<>();
        sizerRegion.setCache(cache);
        sizerRegion.setClose(false);
        sizerRegion.setName("sizer");
        return sizerRegion;
    }


    @Bean
    CacheLoader<CustomerKey, Customer> customerCacheLoader() {
        return new CustomerCacheLoader();
    }

    @Bean
    FunctionServiceFactoryBean functionService(final Function customerOrderPriceFunction,
                                               final Function customerOrderListFunction) {
        FunctionServiceFactoryBean functionService = new FunctionServiceFactoryBean();
        functionService
                .setFunctions(Arrays.asList(new Function[]{customerOrderPriceFunction, customerOrderListFunction}));
        return functionService;
    }

    @Bean
    Function customerOrderPriceFunction() {
        CustomerOrderPriceFunction customerOrderPriceFunction = new CustomerOrderPriceFunction();
        return customerOrderPriceFunction;
    }

    @Bean
    Function customerOrderListFunction() {
        CustomerOrderListFunction customerOrderListFunction = new CustomerOrderListFunction();
        return customerOrderListFunction;
    }

}
