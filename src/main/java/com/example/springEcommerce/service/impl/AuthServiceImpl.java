package com.example.springEcommerce.service.impl;

import com.example.springEcommerce.feign.NotificationClient;
import com.example.springEcommerce.mapper.UserMapper;
import com.example.springEcommerce.model.dto.NotificationDTO;
import com.example.springEcommerce.model.dto.UserRequestDTO;
import com.example.springEcommerce.model.dto.UserResponseDTO;
import com.example.springEcommerce.model.entity.RoleEntity;
import com.example.springEcommerce.model.entity.UserEntity;
import com.example.springEcommerce.repository.UserRepository;
import com.example.springEcommerce.service.AuthService;
import com.example.springEcommerce.util.EmailSender;
import com.example.springEcommerce.util.RandomGenerator;
import com.example.springEcommerce.util.constants.Role;
import com.example.springEcommerce.util.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final EmailSender emailSender;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final NotificationClient notificationClient;
    private final HttpServletRequest request;

    @Override
    @Transactional

    public UserResponseDTO register(UserRequestDTO userRequestDTO) {
        var userEntity = userMapper.toUser(userRequestDTO);
        setUserData(userRequestDTO, userEntity);
        UserEntity save = userRepository.save(userEntity);
        String token = request.getHeader("Authorization");
       notificationClient.notify(token, new NotificationDTO(userEntity.getCode()));
        //   emailSender.sendSimpleMessage(userEntity.getEmail(), "Vareification Code", userEntity.getCode());
        return userMapper.toUserResponseDTO(save);
    }


    @Override
    @Transactional
    public String login(String email, String password) {
        var authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        var user = (UserEntity) authenticate.getPrincipal();
        return jwtProvider.generateAccessToken(user.getId(), user.getUsername(), user.getRole().getName());
    }


    private void setUserData(UserRequestDTO userRequestDTO, UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        if (userRequestDTO.getDateOfBirth() != null) {
            var age = LocalDate.now().getYear() - userRequestDTO.getDateOfBirth().getYear();
            userEntity.setAge(age);
        }
        userEntity.setRole(RoleEntity.builder().id(2L).name(Role.USER).build());
        userEntity.setCode(RandomGenerator.generateCode(6));
        userEntity.setVerified(false);
        userEntity.setActive(false);
    }
}
