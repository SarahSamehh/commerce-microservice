package com.example.product_service.service;

import com.example.product_service.dto.CreateProductRequest;
import com.example.product_service.entity.Product;
import com.example.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(CreateProductRequest request){
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        return productRepository.save(product);
    }

    public Product getProduct(Long id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
