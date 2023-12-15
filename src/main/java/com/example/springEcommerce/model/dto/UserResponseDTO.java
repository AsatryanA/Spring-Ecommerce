package com.example.springEcommerce.model.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
}
