package com.vladveretilnyk.clinic.model.utils;

import com.vladveretilnyk.clinic.model.entity.Category;
import com.vladveretilnyk.clinic.model.entity.Note;
import com.vladveretilnyk.clinic.model.entity.Role;
import com.vladveretilnyk.clinic.model.entity.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoUtils {
    private DaoUtils() {
    }

    public static User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setBirthday(resultSet.getDate("birthday") == null ?
                null : LocalDateUtils.parse(resultSet.getDate("birthday").toString()));
        user.setPatientVolume(resultSet.getObject("patient_volume", Integer.class) == null ?
                null : resultSet.getObject("patient_volume", Integer.class));
        user.setRole(Role.roleById(resultSet.getLong("role_id")));
        user.setCategory(resultSet.getObject("category_id", Long.class) == null ?
                null : Category.categoryById(resultSet.getObject("category_id", Long.class)));

        return user;
    }

    public static Note extractNoteFromResultSet(ResultSet resultSet) throws SQLException {
        Note note = new Note();

        note.setId(resultSet.getLong("id"));
        note.setCreationDate(LocalDateUtils.parse(resultSet.getDate("creation_date").toString()));
        note.setDiagnosis(resultSet.getString("diagnosis"));
        note.setSurgery(resultSet.getString("surgery"));
        note.setProcedures(resultSet.getString("procedures"));
        note.setProceduresDone(resultSet.getBoolean("is_procedures_done"));
        note.setSurgeryDone(resultSet.getBoolean("is_surgery_done"));
        note.setDoctorIdWhoCreatedNote(resultSet.getLong("doctor_id_who_created_note"));
        note.setPersonIdWhoMadeProcedures(resultSet.getObject("person_id_who_made_procedures", Long.class));
        note.setPatientId(resultSet.getLong("patient_id"));

        return note;
    }
}
