package com.example.springEcommerce.service.impl;

import com.example.springEcommerce.exception.ResourceNotFoundException;
import com.example.springEcommerce.mapper.UserMapper;
import com.example.springEcommerce.model.dto.UserRequestDTO;
import com.example.springEcommerce.model.dto.UserResponseDTO;
import com.example.springEcommerce.model.entity.UserEntity;
import com.example.springEcommerce.repository.UserRepository;
import com.example.springEcommerce.service.AuthService;
import com.example.springEcommerce.util.EmailSender;
import com.example.springEcommerce.util.RandomGenerator;
import com.example.springEcommerce.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final EmailSender emailSender;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional

    public UserResponseDTO register(UserRequestDTO userRequestDTO) {
        var userEntity = userMapper.toUser(userRequestDTO);
        setUserData(userRequestDTO, userEntity);
        UserEntity save = userRepository.save(userEntity);
        emailSender.sendSimpleMessage(userEntity.getEmail(), "Vareification Code", userEntity.getCode());
        return userMapper.toUserResponseDTO(save);
    }


    @Override
    @Transactional
    public String login(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        UserEntity user = userRepository.findByEmail(email).orElseThrow();
        return jwtProvider.generateAccessToken(user.getId(),user.getUsername());
    }


    private void setUserData(UserRequestDTO userRequestDTO, UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        if (userRequestDTO.getDateOfBirth() != null) {
            var age = LocalDate.now().getYear() - userRequestDTO.getDateOfBirth().getYear();
            userEntity.setAge(age);
        }
        userEntity.setCode(RandomGenerator.generateCode(6));
        userEntity.setVerified(false);
        userEntity.setActive(false);
    }
}
