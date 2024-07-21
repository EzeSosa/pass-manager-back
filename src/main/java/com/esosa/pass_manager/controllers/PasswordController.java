package com.esosa.pass_manager.controllers;

import com.esosa.pass_manager.controllers.request.CreatePasswordRequest;
import com.esosa.pass_manager.controllers.request.UpdatePasswordRequest;
import com.esosa.pass_manager.controllers.response.PasswordResponse;
import com.esosa.pass_manager.services.PasswordService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/passwords")
@Validated
public class PasswordController {
    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @GetMapping("/{passwordId}")
    @ResponseStatus(HttpStatus.OK)
    public PasswordResponse getPassword(@PathVariable UUID passwordId) {
        return passwordService.getPassword(passwordId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PasswordResponse savePassword(@RequestBody @Valid CreatePasswordRequest createPasswordRequest) {
        return passwordService.savePassword(createPasswordRequest);
    }

    @PatchMapping("/{passwordId}")
    @ResponseStatus(HttpStatus.OK)
    public PasswordResponse updatePassword(
            @PathVariable UUID passwordId,
            @RequestBody @Valid UpdatePasswordRequest updatePasswordRequest
    ) {
        return passwordService.updatePassword(passwordId, updatePasswordRequest);
    }

    @DeleteMapping("/{passwordId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePassword(@PathVariable UUID passwordId) {
        passwordService.deletePassword(passwordId);
    }
}