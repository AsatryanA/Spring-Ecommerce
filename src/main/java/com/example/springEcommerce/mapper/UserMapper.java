package com.example.springEcommerce.mapper;

import com.example.springEcommerce.model.dto.UserRequestDTO;
import com.example.springEcommerce.model.dto.UserResponseDTO;
import com.example.springEcommerce.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "lastName", source = "name")
    UserEntity toUser(UserRequestDTO userRequestDTO);

    UserResponseDTO toUserResponseDTO(UserEntity userEntity);


}


