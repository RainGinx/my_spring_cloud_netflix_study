package com.springcloud.study.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import com.springcloud.study.order.entity.Product;
import com.springcloud.study.order.feign.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
//@DefaultProperties(defaultFallback = "failFallBack")
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;//获取服务的元数据的工具类

    @Autowired
    private ProductFeignClient productFeignClient;

    @GetMapping(value = "/buy/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @HystrixCommand(fallbackMethod = )
    public Product buy(@PathVariable Long id){
//        Product product =  restTemplate.getForObject("http://localhost:9001/product/1",Product.class);
        return productFeignClient.getById(id);
    }

    @GetMapping(value = "/eureka/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object eureka(@PathVariable Long id){
        List<ServiceInstance> instances = discoveryClient.getInstances("product-server");
        ServiceInstance instance = instances.get(0);
        String url = "http://product-server"+"/product/PRODUCT_ID";
        Product product = null;
        for (int i = 0; i < 4; i++) {
            product =  restTemplate.getForObject(url.replace("PRODUCT_ID",String.valueOf(id)),Product.class);
            System.out.println(product);
        }

        return product;
    }

//    @HystrixCommand()
    @GetMapping(value = "/feign/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Product feign(@PathVariable Long id){
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


    public Product failFallBack(Long id){
        Product product = new Product();
        product.setName("请求失败");
        return product;
    }

    public Product failFallBack(){
        Product product = new Product();
        product.setName("请求失败");
        return product;
    }
}
