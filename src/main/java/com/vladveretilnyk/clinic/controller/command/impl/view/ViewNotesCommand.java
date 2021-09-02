package com.vladveretilnyk.clinic.controller.command.impl.view;

import com.vladveretilnyk.clinic.controller.command.Command;
import com.vladveretilnyk.clinic.model.entity.Role;
import com.vladveretilnyk.clinic.model.entity.User;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.pagination.Pager;
import com.vladveretilnyk.clinic.model.pagination.Sorting;
import com.vladveretilnyk.clinic.model.service.NoteService;
import com.vladveretilnyk.clinic.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

import static com.vladveretilnyk.clinic.controller.PagePath.DOCTOR_MEDICAL_BOOK_PAGE_PATH;
import static com.vladveretilnyk.clinic.controller.PagePath.NURSE_MEDICAL_BOOK_PAGE_PATH;

public class ViewNotesCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) throws UserNotFoundException {
        if(req.getParameter("patientId")==null){throw new UserNotFoundException("user not found!");}

        User user = (User) req.getSession().getAttribute("authorizedUser");
        User patient = UserService.findUserById(Long.valueOf(req.getParameter("patientId")));
        Pager pager = Pager.of(req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page")), req.getParameter("column") == null ?
                Sorting.NOTE_DEFAULT : new Sorting(req.getParameter("column"), req.getParameter("direction")));

        req.setAttribute("notes", NoteService.findNotesByPatientId(patient.getId(), pager));
        req.setAttribute("patient", patient);
        req.setAttribute("pager", pager);

        if (user.getRole() == Role.ROLE_DOCTOR) {
            return DOCTOR_MEDICAL_BOOK_PAGE_PATH;
        } else {
            return NURSE_MEDICAL_BOOK_PAGE_PATH;
        }
    }
}
