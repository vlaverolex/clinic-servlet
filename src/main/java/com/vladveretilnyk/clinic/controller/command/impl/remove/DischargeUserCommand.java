package com.vladveretilnyk.clinic.controller.command.impl.remove;

import com.vladveretilnyk.clinic.controller.command.Command;
import com.vladveretilnyk.clinic.model.entity.User;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class DischargeUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) throws UserNotFoundException {
        if(req.getParameter("patientId")==null){throw new UserNotFoundException("User not found!");}

        try {
            UserService.removeDoctorForPatient(Long.valueOf(req.getParameter("patientId")));
            UserService.removeNurseForPatient(Long.valueOf(req.getParameter("patientId")));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "redirect:/doctor";
    }
}
