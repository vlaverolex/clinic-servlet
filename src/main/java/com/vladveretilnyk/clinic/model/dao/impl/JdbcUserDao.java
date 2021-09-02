package com.vladveretilnyk.clinic.model.dao.impl;


import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.pagination.Pager;
import com.vladveretilnyk.clinic.model.dao.UserDao;
import com.vladveretilnyk.clinic.model.entity.Category;
import com.vladveretilnyk.clinic.model.entity.Role;
import com.vladveretilnyk.clinic.model.entity.User;
import com.vladveretilnyk.clinic.model.utils.DaoUtils;

import java.sql.*;
import java.util.*;

public class JdbcUserDao implements UserDao {
    private final Connection connection;

    public JdbcUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User findUserByUsername(String username) throws UserNotFoundException {
        User user = null;
        Long doctorId = null;
        Long nurseId = null;

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from user where username = '" + username + "'");
            if (resultSet.next()) {
                user = DaoUtils.extractUserFromResultSet(resultSet);
                doctorId = resultSet.getObject("doctor_id", Long.class);
                nurseId = resultSet.getObject("nurse_id", Long.class);
            }
            return user == null ? null : initUserByDoctorAndNurse(user, doctorId, nurseId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User initUserByDoctorAndNurse(User user, Long doctorId, Long nurseId) throws UserNotFoundException {
        if (user != null) {
            if (doctorId != null) {
                user.setDoctor(findUserById(doctorId));
            }
            if (nurseId != null) {
                user.setNurse(findUserById(nurseId));
            }
        }
        return user;
    }

    @Override
    public User findUserById(Long id) throws UserNotFoundException {
        User user = null;
        Long doctorId = null;
        Long nurseId = null;

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from user where user_id = '" + id + "'");

            if (resultSet.next()) {
                user = DaoUtils.extractUserFromResultSet(resultSet);
                doctorId = resultSet.getObject("doctor_id", Long.class);
                nurseId = resultSet.getObject("nurse_id", Long.class);
            }

            if (user == null) {
                throw new UserNotFoundException("User with id " + id + " not found!");
            }

            return initUserByDoctorAndNurse(user, doctorId, nurseId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findUsersByRole(Role role) throws UserNotFoundException {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from user where role_id = '" + role.id() + "'");
            while (resultSet.next()) {
                User user = DaoUtils.extractUserFromResultSet(resultSet);
                Long doctorId = resultSet.getObject("doctor_id", Long.class);
                Long nurseId = resultSet.getObject("nurse_id", Long.class);
                users.add(initUserByDoctorAndNurse(user, doctorId, nurseId));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findUsersByRole(Role role, Pager pager) throws UserNotFoundException {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet usersSet = statement.executeQuery(
                    "select * from user where role_id = " + role.id() +
                            " order by " + pager.getSorting().getColumn() + " " + pager.getSorting().getDirection() +
                            " limit " + ((pager.getCurrentPage() - 1) * pager.getPageSize()) + ", " + pager.getPageSize()
            );
            while (usersSet.next()) {
                User user = DaoUtils.extractUserFromResultSet(usersSet);
                Long doctorId = usersSet.getObject("doctor_id", Long.class);
                Long nurseId = usersSet.getObject("nurse_id", Long.class);
                users.add(initUserByDoctorAndNurse(user, doctorId, nurseId));
            }

            ResultSet totalSizeSet = statement.executeQuery("select *, count(role_id) as total_size from user where role_id = " + role.id());
            if (totalSizeSet.next()) {
                pager.setTotalSize(totalSizeSet.getLong("total_size"));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Category, List<User>> findDoctorsGroupByCategories() {
        Map<Category, List<User>> doctorsGroupedByCategories = new HashMap<>();

        try (Statement statement = connection.createStatement()) {

            Arrays.asList(Category.values()).forEach(category -> {
                List<User> users = new ArrayList<>();
                try {
                    ResultSet resultSet = statement.executeQuery("select * from user where category_id = '" + category.id() + "'");
                    while (resultSet.next()) {
                        users.add(DaoUtils.extractUserFromResultSet(resultSet));
                    }
                    doctorsGroupedByCategories.put(category, users);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return doctorsGroupedByCategories;
    }

    @Override
    public List<User> findPatientsByNurse(User nurse) throws UserNotFoundException {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from user where nurse_id = '" + nurse.getId() + "'");

            while (resultSet.next()) {
                User user = DaoUtils.extractUserFromResultSet(resultSet);
                Long doctorId = resultSet.getObject("doctor_id", Long.class);
                Long nurseId = resultSet.getObject("nurse_id", Long.class);
                users.add(initUserByDoctorAndNurse(user, doctorId, nurseId));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public List<User> findPatientsByDoctor(User doctor) throws UserNotFoundException {
        List<User> users = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from user where doctor_id = '" + doctor.getId() + "'");

            while (resultSet.next()) {
                User user = DaoUtils.extractUserFromResultSet(resultSet);
                Long doctorId = resultSet.getObject("doctor_id", Long.class);
                Long nurseId = resultSet.getObject("nurse_id", Long.class);
                users.add(initUserByDoctorAndNurse(user, doctorId, nurseId));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public List<User> findPatientsByNurse(User nurse, Pager pager) throws UserNotFoundException {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from user where nurse_id = '" + nurse.getId() + "'" +
                    " order by " + pager.getSorting().getColumn() + " " + pager.getSorting().getDirection() +
                    " limit " + ((pager.getCurrentPage() - 1) * pager.getPageSize()) + ", " + pager.getPageSize());

            while (resultSet.next()) {
                User user = DaoUtils.extractUserFromResultSet(resultSet);
                Long doctorId = resultSet.getObject("doctor_id", Long.class);
                Long nurseId = resultSet.getObject("nurse_id", Long.class);
                users.add(initUserByDoctorAndNurse(user, doctorId, nurseId));
            }
            ResultSet totalSizeSet = statement.executeQuery("select *, count(role_id) as total_size from user where nurse_id = " + nurse.getId());
            if (totalSizeSet.next()) {
                pager.setTotalSize(totalSizeSet.getLong("total_size"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public List<User> findPatientsByDoctor(User doctor, Pager pager) throws UserNotFoundException {
        List<User> users = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from user where doctor_id = '" + doctor.getId() + "'" +
                    " order by " + pager.getSorting().getColumn() + " " + pager.getSorting().getDirection() +
                    " limit " + ((pager.getCurrentPage() - 1) * pager.getPageSize()) + ", " + pager.getPageSize());

            while (resultSet.next()) {
                User user = DaoUtils.extractUserFromResultSet(resultSet);
                Long doctorId = resultSet.getObject("doctor_id", Long.class);
                Long nurseId = resultSet.getObject("nurse_id", Long.class);
                users.add(initUserByDoctorAndNurse(user, doctorId, nurseId));
            }

            ResultSet totalSizeSet = statement.executeQuery("select *, count(role_id) as total_size from user where doctor_id = " + doctor.getId());
            if (totalSizeSet.next()) {
                pager.setTotalSize(totalSizeSet.getLong("total_size"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public void save(User user) {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into user (user_id,username, password, birthday, " +
                        "patient_volume, role_id, doctor_id, nurse_id, category_id)" +
                        "values (?,?,?,?,?,?,?,?,?)"
        )) {
            statement.setObject(1, user.getId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setDate(4, user.getBirthday() != null ? java.sql.Date.valueOf(user.getBirthday()) : null);
            if (user.getRole() == Role.ROLE_DOCTOR || user.getRole() == Role.ROLE_NURSE) {
                statement.setObject(5, user.getPatientVolume() == null ? 0 : user.getPatientVolume());
            } else {
                statement.setObject(5, user.getPatientVolume() == null ? null : user.getPatientVolume());
            }
            statement.setLong(6, user.getRole().id());
            statement.setObject(7, user.getDoctor() != null ? user.getDoctor().getId() : null);
            statement.setObject(8, user.getNurse() != null ? user.getNurse().getId() : null);
            statement.setObject(9, user.getCategory() != null ? user.getCategory().id() : null);
            if (user.getDoctor() != null) {
                update(user.getDoctor());
            }
            if (user.getNurse() != null) {
                update(user.getNurse());
            }
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try (PreparedStatement statement = connection.prepareStatement(
                "update  user set username=?, password=?, birthday=?, " +
                        "patient_volume=?, role_id=?, doctor_id=?, nurse_id=?, category_id=? " +
                        "where user_id = ?"
        )) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setDate(3, user.getBirthday() != null ? java.sql.Date.valueOf(user.getBirthday()) : null);
            if (user.getRole() == Role.ROLE_DOCTOR || user.getRole() == Role.ROLE_NURSE) {
                statement.setObject(4, user.getPatientVolume() == null ? 0 : user.getPatientVolume());
            } else {
                statement.setObject(4, user.getPatientVolume() == null ? null : user.getPatientVolume());
            }
            statement.setLong(5, user.getRole().id());
            statement.setObject(6, user.getDoctor() != null ? user.getDoctor().getId() : null);
            statement.setObject(7, user.getNurse() != null ? user.getNurse().getId() : null);
            statement.setObject(8, user.getCategory() != null ? user.getCategory().id() : null);
            if (user.getDoctor() != null) {
                update(user.getDoctor());
            }
            if (user.getNurse() != null) {
                update(user.getNurse());
            }
            statement.setLong(9, user.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("delete from user where user_id = '" + user.getId() + "'");
            commit();
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