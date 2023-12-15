package com.example.springEcommerce.service.impl;


import com.example.springEcommerce.mapper.UserMapper;
import com.example.springEcommerce.model.dto.UserRequestDTO;
import com.example.springEcommerce.model.dto.UserResponseDTO;
import com.example.springEcommerce.model.entity.UserEntity;
import com.example.springEcommerce.repository.UserRepository;
import com.example.springEcommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponseDTO register(UserRequestDTO userRequestDTO) {
        UserEntity userEntity = userMapper.toUser(userRequestDTO);
        userEntity.setVerified(false);
        userEntity.setActive(false);
        UserEntity save = userRepository.save(userEntity);
        return userMapper.toUserResponseDTO(save);
    }

    @Override
    public UserEntity login(String username, String password) {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity != null && userEntity.getPassword().equals(password)) {
            return userEntity;
        }
        throw new RuntimeException("Wrong username or password");
    }

    @Override
    @Transactional(readOnly = true)
    public  UserResponseDTO getById(Long id) {
        var userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserEntity not found"));
        return userMapper.toUserResponseDTO(userEntity);
    }

    @Transactional
    public UserEntity update(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {

        userRepository.deleteById(id);
    }
}
