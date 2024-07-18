package com.esosa.pass_manager.service.mapper;

import com.esosa.pass_manager.controller.request.PasswordRequest;
import com.esosa.pass_manager.controller.response.PasswordResponse;
import com.esosa.pass_manager.data.model.Password;

public class PasswordMapper {
    public static Password buildPassword(PasswordRequest passwordRequest, String password) {
        return new Password(passwordRequest.name(), password);
    }

    public static PasswordResponse buildPasswordResponse(Password password) {
        return new PasswordResponse(password.getId(), password.getName(), password.getPassword());
    }
}