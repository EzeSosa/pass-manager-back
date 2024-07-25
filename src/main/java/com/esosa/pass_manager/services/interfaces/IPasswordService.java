package com.esosa.pass_manager.services.interfaces;

import com.esosa.pass_manager.controllers.request.CreatePasswordRequest;
import com.esosa.pass_manager.controllers.request.UpdatePasswordRequest;
import com.esosa.pass_manager.controllers.response.PasswordResponse;
import com.esosa.pass_manager.data.model.User;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IPasswordService {
    Page<PasswordResponse> getUserPasswords(User user, int pageNumber, int size);
    PasswordResponse getPassword(UUID passwordId);
    PasswordResponse savePassword(CreatePasswordRequest createPasswordRequest);
    PasswordResponse updatePassword(UUID passwordId, UpdatePasswordRequest updatePasswordRequest);
    void deletePassword(UUID passwordId);
}