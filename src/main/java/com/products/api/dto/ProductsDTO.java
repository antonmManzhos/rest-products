package com.products.api.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Entity
@Table(name = "Products")
public class ProductsDTO {

    @Id
    @GeneratedValue
    @Column(name="Id")
    private Long Id;

    @Setter
    @NotEmpty(message = "error.name.empty")
    @Length(max = 50, message = "error.name.length")
    @Column(name = "name")
    private String name;

    @Setter
    @NotEmpty(message = "error.price.empty")
    @Length(max = 50, message = "error.price.length")
    @Column(name = "price")
    private String price;

    @Setter
    @NotEmpty(message = "error.date.empty")
    @Column(name = "Date")
    private Timestamp date;
}
