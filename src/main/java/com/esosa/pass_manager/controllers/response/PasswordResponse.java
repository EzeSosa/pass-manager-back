package com.esosa.pass_manager.controllers.response;

import java.util.UUID;

public record PasswordResponse(
        UUID id,
        String name,
        String password
) {}