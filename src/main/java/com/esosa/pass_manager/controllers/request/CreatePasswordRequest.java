package com.esosa.pass_manager.controllers.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreatePasswordRequest(
        @NotNull
        @Size(min = 2, max = 30, message = "Password name must be between 2 and 30 characters")
        String name,

        @NotNull
        UUID userId
) {}