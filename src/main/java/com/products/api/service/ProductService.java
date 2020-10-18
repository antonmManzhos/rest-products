package com.products.api.service;

import com.products.api.dto.ProductsDTO;
import com.products.api.repository.ProductsJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductsJpaRepository productsJpaRepository;

    public List<ProductsDTO> getAllProducts() {
        return productsJpaRepository.findAll();
    }

    public ProductsDTO findProductById(Long id) {
        return productsJpaRepository.findById(id).orElse(null);
    }

    public ProductsDTO findProductByName(String name) {
        return productsJpaRepository.findByName(name);
    }

    public void createProduct(ProductsDTO product) {
        productsJpaRepository.saveAndFlush(product);
    }

    public void updateProduct(ProductsDTO productFromRequest, ProductsDTO currentProduct) {
        currentProduct.setName(productFromRequest.getName());
        currentProduct.setPrice(productFromRequest.getPrice());
        currentProduct.setDate(productFromRequest.getDate());
        createProduct(currentProduct);
    }

    public void deleteProductById(Long id) {
        productsJpaRepository.deleteById(id);
    }
}
