package com.vladveretilnyk.clinic.controller.command.impl.create;

import com.vladveretilnyk.clinic.controller.command.Command;
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
import static com.vladveretilnyk.clinic.controller.PagePath.PATIENT_CREATE_PAGE_PATH;

public class CreatePatientCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) throws UserNotFoundException{
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                confirmPassword == null || confirmPassword.isEmpty()) {
            req.setAttribute("doctorsGroupedByCategories", UserService.findDoctorsGroupByCategories());
            req.setAttribute("maxDate", LocalDate.now().minusYears(1));
            req.setAttribute("doctors", UserService.findAllPatients());
            return PATIENT_CREATE_PAGE_PATH;
        }

        User patient = new User();
        patient.setUsername(username);
        patient.setPassword(password);
        patient.setConfirmPassword(confirmPassword);

        if (!ValidateUtils.validate(req, patient)) {
            return PATIENT_CREATE_PAGE_PATH;
        }

        patient.setBirthday(LocalDateUtils.parse(req.getParameter("birthday")));
        patient.setDoctor(UserService.findUserByUsername(req.getParameter("doctorUsername")));
        patient.getDoctor().incrementPatientVolume();
        patient.setRole(Role.ROLE_PATIENT);

        try {
            UserService.save(patient);
            UserService.commit();
        } catch (SQLException e) {
            req.setAttribute("userExistErrorMessage", "duplicate.user.username");
            return DOCTOR_CREATE_PAGE_PATH;
        }

        return "redirect:/admin/patients";
    }
}
