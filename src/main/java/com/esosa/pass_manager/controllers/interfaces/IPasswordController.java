package com.esosa.pass_manager.controllers.interfaces;

import com.esosa.pass_manager.controllers.request.CreatePasswordRequest;
import com.esosa.pass_manager.controllers.request.UpdatePasswordRequest;
import com.esosa.pass_manager.controllers.response.PasswordResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@RequestMapping("/api/v1/passwords")
public interface IPasswordController {

    @GetMapping("/{passwordId}")
    @ResponseStatus(HttpStatus.OK)
    PasswordResponse getPassword(@PathVariable UUID passwordId);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PasswordResponse savePassword(@RequestBody @Valid CreatePasswordRequest createPasswordRequest);

    @PatchMapping("/{passwordId}")
    @ResponseStatus(HttpStatus.OK)
    PasswordResponse updatePassword(
            @PathVariable UUID passwordId,
            @RequestBody @Valid UpdatePasswordRequest updatePasswordRequest
    );

    @DeleteMapping("/{passwordId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePassword(@PathVariable UUID passwordId);
}