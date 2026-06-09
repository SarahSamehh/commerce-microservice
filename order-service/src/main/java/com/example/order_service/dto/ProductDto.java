package com.example.order_service.dto;

public record ProductDto(
        Long id,
        String name,
        Double price,
        Integer stock
) {}