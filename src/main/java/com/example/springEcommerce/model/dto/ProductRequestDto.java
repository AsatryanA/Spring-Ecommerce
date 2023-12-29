package com.example.springEcommerce.model.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class ProductRequestDto extends ProductBaseDto{

    @NotNull
    private LocalDate productionDate;

}
