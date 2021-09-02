package com.vladveretilnyk.clinic.model.utils;

import com.vladveretilnyk.clinic.model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class ValidateUtils {

    private ValidateUtils(){}

    public static boolean validate(HttpServletRequest request, User user) {
        if (user.getUsername().length() < 5) {
            request.setAttribute("usernameLengthErrorMessage", "size.user.username");
            return false;
        }

        if (user.getPassword().length() < 5) {
            request.setAttribute("passwordLengthErrorMessage", "size.user.password");
            return false;
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            request.setAttribute("passwordsDoNotMatch", "different.user.password");
            return false;
        }

        return true;
    }
}