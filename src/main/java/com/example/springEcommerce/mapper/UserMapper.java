package com.example.springEcommerce.mapper;

import com.example.springEcommerce.model.dto.UserRequestDTO;
import com.example.springEcommerce.model.dto.UserResponseDTO;
import com.example.springEcommerce.model.dto.UserUpdateDto;
import com.example.springEcommerce.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toUser(UserRequestDTO userRequestDTO);
    UserEntity toUser(UserUpdateDto userUpdateDto);

    UserResponseDTO toUserResponseDTO(UserEntity userEntity);


}


