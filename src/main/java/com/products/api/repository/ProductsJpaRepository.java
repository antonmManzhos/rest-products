package com.products.api.repository;

import com.products.api.dto.ProductsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsJpaRepository extends JpaRepository<ProductsDTO, Long> {
    public ProductsDTO findByName(String name);
}
