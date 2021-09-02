package com.vladveretilnyk.clinic.controller.command.impl.remove;

import com.vladveretilnyk.clinic.controller.command.Command;
import com.vladveretilnyk.clinic.model.entity.User;
import com.vladveretilnyk.clinic.model.exception.NoteNotFoundException;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class RemoveUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) throws UserNotFoundException, NoteNotFoundException {
        if (req.getParameter("patientId") == null) {
            throw new UserNotFoundException("User not found!");
        }
        User patient = UserService.findUserById(Long.valueOf(req.getParameter("patientId")));
        UserService.delete(patient);
        return "redirect:/admin/patients";
    }
}
