package com.esosa.pass_manager.services.mapper;

import com.esosa.pass_manager.controllers.request.CreatePasswordRequest;
import com.esosa.pass_manager.controllers.response.PasswordResponse;
import com.esosa.pass_manager.data.model.Password;
import com.esosa.pass_manager.data.model.User;

public class PasswordMapper {
    public static Password buildPassword(CreatePasswordRequest createPasswordRequest, String password, User user) {
        return new Password(createPasswordRequest.name(), password, user);
    }

    public static PasswordResponse buildPasswordResponse(Password password) {
        return new PasswordResponse(password.getId(), password.getName(), password.getPassword());
    }
}