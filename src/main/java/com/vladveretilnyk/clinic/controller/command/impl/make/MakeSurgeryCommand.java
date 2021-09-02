package com.vladveretilnyk.clinic.controller.command.impl.make;

import com.vladveretilnyk.clinic.controller.command.Command;
import com.vladveretilnyk.clinic.model.entity.Note;
import com.vladveretilnyk.clinic.model.entity.User;
import com.vladveretilnyk.clinic.model.exception.NoteNotFoundException;
import com.vladveretilnyk.clinic.model.service.NoteService;
import com.vladveretilnyk.clinic.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class MakeSurgeryCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) throws NoteNotFoundException {
        User user = (User) req.getSession().getAttribute("authorizedUser");
        Note note = NoteService.findNoteById(Long.valueOf(req.getParameter("noteId")));

        note.setSurgeryDone(true);
        NoteService.update(note);

        try {
            NoteService.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return "redirect:/doctor";
        }
        return "redirect:/" + user.getRole().name().split("_")[1].toLowerCase() + "/patients/medical-book/note?noteId=" +
                note.getId() + "&patientId=" + note.getPatientId();
    }
}
