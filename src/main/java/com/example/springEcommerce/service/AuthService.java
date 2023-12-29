package com.example.springEcommerce.service;

import com.example.springEcommerce.model.dto.UserRequestDTO;
import com.example.springEcommerce.model.dto.UserResponseDTO;

public interface AuthService {
    UserResponseDTO register(UserRequestDTO userRequestDTO);

    String login(String username, String password);
}
