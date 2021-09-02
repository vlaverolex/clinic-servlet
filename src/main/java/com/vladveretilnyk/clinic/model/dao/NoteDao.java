package com.vladveretilnyk.clinic.model.dao;

import com.vladveretilnyk.clinic.model.entity.Note;
import com.vladveretilnyk.clinic.model.exception.NoteNotFoundException;
import com.vladveretilnyk.clinic.model.pagination.Pager;

import java.sql.SQLException;
import java.util.List;

public interface NoteDao {
    List<Note> findNotesByPatientId(Long id);

    List<Note> findNotesByPatientId(Long id, Pager pager);

    Note findNoteById(Long id) throws NoteNotFoundException;

    void save(Note note);

    void update(Note note);

    void delete(Note note);

    void commit() throws SQLException;
}
