package com.example.springEcommerce.mapper;


import com.example.springEcommerce.model.dto.ProductDetails;
import com.example.springEcommerce.model.dto.ProductRequestDto;
import com.example.springEcommerce.model.dto.ProductResponseDto;
import com.example.springEcommerce.model.entity.ProductEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductEntity toEntity (ProductRequestDto productRequestDto);

    ProductDetails toProductDetails(ProductEntity productEntity);

    ProductResponseDto toResponseDto(ProductEntity productEntity);

    List<ProductResponseDto> toResponseDtoList(List<ProductEntity> productEntities);

}
