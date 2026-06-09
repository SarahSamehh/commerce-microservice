package com.example.order_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
       @NotNull Long userId,
       @NotNull Long productId,
       @Min(1) Integer quantity
) {}