package com.springcloud.study.service.Impl;

import com.springcloud.study.dao.ProductDao;
import com.springcloud.study.entity.Product;
import com.springcloud.study.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product findById(Long id) {
        Optional<Product> product = productDao.findById(id);
        return product.orElse(null);
    }

    @Override
    public void save(Product product) {

    }

    @Override
    public void update(Product product) {

    }

    @Override
    public void delete(Product product) {

    }
}
