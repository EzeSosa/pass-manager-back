package com.esosa.pass_manager.controllers;

import com.esosa.pass_manager.controllers.response.PasswordResponse;
import com.esosa.pass_manager.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("http://localhost:3000")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/passwords")
    @ResponseStatus(HttpStatus.OK)
    public List<PasswordResponse> getUserPasswords(@PathVariable UUID userId) {
        return userService.getUserPasswords(userId);
    }
}