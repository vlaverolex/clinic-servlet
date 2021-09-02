package com.vladveretilnyk.clinic.controller.command.impl.assign;

import com.vladveretilnyk.clinic.controller.command.Command;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.pagination.Pager;
import com.vladveretilnyk.clinic.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;

import static com.vladveretilnyk.clinic.controller.PagePath.NURSE_ASSIGN_PAGE_PATH;

public class AssignNurseCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) throws UserNotFoundException {
        if(req.getParameter("nurseId")==null){
            req.setAttribute("nurses", UserService.findAllNurses((Pager) req.getAttribute("pager")));
            return NURSE_ASSIGN_PAGE_PATH;
        }

        try {
            UserService.assignNurseForPatient(Long.valueOf(req.getParameter("patientId")),
                    Long.valueOf(req.getParameter("nurseId")));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "redirect:/doctor";
    }
}
