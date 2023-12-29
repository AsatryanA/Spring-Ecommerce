package com.example.springEcommerce.controller;


import com.example.springEcommerce.model.dto.FilterProductDto;
import com.example.springEcommerce.model.dto.ProductRequestDto;
import com.example.springEcommerce.model.dto.ProductResponseDto;
import com.example.springEcommerce.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable @Positive Integer id) {
        ProductResponseDto productById = productService.getById(id);
        return ResponseEntity.ok(productById);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> create(@RequestBody @Valid ProductRequestDto productRequestDto) {
        var productResponseDto = productService.create(productRequestDto);
        return ResponseEntity.ok(productResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Integer id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                           @RequestParam(required = false, defaultValue = "2") Integer size,
                                                           @RequestParam String sort,
                                                           @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection
                                                           ) {
        var pageRequest = PageRequest.ofSize(size).withPage(page).withSort(Sort.by(sortDirection,sort));
        return new ResponseEntity<>(productService.getAll(pageRequest), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductResponseDto>> filter(@RequestBody FilterProductDto filterProductDto){
        return new ResponseEntity<>(productService.filter(filterProductDto), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> search(@RequestParam String searchTxt){
        return new ResponseEntity<>(productService.search(searchTxt),HttpStatus.OK);
    }
}
