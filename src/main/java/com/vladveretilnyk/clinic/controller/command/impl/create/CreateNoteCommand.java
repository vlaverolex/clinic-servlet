package com.vladveretilnyk.clinic.controller.command.impl.create;

import com.vladveretilnyk.clinic.controller.command.Command;
import com.vladveretilnyk.clinic.model.entity.Note;
import com.vladveretilnyk.clinic.model.entity.User;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.service.NoteService;
import com.vladveretilnyk.clinic.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.vladveretilnyk.clinic.controller.PagePath.DOCTOR_NOTE_CREATE_PAGE_PATH;

public class CreateNoteCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) throws UserNotFoundException {
        if (req.getParameter("patientId") == null) {throw new UserNotFoundException("User not found!");}

        User patient = UserService.findUserById(Long.valueOf(req.getParameter("patientId")));
        User doctor = (User) req.getSession().getAttribute("authorizedUser");
        req.setAttribute("patient", patient);

        String diagnosis = req.getParameter("diagnosis");
        String procedures = req.getParameter("procedures");
        String surgery = req.getParameter("surgery");

        if (diagnosis == null || procedures == null) {
            return DOCTOR_NOTE_CREATE_PAGE_PATH;
        }

        Note note = new Note();
        note.setDiagnosis(diagnosis);
        note.setProcedures(procedures);

        if (surgery == null || surgery.isEmpty()) {
            note.setSurgery(null);
        } else {
            note.setSurgery(surgery);
        }

        try {
            NoteService.createNoteForPatient(note, patient.getId(), doctor.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "redirect:/doctor/patients/medical-book?patientId=" + patient.getId();
    }
}