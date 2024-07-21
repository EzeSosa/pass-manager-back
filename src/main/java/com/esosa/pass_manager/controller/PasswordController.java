package com.esosa.pass_manager.controller;

import com.esosa.pass_manager.controller.request.PasswordRequest;
import com.esosa.pass_manager.controller.response.PasswordResponse;
import com.esosa.pass_manager.service.PasswordService;

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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/passwords")
@Validated
public class PasswordController {
    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PasswordResponse> getPasswords() {
        return passwordService.getPasswords();
    }

    @GetMapping("/{passwordId}")
    @ResponseStatus(HttpStatus.OK)
    public PasswordResponse getPassword(@PathVariable UUID passwordId) {
        return passwordService.getPassword(passwordId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PasswordResponse savePassword(@RequestBody @Valid PasswordRequest passwordRequest) {
        return passwordService.savePassword(passwordRequest);
    }

    @PatchMapping("/{passwordId}")
    @ResponseStatus(HttpStatus.OK)
    public PasswordResponse updatePassword(
            @PathVariable UUID passwordId,
            @RequestBody @Valid PasswordRequest passwordRequest
    ) {
        return passwordService.updatePassword(passwordId, passwordRequest);
    }

    @DeleteMapping("/{passwordId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePassword(@PathVariable UUID passwordId) {
        passwordService.deletePassword(passwordId);
    }
}