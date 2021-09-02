package com.vladveretilnyk.clinic.controller.command.impl.create;

import com.vladveretilnyk.clinic.controller.command.Command;
import com.vladveretilnyk.clinic.model.entity.Category;
import com.vladveretilnyk.clinic.model.entity.Role;
import com.vladveretilnyk.clinic.model.entity.User;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.service.UserService;
import com.vladveretilnyk.clinic.model.utils.LocalDateUtils;
import com.vladveretilnyk.clinic.model.utils.ValidateUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.vladveretilnyk.clinic.controller.PagePath.DOCTOR_CREATE_PAGE_PATH;

public class CreateDoctorCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) throws UserNotFoundException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                confirmPassword == null || confirmPassword.isEmpty()) {
            req.setAttribute("categories", Category.values());
            req.setAttribute("maxDate", LocalDate.now().minusYears(25));
            req.setAttribute("doctors", UserService.findAllDoctors());
            return DOCTOR_CREATE_PAGE_PATH;
        }

        User doctor = new User();
        doctor.setUsername(username);
        doctor.setPassword(password);
        doctor.setConfirmPassword(confirmPassword);

        if (!ValidateUtils.validate(req, doctor)) {
            return DOCTOR_CREATE_PAGE_PATH;
        }

        doctor.setBirthday(LocalDateUtils.parse(req.getParameter("birthday")));
        doctor.setCategory(Category.valueOf(req.getParameter("categoryName").toUpperCase()));
        doctor.setRole(Role.ROLE_DOCTOR);

        try {
            UserService.save(doctor);
            UserService.commit();
        } catch (SQLException e) {
            req.setAttribute("userExistErrorMessage", "duplicate.user.username");
            return DOCTOR_CREATE_PAGE_PATH;
        }

        return "redirect:/admin/doctors";
    }
}
