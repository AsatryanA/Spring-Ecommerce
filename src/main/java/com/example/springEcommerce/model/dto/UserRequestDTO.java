package com.example.springEcommerce.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDTO {
    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String name;
    private String email;
    private String password;
    private int age;
}

