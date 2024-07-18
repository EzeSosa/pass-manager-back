package com.esosa.pass_manager.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PasswordRequest(
        @NotNull(message = "Name must not be null")
        @Size(max = 30, message = "Name size must be less than 30 characters")
        String name
) {}