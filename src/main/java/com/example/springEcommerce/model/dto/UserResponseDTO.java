package com.example.springEcommerce.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UserResponseDTO {
    private Long id;
    private String email;
    private LocalDate dateOfBirth;
}
