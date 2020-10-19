package com.products.api.dto;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Products")
public class ProductsDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long Id;

    @NotEmpty(message = "error.name.empty")
    @Length(max = 50, message = "error.name.length")
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    @Min(value = 0L, message = "error.price.value" )
    private float price;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "error.date.empty")
    @Column(name = "date")
    private Date date;
}
