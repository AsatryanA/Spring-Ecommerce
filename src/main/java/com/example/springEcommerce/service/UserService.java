package com.example.springEcommerce.service;


import com.example.springEcommerce.model.dto.UserRequestDTO;
import com.example.springEcommerce.model.dto.UserResponseDTO;
import com.example.springEcommerce.model.dto.UserUpdateDto;
import com.example.springEcommerce.model.entity.UserEntity;

import java.util.List;

public interface UserService {



    UserResponseDTO getById(Long id);

    List<UserResponseDTO> getAll();

    void delete(Long id);
    UserResponseDTO update(UserUpdateDto userUpdateDto);
    UserResponseDTO getByEmail(String email);

    void verify(String email, String code);
}
