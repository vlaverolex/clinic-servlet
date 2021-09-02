package com.vladveretilnyk.clinic.controller.command.impl.view;

import com.vladveretilnyk.clinic.controller.command.Command;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.pagination.Pager;
import com.vladveretilnyk.clinic.model.pagination.Sorting;
import com.vladveretilnyk.clinic.model.service.UserService;

import javax.servlet.http.HttpServletRequest;


import static com.vladveretilnyk.clinic.controller.PagePath.DOCTORS_SHOW_PAGE_PATH;
import static com.vladveretilnyk.clinic.controller.PagePath.NURSES_SHOW_PAGE_PATH;
import static com.vladveretilnyk.clinic.controller.PagePath.PATIENTS_SHOW_PAGE_PATH;

public class ViewUsersCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) throws UserNotFoundException {
        String url = req.getRequestURI();
        Pager pager = Pager.of(req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page")), req.getParameter("column") == null ?
                Sorting.USER_DEFAULT : new Sorting(req.getParameter("column"), req.getParameter("direction")));

        req.setAttribute("pager", pager);
        if (url.contains("doctors")) {
            req.setAttribute("doctors", UserService.findAllDoctors(pager));
            return DOCTORS_SHOW_PAGE_PATH;
        } else if (url.contains("nurses")) {
            req.setAttribute("nurses", UserService.findAllNurses(pager));
            return NURSES_SHOW_PAGE_PATH;
        } else {
            req.setAttribute("patients", UserService.findAllPatients(pager));
            return PATIENTS_SHOW_PAGE_PATH;
        }
    }
}
