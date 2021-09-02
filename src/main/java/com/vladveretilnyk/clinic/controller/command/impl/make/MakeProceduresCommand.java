package com.vladveretilnyk.clinic.controller.command.impl.make;

import com.vladveretilnyk.clinic.controller.command.Command;
import com.vladveretilnyk.clinic.model.entity.Note;
import com.vladveretilnyk.clinic.model.entity.Role;
import com.vladveretilnyk.clinic.model.entity.User;
import com.vladveretilnyk.clinic.model.exception.NoteNotFoundException;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.service.NoteService;
import com.vladveretilnyk.clinic.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class MakeProceduresCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) throws UserNotFoundException, NoteNotFoundException {
        User user = (User) req.getSession().getAttribute("authorizedUser");
        Note note = NoteService.findNoteById(Long.valueOf(req.getParameter("noteId")));
        User executor = UserService.findUserById(Long.valueOf(req.getParameter("executorId")));

        note.setProceduresDone(true);
        note.setPersonIdWhoMadeProcedures(executor.getId());

        NoteService.update(note);
        try {
            NoteService.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return redirect(user.getRole());
        }

        return "redirect:/" + user.getRole().name().split("_")[1].toLowerCase() + "/patients/medical-book/note?noteId=" +
                note.getId() + "&patientId=" + note.getPatientId();
    }

    private String redirect(Role role) {
        return "redirect:/" + role.name().split("_")[1].toLowerCase();
    }
}