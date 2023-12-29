package com.example.springEcommerce.controller;

import com.example.springEcommerce.model.dto.LoginDTO;
import com.example.springEcommerce.model.dto.UserRequestDTO;
import com.example.springEcommerce.model.dto.UserResponseDTO;
import com.example.springEcommerce.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping
    ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(authService.register(userRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDTO) {
        return new ResponseEntity<>(authService.login(loginDTO.getUsername(), loginDTO.getPassword()), HttpStatus.OK);
    }
}
