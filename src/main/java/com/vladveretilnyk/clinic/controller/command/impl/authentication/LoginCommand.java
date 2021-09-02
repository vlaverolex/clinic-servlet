package com.vladveretilnyk.clinic.controller.command.impl.authentication;

import com.vladveretilnyk.clinic.controller.command.Command;
import com.vladveretilnyk.clinic.model.entity.Role;
import com.vladveretilnyk.clinic.model.entity.User;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;

import static com.vladveretilnyk.clinic.controller.PagePath.LOGIN_PAGE_PATH;

public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) throws UserNotFoundException {
        HttpSession session = req.getSession();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null) {
            if (req.getParameter("logout") != null) {
                req.setAttribute("logoutMessage", "loggedOutSuccessfully");
            }
            return LOGIN_PAGE_PATH;
        }

        User user = UserService.findUserByCredentials(username, password);
        if (user != null) {
            session.setAttribute("authorizedUser", user);
            return getUrlByRole(user.getRole());
        }

        req.setAttribute("errorMessage", "invalidUsernameOrPassword");
        return LOGIN_PAGE_PATH;
    }

    public String getUrlByRole(Role role) {

        switch (role) {
            case ROLE_ADMIN:
                return "redirect:/admin";
            case ROLE_DOCTOR:
                return "redirect:/doctor";
            case ROLE_NURSE:
                return "redirect:/nurse";
            case ROLE_PATIENT:
                return "redirect:/patient";
        }

        return LOGIN_PAGE_PATH;
    }
}
