package com.esosa.pass_manager.services;

import com.esosa.pass_manager.controllers.request.PasswordRequest;
import com.esosa.pass_manager.controllers.response.PasswordResponse;
import com.esosa.pass_manager.data.model.Password;
import com.esosa.pass_manager.data.model.User;
import com.esosa.pass_manager.data.repositories.IPasswordRepository;
import com.esosa.pass_manager.services.mapper.PasswordMapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PasswordService {
    private final IPasswordRepository passwordRepository;
    private final UserService userService;

    public PasswordService(IPasswordRepository passwordRepository, UserService userService) {
        this.passwordRepository = passwordRepository;
        this.userService = userService;
    }

    public PasswordResponse getPassword(UUID passwordId) {
        Password existentPassword = findPasswordByIdOrThrowException(passwordId);
        return PasswordMapper.buildPasswordResponse(existentPassword);
    }

    public PasswordResponse savePassword(PasswordRequest passwordRequest) {
        ifExistsByNameThrowException(passwordRequest.name());

        String password = generatePassword();
        User user = userService.findUserByIdOrThrowException(passwordRequest.userId());
        Password newPassword = PasswordMapper.buildPassword(passwordRequest, password, user);

        passwordRepository.save(newPassword);

        return PasswordMapper.buildPasswordResponse(newPassword);
    }

    public PasswordResponse updatePassword(UUID passwordId, PasswordRequest passwordRequest) {
        Password existentPassword = findPasswordByIdOrThrowException(passwordId);

        if (!existentPassword.getName().equalsIgnoreCase(passwordRequest.name()))
            ifExistsByNameThrowException(passwordRequest.name());

        existentPassword.setName(passwordRequest.name());
        passwordRepository.save(existentPassword);

        return PasswordMapper.buildPasswordResponse(existentPassword);
    }

    public void deletePassword(UUID passwordId) {
        ifNotExistsPasswordByIdThrowException(passwordId);
        passwordRepository.deleteById(passwordId);
    }

    private String generatePassword() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] secretKeyBytes = new byte[20];
        secureRandom.nextBytes(secretKeyBytes);

        return Base64.getEncoder()
                .encodeToString(secretKeyBytes);
    }

    private Password findPasswordByIdOrThrowException(UUID passwordId) {
        return passwordRepository.findById(passwordId)
                .orElseThrow(() -> new NoSuchElementException("Password with ID " + passwordId + "does not exist"));
    }

    private void ifNotExistsPasswordByIdThrowException(UUID passwordId) {
        if (!passwordRepository.existsById(passwordId))
            throw new NoSuchElementException("Password with ID " + passwordId + "does not exist");
    }

    private void ifExistsByNameThrowException(String name) {
        if (passwordRepository.existsByName(name))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name already taken");
    }
}