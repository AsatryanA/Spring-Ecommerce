package com.example.springEcommerce.service;


import com.example.springEcommerce.model.dto.UserRequestDTO;
import com.example.springEcommerce.model.dto.UserResponseDTO;
import com.example.springEcommerce.model.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserResponseDTO register(UserRequestDTO userRequestDTO);

    UserEntity login(String username, String password);

    UserResponseDTO getById(Long id);

    List<UserEntity> getAll();

    void delete(Long id);
    UserEntity update(UserEntity userEntity);
     UserEntity getByEmail(String email);
}
