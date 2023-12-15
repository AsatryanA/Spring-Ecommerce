package com.example.springEcommerce.service.impl;


import com.example.springEcommerce.exception.UserNotFoundException;
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

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailSender emailSender;

    @Override
    @Transactional
    public UserResponseDTO register(UserRequestDTO userRequestDTO) {
        var userEntity = userMapper.toUser(userRequestDTO);
        setUserData(userRequestDTO, userEntity);
        emailSender.sendSimpleMessage(userEntity.getEmail(),"Vareification Code",userEntity.getCode());
        return userMapper.toUserResponseDTO(userRepository.save(userEntity));
    }

    @Override
    @Transactional
    public UserResponseDTO login(String email, String password) {
        var userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User by email %s not found", email)));
        if (userEntity.getPassword().equals(password)) {
            return userMapper.toUserResponseDTO(userEntity);
        }
        throw new RuntimeException("Wrong username or password");
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getById(Long id) {
        var userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserEntity not found"));
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
                .orElseThrow(() -> new UserNotFoundException(String.format("User by email %s not found", email)));
        return userMapper.toUserResponseDTO(userEntity);
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

    private void setUserData(UserRequestDTO userRequestDTO, UserEntity userEntity) {
        userEntity.setPassword(MD5Encoder.encode(userRequestDTO.getPassword()));
        if (userRequestDTO.getDateOfBirth() != null) {
            var age = LocalDate.now().getYear() - userRequestDTO.getDateOfBirth().getYear();
            userEntity.setAge(age);
        }
        userEntity.setCode(RandomGenerator.generateCode(6));
        userEntity.setVerified(false);
        userEntity.setActive(false);
    }
}
