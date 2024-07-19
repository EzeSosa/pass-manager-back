package com.esosa.pass_manager.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PasswordRequest(
        @NotNull
        @Size(min = 2, max = 30)
        String name
) {}