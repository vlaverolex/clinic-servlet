package com.vladveretilnyk.clinic.model.dao.impl;

import com.vladveretilnyk.clinic.model.dao.DaoFactory;
import com.vladveretilnyk.clinic.model.dao.NoteDao;
import com.vladveretilnyk.clinic.model.dao.UserDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcDaoFactory extends DaoFactory {
    private static Connection connection;

    @Override
    public UserDao createUserDao() {
        return new JdbcUserDao(getConnection());
    }

    @Override
    public NoteDao createNoteDao() {
        return new JdbcNoteDao(getConnection());
    }

    private Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/clinic_db_servlet",
                        "root",
                        "admin");
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return connection;
    }

}