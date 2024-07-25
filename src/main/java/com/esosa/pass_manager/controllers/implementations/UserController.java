package com.esosa.pass_manager.controllers.implementations;

import com.esosa.pass_manager.controllers.interfaces.IUserController;
import com.esosa.pass_manager.controllers.response.PasswordResponse;
import com.esosa.pass_manager.services.interfaces.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController implements IUserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public Page<PasswordResponse> getUserPasswords(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userService.getUserPasswords(userId, pageNumber, size);
    }
}