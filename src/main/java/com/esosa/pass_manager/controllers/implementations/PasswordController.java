package com.esosa.pass_manager.controllers.implementations;

import com.esosa.pass_manager.controllers.interfaces.IPasswordController;
import com.esosa.pass_manager.controllers.request.CreatePasswordRequest;
import com.esosa.pass_manager.controllers.request.UpdatePasswordRequest;
import com.esosa.pass_manager.controllers.response.PasswordResponse;
import com.esosa.pass_manager.services.PasswordService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PasswordController implements IPasswordController {
    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Override
    public PasswordResponse getPassword(@PathVariable UUID passwordId) {
        return passwordService.getPassword(passwordId);
    }

    @Override
    public PasswordResponse savePassword(@RequestBody @Valid CreatePasswordRequest createPasswordRequest) {
        return passwordService.savePassword(createPasswordRequest);
    }

    @Override
    public PasswordResponse updatePassword(
            @PathVariable UUID passwordId,
            @RequestBody @Valid UpdatePasswordRequest updatePasswordRequest
    ) {
        return passwordService.updatePassword(passwordId, updatePasswordRequest);
    }

    @Override
    public void deletePassword(@PathVariable UUID passwordId) {
        passwordService.deletePassword(passwordId);
    }
}