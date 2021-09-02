package com.vladveretilnyk.clinic.model.service;

import com.vladveretilnyk.clinic.model.dao.DaoFactory;
import com.vladveretilnyk.clinic.model.dao.NoteDao;
import com.vladveretilnyk.clinic.model.entity.Note;
import com.vladveretilnyk.clinic.model.exception.NoteNotFoundException;
import com.vladveretilnyk.clinic.model.pagination.Pager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class NoteService {
    private static final NoteDao NOTE_DAO = DaoFactory.getInstance().createNoteDao();

    public static List<Note> findNotesByPatientId(Long id) {
        return NOTE_DAO.findNotesByPatientId(id);
    }

    public static List<Note> findNotesByPatientId(Long id, Pager pager) {
        return NOTE_DAO.findNotesByPatientId(id, pager);
    }

    public static void save(Note note) {
        NOTE_DAO.save(note);
    }

    public static void update(Note note) {
        NOTE_DAO.update(note);
    }

    public static Note findNoteById(Long id) throws NoteNotFoundException {
        return NOTE_DAO.findNoteById(id);
    }

    public static void createNoteForPatient(Note note, Long patientId, Long doctorId) throws SQLException {
        note.setPatientId(patientId);
        note.setDoctorIdWhoCreatedNote(doctorId);
        note.setCreationDate(LocalDate.now());
        NoteService.save(note);
        NoteService.commit();
    }

    public static void commit() throws SQLException {
        NOTE_DAO.commit();
    }
}

