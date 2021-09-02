package com.vladveretilnyk.clinic.model.dao.impl;

import com.vladveretilnyk.clinic.model.dao.NoteDao;
import com.vladveretilnyk.clinic.model.dao.UserDao;
import com.vladveretilnyk.clinic.model.entity.Note;
import com.vladveretilnyk.clinic.model.entity.User;
import com.vladveretilnyk.clinic.model.exception.NoteNotFoundException;
import com.vladveretilnyk.clinic.model.pagination.Pager;
import com.vladveretilnyk.clinic.model.utils.DaoUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcNoteDao implements NoteDao {
    private Connection connection;

    public JdbcNoteDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Note> findNotesByPatientId(Long id) {
        List<Note> notes = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(
                    "select * from note where patient_id = '" + id + "'"
            );
            while (resultSet.next()) {
                notes.add(DaoUtils.extractNoteFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return notes;
    }

    @Override
    public List<Note> findNotesByPatientId(Long id, Pager pager) {
        List<Note> notes = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {

            ResultSet notesSet = statement.executeQuery(
                    "select * from note where patient_id = '" + id + "'" +
                            " order by " + pager.getSorting().getColumn() + " " + pager.getSorting().getDirection() +
                            " limit " + ((pager.getCurrentPage() - 1) * pager.getPageSize()) + ", " + pager.getPageSize()
            );
            while (notesSet.next()) {
                notes.add(DaoUtils.extractNoteFromResultSet(notesSet));
            }
            ResultSet totalSizeSet = statement.executeQuery("select *, count(patient_id) as total_size from note where patient_id = " + id);
            if (totalSizeSet.next()) {
                pager.setTotalSize(totalSizeSet.getLong("total_size"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return notes;
    }

    @Override
    public void save(Note note) {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into note (id,creation_date, diagnosis, " +
                        "doctor_id_who_created_note, is_procedures_done, is_surgery_done, " +
                        "person_id_who_made_procedures, procedures, surgery, patient_id) values (?,?,?,?,?,?,?,?,?)"
        )) {
            statement.setObject(1, note.getId());
            statement.setDate(2, java.sql.Date.valueOf(note.getCreationDate()));
            statement.setString(3, note.getDiagnosis());
            statement.setLong(4, note.getDoctorIdWhoCreatedNote());
            statement.setBoolean(5, note.isProceduresDone());
            statement.setBoolean(6, note.isSurgeryDone());
            statement.setObject(7, note.getPersonIdWhoMadeProcedures());
            statement.setString(8, note.getProcedures());
            statement.setString(9, note.getSurgery());
            statement.setLong(10, note.getPatientId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Note note) {
        try (PreparedStatement statement = connection.prepareStatement(
                "update note set is_procedures_done = ?, is_surgery_done = ?, person_id_who_made_procedures = ? where id = ?"
        )) {
            statement.setBoolean(1, note.isProceduresDone());
            statement.setBoolean(2, note.isSurgeryDone());
            statement.setObject(3, note.getPersonIdWhoMadeProcedures());
            statement.setLong(4, note.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Note note) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("delete from note where id = '" + note.getId() + "' or patient_id = '" + note.getPatientId() + "'");
            commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Note findNoteById(Long id) throws NoteNotFoundException {
        Note note = null;
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery("select * from note where id = '" + id + "'");
            if (result.next()) {
                note = DaoUtils.extractNoteFromResultSet(result);
            }
            if (note == null) {
                throw new NoteNotFoundException("Note not found!");
            }
            return note;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void commit() throws SQLException {
        try {
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }
    }
}
