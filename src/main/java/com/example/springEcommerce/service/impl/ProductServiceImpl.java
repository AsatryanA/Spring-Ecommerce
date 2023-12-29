package com.example.springEcommerce.service.impl;

import com.example.springEcommerce.exception.ResourceNotFoundException;
import com.example.springEcommerce.mapper.ProductMapper;
import com.example.springEcommerce.model.dto.FilterProductDto;
import com.example.springEcommerce.model.dto.ProductRequestDto;
import com.example.springEcommerce.model.dto.ProductResponseDto;
import com.example.springEcommerce.model.dto.ProductUpdateDto;
import com.example.springEcommerce.model.entity.ProductEntity;
import com.example.springEcommerce.repository.ProductRepository;
import com.example.springEcommerce.service.ProductService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponseDto create(ProductRequestDto productRequestDto) {
        ProductEntity productEntity = productMapper.toEntity(productRequestDto);
        setProductDeadline(productEntity);
        ProductEntity save = productRepository.save(productEntity);
        return productMapper.toResponseDto(save);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getById(Integer id) {
        return productMapper.toResponseDto(productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("product not found")));
    }


    @Override
    @Transactional
    public ProductResponseDto update(Integer id, ProductUpdateDto productUpdateDto) {
        ProductEntity excistingProductEntity = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("product not found"));

        excistingProductEntity.setName(productUpdateDto.getName());
        excistingProductEntity.setCount(productUpdateDto.getCount());
        excistingProductEntity.setPrice(productUpdateDto.getPrice());
        excistingProductEntity.setCategory(productUpdateDto.getCategory());

        productRepository.save(excistingProductEntity);
        return productMapper.toResponseDto(excistingProductEntity);
    }

    @Override
    @Transactional
    public ProductResponseDto updatePart(Integer id, ProductUpdateDto productUpdateDto) {
        ProductEntity existingProd = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("product not found"));

        existingProd.setCompany(nonNull(productUpdateDto.getCompany()) ? productUpdateDto.getCompany() : existingProd.getCompany());
        existingProd.setName(nonNull(productUpdateDto.getName()) ? productUpdateDto.getName() : existingProd.getName());
        existingProd.setCategory(nonNull(productUpdateDto.getCategory()) ? productUpdateDto.getCategory() : existingProd.getCategory());
        existingProd.setPrice(nonNull(productUpdateDto.getPrice()) ? productUpdateDto.getPrice() : existingProd.getPrice());
        existingProd.setCount(productUpdateDto.getCount() != 0 ? productUpdateDto.getCount() : existingProd.getCount());
        productRepository.save(existingProd);
        return productMapper.toResponseDto(existingProd);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponseDto> getAll(PageRequest pageRequest) {
        Page<ProductEntity> all = productRepository.findAll(pageRequest);
        return all.stream().map(productMapper::toResponseDto).toList();
    }

    @Override
    public List<ProductResponseDto> filter(FilterProductDto filterProductDto) {

        Specification<ProductEntity> specification = Specification.where((root, criteriaQuery, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (nonNull(filterProductDto.getStartCount())) {
                Predicate isVerified = criteriaBuilder.greaterThan(root.get("count"), filterProductDto.getStartCount());
                predicates.add(isVerified);
            }
            if (nonNull(filterProductDto.getEndCount())) {
                Predicate startCount = criteriaBuilder.lessThan(root.get("count"), filterProductDto.getEndCount());
                predicates.add(startCount);
            }
            if (nonNull(filterProductDto.getStartPrice())) {
                Predicate startPrice = criteriaBuilder.greaterThan(root.get("price"), filterProductDto.getStartPrice());
                predicates.add(startPrice);
            }
            if (nonNull(filterProductDto.getEndPrice())) {
                Predicate endPrice = criteriaBuilder.lessThan(root.get("price"), filterProductDto.getEndPrice());
                predicates.add(endPrice);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        return productMapper.toResponseDtoList(productRepository.findAll(specification));
    }

    @Override
    public List<ProductResponseDto> search(String searchTxt) {

        Specification<ProductEntity> specification = Specification.where((root, criteriaQuery, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (nonNull(searchTxt)) {
                Predicate nameLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchTxt.toLowerCase() + "%");
                predicates.add(nameLike);

                Predicate lastNameLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("company")), "%" + searchTxt.toLowerCase() + "%");
                predicates.add(lastNameLike);

                Predicate emailLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("category")), "%" + searchTxt.toLowerCase() + "%");
                predicates.add(emailLike);
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        });
        return productMapper.toResponseDtoList(productRepository.findAll(specification));
    }

    private static void setProductDeadline(ProductEntity productEntity) {
        if (productEntity.getCategory().equalsIgnoreCase("grocery")) {
            if (LocalDate.now().getDayOfMonth() - productEntity.getProductionDate().getDayOfMonth() < 4)
                productEntity.setIsInDeadline(true);
            else
                productEntity.setIsInDeadline(false);
        }
    }
}
