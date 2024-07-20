package com.esosa.pass_manager.controllers.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PasswordRequest(
        @NotNull
        @Size(min = 2, max = 30)
        String name,

        @NotNull
        UUID userId
) {}