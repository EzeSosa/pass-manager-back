package com.esosa.pass_manager.controllers.response;

import java.util.UUID;

public record AuthResponse(
        UUID userId,
        String accessToken
) {}