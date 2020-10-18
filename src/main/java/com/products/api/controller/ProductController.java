package com.products.api.controller;

import com.products.api.Exception.CustomErrorType;
import com.products.api.dto.ProductsDTO;
import com.products.api.repository.ProductsJpaRepository;
import com.products.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<ProductsDTO>> listAllProducts() {

        var allProducts = productService.getAllProducts();

        if (allProducts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ProductsDTO> getProductById(@PathVariable("id") final Long id) {

        var product = productService.findProductById(id);

        if (product == null) {
            return new ResponseEntity<>(
                    new CustomErrorType("Product with id" + id + " not found"), HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ProductsDTO> createProduct(@Valid @RequestBody final ProductsDTO product) {

        if (productService.findProductByName(product.getName()) != null) {
            return new ResponseEntity<>(
                    new CustomErrorType("Unable to create new product. A product with name " +
                            product.getName() + "already exist."), HttpStatus.CONFLICT);
        }

        productService.createProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductsDTO> updateUser(
            @PathVariable("id") final Long id, @RequestBody ProductsDTO productFromRequest) {

        var currentProduct = productService.findProductById(id);

        if (currentProduct == null) {
            return new ResponseEntity<>(
                    new CustomErrorType("Unable to upate. Product with id "
                            + id + " not found."), HttpStatus.NOT_FOUND);
        }

        productService.updateProduct(productFromRequest, currentProduct);

        return new ResponseEntity<>(productFromRequest, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductsDTO> deleteProduct(@PathVariable("id") final Long id) {

        var currentProduct = productService.findProductById(id);

        if (currentProduct == null) {
            return new ResponseEntity<>(new CustomErrorType(
                    "Unable to delete. Product with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }

        productService.deleteProductById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}