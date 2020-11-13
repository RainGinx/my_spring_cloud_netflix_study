package com.springcloud.study.order.feign.callback;

import com.springcloud.study.order.entity.Product;
import com.springcloud.study.order.feign.ProductFeignClient;
import org.springframework.stereotype.Component;

/**
 * 这里实现的方法就是feign使用hystrix的熔断降级方法
 */
@Component
public class ProductFeignClientCallBack implements ProductFeignClient {

    @Override
    public Product getProductById(Long id) {
        Product product = new Product();
        product.setName("熔断降级方法");
        return product;
    }

    @Override
    public Product getById(Long id) {
        Product product = new Product();
        product.setName("熔断降级方法");
        return product;
    }
}
