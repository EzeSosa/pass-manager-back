package com.esosa.pass_manager.controllers.interfaces;

import com.esosa.pass_manager.controllers.response.PasswordResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@RequestMapping("/api/v1/users")
public interface IUserController {

    @GetMapping("/{userId}/passwords")
    @ResponseStatus(HttpStatus.OK)
    Page<PasswordResponse> getUserPasswords(
            @PathVariable UUID userId,
            @RequestParam int pageNumber,
            @RequestParam int size
    );
}