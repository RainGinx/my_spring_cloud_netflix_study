package com.springcloud.study.service;

import com.springcloud.study.entity.Product;

public interface ProductService {
    Product findById(Long id);
    void save(Product product);
    void update(Product product);
    void delete(Product product);
}
