package com.esosa.pass_manager.services.interfaces;

import com.esosa.pass_manager.controllers.request.AuthRequest;
import com.esosa.pass_manager.controllers.response.AuthResponse;

public interface IAuthService {
    void register(AuthRequest authRequest);
    AuthResponse login(AuthRequest authRequest);
}