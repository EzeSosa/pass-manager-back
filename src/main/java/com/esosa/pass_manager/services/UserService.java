package com.esosa.pass_manager.services;

import com.esosa.pass_manager.controllers.request.AuthRequest;
import com.esosa.pass_manager.controllers.response.PasswordResponse;
import com.esosa.pass_manager.data.model.User;
import com.esosa.pass_manager.data.repositories.IUserRepository;
import com.esosa.pass_manager.services.mapper.PasswordMapper;
import com.esosa.pass_manager.services.mapper.UserMapper;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<PasswordResponse> getUserPasswords(UUID userId) {
        return findUserByIdOrThrowException(userId)
                .getPasswords()
                .stream()
                .map(PasswordMapper::buildPasswordResponse)
                .toList();
    }

    public User findUserByIdOrThrowException(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with ID " + userId + "does not exist"));
    }

    public void saveUser(AuthRequest registerRequest, PasswordEncoder passwordEncoder) {
        validateExistsUsername(registerRequest.username());
        userRepository.save(UserMapper.buildUser(registerRequest, passwordEncoder));
    }

    public User findUserByUsernameOrThrowException(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User with username " + username + "does not exist"));
    }

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