package com.springcloud.study.order.feign;

import com.springcloud.study.order.entity.Product;
import com.springcloud.study.order.feign.callback.ProductFeignClientCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 声明需要调用的微服务的名称
 *      name:服务提供者的名称
 *      fallback:配置熔断发生降级的方法实现类
 */
@FeignClient(name = "product-server",fallback = ProductFeignClientCallBack.class)
public interface ProductFeignClient {

    @RequestMapping(value = "/product/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Product getProductById(@PathVariable("id") Long id);

    @RequestMapping(value = "/product",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Product getById(@RequestParam("id") Long id);
}
