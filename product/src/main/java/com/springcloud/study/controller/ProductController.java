package com.springcloud.study.controller;

import com.springcloud.study.entity.Product;
import com.springcloud.study.response.ObjectRestResponse;
import com.springcloud.study.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Value("${spring.cloud.client.ip-address}")
    private String ip;
    @Value("${server.port}")
    private Integer port;

    @GetMapping(value = "/{id}")
    public Product getProductById(@PathVariable Long id){
       /* try {
            Thread.sleep(800L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        Product product = productService.findById(id);
        product.setName(port+"");
        return product;
    }

    @GetMapping()
    public Product getById(@RequestParam(value = "id") Long id){
        Product product = productService.findById(id);
        product.setName(port+"");
        return product;
    }
}
