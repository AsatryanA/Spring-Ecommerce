package com.example.springEcommerce.model.dto;

import com.example.springEcommerce.util.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UserRequestDTO {
    private String firstName;
    private String lastName;
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    private String phone;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDate dateOfBirth;
    private Gender gender;
}

