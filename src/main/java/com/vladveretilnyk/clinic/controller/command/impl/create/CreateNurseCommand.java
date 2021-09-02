package com.vladveretilnyk.clinic.controller.command.impl.create;

import com.vladveretilnyk.clinic.controller.command.Command;
import com.vladveretilnyk.clinic.model.entity.Category;
import com.vladveretilnyk.clinic.model.entity.Role;
import com.vladveretilnyk.clinic.model.entity.User;
import com.vladveretilnyk.clinic.model.exception.NoteNotFoundException;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.service.UserService;
import com.vladveretilnyk.clinic.model.utils.LocalDateUtils;
import com.vladveretilnyk.clinic.model.utils.ValidateUtils;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.time.LocalDate;

import static com.vladveretilnyk.clinic.controller.PagePath.DOCTOR_CREATE_PAGE_PATH;
import static com.vladveretilnyk.clinic.controller.PagePath.NURSE_CREATE_PAGE_PATH;

public class CreateNurseCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) throws UserNotFoundException{
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                confirmPassword == null || confirmPassword.isEmpty()) {
            req.setAttribute("maxDate", LocalDate.now().minusYears(18));
            return NURSE_CREATE_PAGE_PATH;
        }

        User nurse = new User();
        nurse.setUsername(username);
        nurse.setPassword(password);
        nurse.setConfirmPassword(confirmPassword);

        if (!ValidateUtils.validate(req, nurse)) {
            return NURSE_CREATE_PAGE_PATH;
        }

        nurse.setBirthday(LocalDateUtils.parse(req.getParameter("birthday")));
        nurse.setRole(Role.ROLE_NURSE);

        try {
            UserService.save(nurse);
            UserService.commit();
        } catch (SQLException e) {
            req.setAttribute("userExistErrorMessage", "duplicate.user.username");
            return DOCTOR_CREATE_PAGE_PATH;
        }

        return "redirect:/admin/nurses";
    }
}
