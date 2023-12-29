package com.example.springEcommerce.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class LoginDTO {
    @NonNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;

}
