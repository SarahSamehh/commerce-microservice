package com.example.product_service.controller;


import com.example.product_service.dto.CreateProductRequest;
import com.example.product_service.entity.Product;
import com.example.product_service.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody CreateProductRequest request){
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProduct(id));
    }
}
