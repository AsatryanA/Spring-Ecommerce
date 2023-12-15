package com.example.springEcommerce.controller;

import com.example.springEcommerce.model.dto.UserRequestDTO;
import com.example.springEcommerce.model.dto.UserResponseDTO;
import com.example.springEcommerce.model.entity.UserEntity;
import com.example.springEcommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(userService.register(userRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping
    ResponseEntity<UserEntity> update(@RequestBody UserEntity userEntity) {
        return new ResponseEntity<>(userService.update(userEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<UserEntity> getByEmail(@RequestParam String email) {
        return new ResponseEntity<>(userService.getByEmail(email), HttpStatus.OK);


        /*    @GetMapping
    ResponseEntity<List<UserEntity>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }*/
    }
}