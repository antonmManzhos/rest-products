package com.products.api.controller;

import com.products.api.dto.ProductsDTO;
import com.products.api.repository.ProductsJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductsJpaRepository productsJpaRepository;

    @GetMapping("/")
    public ResponseEntity<List<ProductsDTO>> listAllUsers() {
        List<ProductsDTO> users = productsJpaRepository.findAll();
        return new ResponseEntity<List<ProductsDTO>>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ProductsDTO> getProductById(@PathVariable("id") final Long id) {
        ProductsDTO product = productsJpaRepository.findById(id).orElse(null);
        return new ResponseEntity<ProductsDTO>(product, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ProductsDTO> createUser(@RequestBody final ProductsDTO user) {
        productsJpaRepository.save(user);
        return new ResponseEntity<ProductsDTO>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductsDTO> deleteUser(@PathVariable("id") final Long id) {
        productsJpaRepository.deleteById(id);
        return new ResponseEntity<ProductsDTO>(HttpStatus.NO_CONTENT);
    }
}
