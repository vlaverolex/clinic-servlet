package com.vladveretilnyk.clinic.model.dao;

import com.vladveretilnyk.clinic.model.entity.Category;
import com.vladveretilnyk.clinic.model.entity.Role;
import com.vladveretilnyk.clinic.model.entity.User;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.pagination.Pager;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;
import java.util.Map;

public interface UserDao {
    User findUserByUsername(String username) throws UserNotFoundException;

    User findUserById(Long id) throws UserNotFoundException;

    List<User> findUsersByRole(Role role, Pager pager) throws UserNotFoundException;

    List<User> findUsersByRole(Role role) throws UserNotFoundException;

    Map<Category, List<User>> findDoctorsGroupByCategories();

    List<User> findPatientsByNurse(User nurse) throws UserNotFoundException;

    List<User> findPatientsByDoctor(User doctor) throws UserNotFoundException;

    List<User> findPatientsByNurse(User nurse, Pager pager) throws UserNotFoundException;

    List<User> findPatientsByDoctor(User doctor, Pager pager) throws UserNotFoundException;

    void save(User user);

    void update(User user);

    void delete(User user);

    void commit() throws SQLException;
}