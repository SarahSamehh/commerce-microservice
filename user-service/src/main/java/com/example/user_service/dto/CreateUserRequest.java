package com.example.user_service.dto;

import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(@NotNull String name, @NotNull String email) {}
