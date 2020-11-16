package com.springcloud.study.ordersentinel.feign.callback;

import com.springcloud.study.ordersentinel.Product;
import com.springcloud.study.ordersentinel.feign.ProductFeignClient;
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
