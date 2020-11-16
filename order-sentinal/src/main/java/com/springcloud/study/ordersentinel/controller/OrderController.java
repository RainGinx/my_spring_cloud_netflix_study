package com.springcloud.study.ordersentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.springcloud.study.ordersentinel.Product;
import com.springcloud.study.ordersentinel.feign.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    ProductFeignClient productFeignClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("get/{id}")
//    @SentinelResource(value = "order-server-get",blockHandler = "orderBlockHandler", fallback = "orderFallBack")
    public Product get(@PathVariable("id")Long id){
        /*if (id == 1L){
            String url = "http://product-server"+"/product/PRODUCT_ID";
            return restTemplate.getForObject(url.replace("PRODUCT_ID",String.valueOf(id)),Product.class);
        }else {
            return new Product();
        }*/
        String url = "http://product-server"+"/product/PRODUCT_ID";
        return restTemplate.getForObject(url.replace("PRODUCT_ID",String.valueOf(id)),Product.class);
    }

    @GetMapping(value = "/feign/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Product feign(@PathVariable Long id) {
       /* List<ServiceInstance> instances = discoveryClient.getInstances("product-server");
        ServiceInstance instance = instances.get(0);
        String url = "http://product-server"+"/product/PRODUCT_ID";
        Product product = null;
        for (int i = 0; i < 4; i++) {
            product =  restTemplate.getForObject(url.replace("PRODUCT_ID",String.valueOf(id)),Product.class);
            System.out.println(product);
        }*/

        return productFeignClient.getProductById(id);
    }

        public Product orderBlockHandler(Long id){
        Product product = new Product();
        product.setName("触发熔断降级方法");
        return product;
    }

    public Product orderFallBack(Long id){
        Product product = new Product();
        product.setName("触发异常降级方法");
        return product;
    }
}
