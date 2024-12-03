package com.example.inventory.ims.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory.ims.dto.request.CreateProductRequest;
import com.example.inventory.ims.dto.request.UpdateProductRequest;
import com.example.inventory.ims.dto.response.OrderEnum;
import com.example.inventory.ims.dto.response.PaginatedResponse;
import com.example.inventory.ims.dto.response.ProductResponse;
import com.example.inventory.ims.entities.Product;
import com.example.inventory.ims.services.ProductService;
import com.example.inventory.ims.utils.ServletUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping(path ="/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody CreateProductRequest productRequest) {
       return ProductResponse.toResponse(productService.createProduct(productRequest, ServletUtil.getCurrentUser()));
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable("id") Long id) {
        return ProductResponse.toResponse(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable("id") Long id,@Valid @RequestBody UpdateProductRequest updateProductRequest) {
       return ProductResponse.toResponse(productService.updateProductById(id, updateProductRequest,ServletUtil.getCurrentUser()));
    }

    @DeleteMapping("/{id}")
    public String deleteProductById(@PathVariable("id") Long id) {
        return productService.deleteProductById(id, ServletUtil.getCurrentUser());
    }

    @GetMapping
    public PaginatedResponse<ProductResponse> getMethodName(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(name = "order", required = false, defaultValue = "DESC") OrderEnum orderEnum) {
        Page<Product> products = productService.getAllProducts(page,pageSize,orderEnum);
        return PaginatedResponse.<ProductResponse>builder()
                                .list(products.stream()
                                .map(p->ProductResponse.toResponse(p)).toList())
                                .page(products.getPageable().getPageNumber())
                                .pageSize(products.getPageable().getPageSize())
                                .total(products.getNumberOfElements())
                                .totalPages(products.getTotalPages())
                                .build();
    }
    
    
    
    
    
}
