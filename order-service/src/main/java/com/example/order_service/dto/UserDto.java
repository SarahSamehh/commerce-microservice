package com.example.order_service.dto;

public record UserDto(
    Long id,
    String name,
    String email
) {}