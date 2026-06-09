package com.example.product_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateProductRequest(@NotNull String name, @NotNull String description, @NotNull Double price, @Min(0)Integer stock) {}
