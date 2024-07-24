package com.esosa.pass_manager.controllers;

import com.esosa.pass_manager.controllers.response.PasswordResponse;
import com.esosa.pass_manager.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/passwords")
    @ResponseStatus(HttpStatus.OK)
    public Page<PasswordResponse> getUserPasswords(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userService.getUserPasswords(userId, pageNumber, size);
    }
}