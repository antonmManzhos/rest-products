package com.products.api.service;

import com.products.api.repository.ProductsJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductsJpaRepository productsJpaRepository;
}
