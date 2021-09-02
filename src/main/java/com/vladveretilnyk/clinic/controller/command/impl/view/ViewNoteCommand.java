package com.vladveretilnyk.clinic.controller.command.impl.view;

import com.vladveretilnyk.clinic.controller.command.Command;
import com.vladveretilnyk.clinic.model.entity.Note;
import com.vladveretilnyk.clinic.model.entity.Role;
import com.vladveretilnyk.clinic.model.entity.User;
import com.vladveretilnyk.clinic.model.exception.NoteNotFoundException;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.service.NoteService;
import com.vladveretilnyk.clinic.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

import static com.vladveretilnyk.clinic.controller.PagePath.DOCTOR_MEDICAL_NOTE_PAGE_PATH;
import static com.vladveretilnyk.clinic.controller.PagePath.NURSE_MEDICAL_NOTE_PAGE_PATH;

public class ViewNoteCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) throws UserNotFoundException, NoteNotFoundException {
        if (req.getParameter("noteId") == null) {throw new NoteNotFoundException("Note not found!");}
        if (req.getParameter("patientId") == null) {throw new NoteNotFoundException("User not found!");}

        User user = (User) req.getSession().getAttribute("authorizedUser");
        Note note = NoteService.findNoteById(Long.valueOf(req.getParameter("noteId")));

        req.setAttribute("patient", UserService.findUserById(note.getPatientId()));
        req.setAttribute("doctorWhoCreateNote", UserService.findUserById(note.getDoctorIdWhoCreatedNote()));
        req.setAttribute("personWhoMadeProcedures", UserService.findUserById(note.getPersonIdWhoMadeProcedures()));
        req.setAttribute("note", note);
        req.setAttribute("currentUser", user);

        if (user.getRole() == Role.ROLE_DOCTOR) {
            return DOCTOR_MEDICAL_NOTE_PAGE_PATH;
        } else {
            return NURSE_MEDICAL_NOTE_PAGE_PATH;
        }
    }
}
