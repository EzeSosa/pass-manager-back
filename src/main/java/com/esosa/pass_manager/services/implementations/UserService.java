package com.esosa.pass_manager.services.implementations;

import com.esosa.pass_manager.controllers.request.AuthRequest;
import com.esosa.pass_manager.controllers.response.PasswordResponse;
import com.esosa.pass_manager.data.model.User;
import com.esosa.pass_manager.data.repositories.IUserRepository;
import com.esosa.pass_manager.services.interfaces.IPasswordService;
import com.esosa.pass_manager.services.interfaces.IUserService;
import com.esosa.pass_manager.services.mapper.UserMapper;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IPasswordService passwordService;

    public UserService(IUserRepository userRepository, @Lazy IPasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    @Override
    public Page<PasswordResponse> getUserPasswords(UUID userId, int pageNumber, int size) {
        User user = findUserByIdOrThrowException(userId);
        return passwordService.getUserPasswords(user, pageNumber, size);
    }

    @Override
    public User findUserByIdOrThrowException(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with ID " + userId + "does not exist"));
    }

    @Override
    public void saveUser(AuthRequest authRequest, PasswordEncoder passwordEncoder) {
        validateExistsUsername(authRequest.username());
        userRepository.save(UserMapper.buildUser(authRequest, passwordEncoder));
    }

    @Override
    public User findUserByUsernameOrThrowException(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User with username " + username + "does not exist"));
    }

    @Override
    public boolean userHasPasswordName(User user, String passwordName) {
        return user.getPasswords()
                .stream()
                .anyMatch(password -> password.getName().equalsIgnoreCase(passwordName));
    }

    private void validateExistsUsername(String username) {
        if (userRepository.existsByUsername(username))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already taken");
    }
}