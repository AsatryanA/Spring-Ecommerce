package com.example.springEcommerce.service;



import com.example.springEcommerce.model.dto.FilterProductDto;
import com.example.springEcommerce.model.dto.ProductRequestDto;
import com.example.springEcommerce.model.dto.ProductResponseDto;
import com.example.springEcommerce.model.dto.ProductUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductService {

    ProductResponseDto create(ProductRequestDto productRequestDto);

    ProductResponseDto getById(Integer id);


    ProductResponseDto update(Integer id, ProductUpdateDto productUpdateDto);

    ProductResponseDto updatePart(Integer id, ProductUpdateDto productUpdateDto);
    void delete(Integer id);


    List<ProductResponseDto> getAll(PageRequest pageRequest);

    List<ProductResponseDto> filter(FilterProductDto filterProductDto);

    List<ProductResponseDto> search(String searchTxt);
}
