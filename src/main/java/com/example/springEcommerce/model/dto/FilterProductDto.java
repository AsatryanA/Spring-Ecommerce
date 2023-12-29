package com.example.springEcommerce.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public  class FilterProductDto {
    private Integer startCount;
    private Integer endCount;
    private Double startPrice;
    private Double endPrice;
}