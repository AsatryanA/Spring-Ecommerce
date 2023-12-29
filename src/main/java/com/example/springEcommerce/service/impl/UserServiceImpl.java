package com.example.springEcommerce.service.impl;


import com.example.springEcommerce.exception.ResourceNotFoundException;
import com.example.springEcommerce.mapper.UserMapper;
import com.example.springEcommerce.model.dto.UserRequestDTO;
import com.example.springEcommerce.model.dto.UserResponseDTO;
import com.example.springEcommerce.model.dto.UserUpdateDto;
import com.example.springEcommerce.model.entity.UserEntity;
import com.example.springEcommerce.repository.UserRepository;
import com.example.springEcommerce.service.UserService;
import com.example.springEcommerce.util.EmailSender;
import com.example.springEcommerce.util.MD5Encoder;
import com.example.springEcommerce.util.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailSender emailSender;




    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getById(Long id) {
        var userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserEntity not found"));
        return userMapper.toUserResponseDTO(userEntity);
    }

    @Transactional
    public UserResponseDTO update(UserUpdateDto userUpdateDto) {
        var user = userMapper.toUser(userUpdateDto);
        return userMapper.toUserResponseDTO(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getByEmail(String email) {
        var userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User by email %s not found", email)));
        return userMapper.toUserResponseDTO(userEntity);
    }

    @Override
    @Transactional
    public void verify(String email, String code) {
        var userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!Objects.equals(userEntity.getCode(), code))   {
            throw new IllegalArgumentException("Wrong Code number, Please try again")  ;
        }
         userEntity.setVerified(true);
        userRepository.save(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAll() {
        var allUsers = userRepository.findAll();
        return allUsers.stream().map(userMapper::toUserResponseDTO).toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


}
