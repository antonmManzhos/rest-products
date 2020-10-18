package com.products.api.dto;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Entity
@Table(name = "Products")
public class ProductsDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long Id;

    @Setter
//    @NotEmpty(message = "error.name.empty")
//    @Length(max = 50, message = "error.name.length")
    @NotEmpty
    @Length(max = 50)
    @Column(name = "name")
    private String name;

    @Setter
    //@NotEmpty(message = "error.price.empty")
    //@Length(max = 50, message = "error.price.length")
    @Column(name = "price")
    private float price;

    @Setter
    //@NotEmpty(message = "error.date.empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private Date date;
}
