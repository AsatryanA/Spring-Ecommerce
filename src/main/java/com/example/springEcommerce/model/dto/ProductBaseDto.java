package com.example.springEcommerce.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductBaseDto {

    private String name;
    private String company;
    private BigDecimal price;
    private String category;
    private int count;

}
