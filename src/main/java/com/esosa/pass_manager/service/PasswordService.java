package com.esosa.pass_manager.service;

import com.esosa.pass_manager.controller.request.PasswordRequest;
import com.esosa.pass_manager.controller.response.PasswordResponse;
import com.esosa.pass_manager.data.model.Password;
import com.esosa.pass_manager.data.repository.IPasswordRepository;
import com.esosa.pass_manager.service.mapper.PasswordMapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PasswordService {
    private final IPasswordRepository passwordRepository;

    public PasswordService(IPasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    public List<PasswordResponse> getPasswords() {
        return passwordRepository.findAll()
                .stream()
                .map(PasswordMapper::buildPasswordResponse)
                .toList();
    }

    public PasswordResponse getPassword(UUID passwordId) {
        Password existentPassword = findPasswordByIdOrThrowException(passwordId);
        return PasswordMapper.buildPasswordResponse(existentPassword);
    }

    public PasswordResponse savePassword(PasswordRequest passwordRequest) {
        ifExistsByNameThrowException(passwordRequest.name());

        String password = generatePassword();
        Password newPassword = PasswordMapper.buildPassword(passwordRequest, password);
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