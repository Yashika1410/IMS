package com.example.inventory.ims.services;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.inventory.ims.dto.request.CreateProductRequest;
import com.example.inventory.ims.dto.request.UpdateProductRequest;
import com.example.inventory.ims.dto.response.OrderEnum;
import com.example.inventory.ims.entities.Product;
import com.example.inventory.ims.entities.User;
import com.example.inventory.ims.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(CreateProductRequest productRequest,User user) {
        Product product = Product.builder()
                                 .description(productRequest.getDescription())
                                 .name(productRequest.getName())
                                 .price(productRequest.getPrice())
                                 .stockQuantity(productRequest.getStockQuantity())
                                 .build();
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setIsDeleted(false);
        product.setCreatedByUserId(user.getId());
        return productRepository.save(product);
    }
    
    public Product getProductById(Long id){
        return productRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found by id: "+id));
    }

    public Product updateProductById(Long id,UpdateProductRequest updateProductRequest,User user){
        Product product = getProductById(id);
        checkPermission(user, product);
        product.setDescription(updateProductRequest.getDescription());
        product.setName(updateProductRequest.getName());
        product.setPrice(updateProductRequest.getPrice());
        product.setStockQuantity(updateProductRequest.getStockQuantity());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    private void checkPermission(User user, Product product) {
        if(product.getCreatedByUserId()!=user.getId())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You don't have permission to update this product");
    }

    public String deleteProductById(Long id,User user){
        Product product = getProductById(id);
        checkPermission(user, product);
        product.setIsDeleted(true);
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
        return "Successfully deleted the product by id: "+ id;
    }

    public Page<Product> getAllProducts(int page, int pageSize, OrderEnum order) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(order.equals(OrderEnum.ASC)?Direction.ASC:Direction.DESC, "updatedAt"));
        return productRepository.findAllByIsDeletedFalse(pageable);
    }
}
