package com.vladveretilnyk.clinic.controller.command.impl.view;

import com.vladveretilnyk.clinic.controller.command.Command;
import com.vladveretilnyk.clinic.model.entity.User;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.pagination.Pager;
import com.vladveretilnyk.clinic.model.pagination.Sorting;
import com.vladveretilnyk.clinic.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

import static com.vladveretilnyk.clinic.controller.PagePath.*;

public class ViewUserIndexPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) throws UserNotFoundException {
        User user = (User) req.getSession().getAttribute("authorizedUser");
        Pager pager = Pager.of(req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page")), req.getParameter("column") == null ?
                Sorting.USER_DEFAULT : new Sorting(req.getParameter("column"), req.getParameter("direction")));

        switch (user.getRole()) {
            case ROLE_ADMIN:
                return ADMIN_INDEX_PAGE_PATH;
            case ROLE_DOCTOR:
                req.setAttribute("patients", UserService.findPatientsByDoctor(user, pager));
                req.setAttribute("pager", pager);
                return DOCTOR_INDEX_PAGE_PATH;
            case ROLE_NURSE:
                req.setAttribute("patients", UserService.findPatientsByNurse(user, pager));
                req.setAttribute("pager", pager);
                return NURSE_INDEX_PAGE_PATH;
        }

        return "redirect:/login";
    }
}
