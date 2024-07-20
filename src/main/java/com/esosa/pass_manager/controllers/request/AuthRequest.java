package com.esosa.pass_manager.controllers.request;

import jakarta.validation.constraints.NotNull;

public record AuthRequest(
        @NotNull
        String username,

        @NotNull
        String password
) {}