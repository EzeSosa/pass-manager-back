package com.esosa.pass_manager.services.implementations;

import com.esosa.pass_manager.controllers.request.CreatePasswordRequest;
import com.esosa.pass_manager.controllers.request.UpdatePasswordRequest;
import com.esosa.pass_manager.controllers.response.PasswordResponse;
import com.esosa.pass_manager.data.model.Password;
import com.esosa.pass_manager.data.model.User;
import com.esosa.pass_manager.data.repositories.IPasswordRepository;
import com.esosa.pass_manager.services.interfaces.IPasswordService;
import com.esosa.pass_manager.services.mapper.PasswordMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PasswordService implements IPasswordService {
    private final IPasswordRepository passwordRepository;
    private final UserService userService;

    public PasswordService(IPasswordRepository passwordRepository, UserService userService) {
        this.passwordRepository = passwordRepository;
        this.userService = userService;
    }

    @Override
    public Page<PasswordResponse> getUserPasswords(User user, int pageNumber, int size) {
        return passwordRepository.findByUser(PageRequest.of(pageNumber, size), user)
                .map(PasswordMapper::buildPasswordResponse);
    }

    @Override
    public PasswordResponse getPassword(UUID passwordId) {
        Password existentPassword = findPasswordByIdOrThrowException(passwordId);
        return PasswordMapper.buildPasswordResponse(existentPassword);
    }

    @Override
    public PasswordResponse savePassword(CreatePasswordRequest createPasswordRequest) {
        String password = generatePassword();
        User user = userService.findUserByIdOrThrowException(createPasswordRequest.userId());

        ifExistsPasswordForUserThrowException(user, createPasswordRequest.name());

        Password newPassword = PasswordMapper.buildPassword(createPasswordRequest, password, user);
        passwordRepository.save(newPassword);

        return PasswordMapper.buildPasswordResponse(newPassword);
    }

    @Override
    public PasswordResponse updatePassword(UUID passwordId, UpdatePasswordRequest updatePasswordRequest) {
        Password existentPassword = findPasswordByIdOrThrowException(passwordId);

        if (!existentPassword.getName().equalsIgnoreCase(updatePasswordRequest.name()))
            ifExistsPasswordForUserThrowException(existentPassword.getUser(), updatePasswordRequest.name());

        existentPassword.setName(updatePasswordRequest.name());
        passwordRepository.save(existentPassword);

        return PasswordMapper.buildPasswordResponse(existentPassword);
    }

    @Override
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

    private void ifExistsPasswordForUserThrowException(User user, String passwordName) {
        if (userService.userHasPasswordName(user, passwordName))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password name already exists");
    }
}