package com.products.api.controller;

import com.products.api.dto.ProductsDTO;
import com.products.api.repository.ProductsJpaRepository;
import com.products.api.service.ProductService;
//import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @Spy
    private ProductController productController;
    @Mock
    private ProductsJpaRepository productsJpaRepository;
    @Mock
    private ProductService productService;
    @BeforeEach
    public void setup() {
        productController = new ProductController();
        ReflectionTestUtils.setField(productController,
                "productsJpaRepository", productsJpaRepository);
    }

    @Test
    void testListAllProducts() {
        List<ProductsDTO> productsDTOList = new ArrayList<>();
        productsDTOList.add(new ProductsDTO());

        when(this.productsJpaRepository.findAll()).thenReturn(productsDTOList);

        ResponseEntity<List<ProductsDTO>> responseEntity = this.productController.listAllProducts();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    void getProductById() {
        var testProduct = new ProductsDTO();
        testProduct.setId(1L);
        testProduct.setName("test product 1");

        when(this.productService.findProductById(1l)).thenReturn(testProduct);

        ResponseEntity<ProductsDTO> responseEntity = this.productController.getProductById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, Objects.requireNonNull(responseEntity.getBody()).getId());
    }

    @Test
    void createProduct() {
        var testProduct = new ProductsDTO();
        testProduct.setId(1L);
        testProduct.setName("test product 1");
        testProduct.setPrice(99);

        when(this.productsJpaRepository.saveAndFlush(testProduct)).thenReturn(testProduct);

        ResponseEntity<ProductsDTO> responseEntity = this.productController.createProduct(testProduct);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1L, Objects.requireNonNull(responseEntity.getBody()).getId());
    }

    @Test
    void updateUser() {
        var testProduct = new ProductsDTO();
        testProduct.setId(1L);
        testProduct.setName("test product 1");
        testProduct.setPrice(99);

        when(this.productsJpaRepository.saveAndFlush(testProduct)).thenReturn(testProduct);

        ResponseEntity<ProductsDTO> responseEntity = this.productController.createProduct(testProduct);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1L, Objects.requireNonNull(responseEntity.getBody()).getId());
    }

    @Test
    void deleteProduct() {
        var testProduct = new ProductsDTO();
        testProduct.setId(1L);
        testProduct.setName("test product 1");

        when(this.productService.findProductById(1l)).thenReturn(testProduct);

        ResponseEntity<ProductsDTO> responseEntity = this.productController.getProductById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}