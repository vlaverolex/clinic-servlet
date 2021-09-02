package com.vladveretilnyk.clinic.model.service;


import com.vladveretilnyk.clinic.model.dao.DaoFactory;
import com.vladveretilnyk.clinic.model.dao.UserDao;
import com.vladveretilnyk.clinic.model.entity.Category;
import com.vladveretilnyk.clinic.model.entity.Role;
import com.vladveretilnyk.clinic.model.entity.User;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.pagination.Pager;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserService {
    private static final UserDao USER_DAO = DaoFactory.getInstance().createUserDao();
    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public static User findUserByUsername(String username) throws UserNotFoundException {
        return USER_DAO.findUserByUsername(username);
    }

    public static User findUserById(Long id) throws UserNotFoundException {
        return USER_DAO.findUserById(id);
    }

    public static User findUserByCredentials(String username, String password) throws UserNotFoundException {
        User user = findUserByUsername(username);
        return user != null && BCrypt.checkpw(password, user.getPassword()) ? user : null;
    }

    public static List<User> findAllDoctors() throws UserNotFoundException {
        return USER_DAO.findUsersByRole(Role.ROLE_DOCTOR);
    }

    public static List<User> findAllDoctors(Pager pager) throws UserNotFoundException {
        return USER_DAO.findUsersByRole(Role.ROLE_DOCTOR, pager);
    }


    public static Map<Category, List<User>> findDoctorsGroupByCategories() {
        return USER_DAO.findDoctorsGroupByCategories();
    }


    public static List<User> findAllNurses() throws UserNotFoundException {
        return USER_DAO.findUsersByRole(Role.ROLE_NURSE);
    }


    public static List<User> findAllPatients() throws UserNotFoundException {
        return USER_DAO.findUsersByRole(Role.ROLE_PATIENT);
    }

    public static List<User> findAllNurses(Pager pager) throws UserNotFoundException {
        return USER_DAO.findUsersByRole(Role.ROLE_NURSE, pager);
    }


    public static List<User> findAllPatients(Pager pager) throws UserNotFoundException {
        return USER_DAO.findUsersByRole(Role.ROLE_PATIENT, pager);
    }


    public static List<User> findPatientsByNurse(User nurse) throws UserNotFoundException {
        return USER_DAO.findPatientsByNurse(nurse);
    }


    public static List<User> findPatientsByDoctor(User doctor) throws UserNotFoundException {
        return USER_DAO.findPatientsByDoctor(doctor);
    }

    public static List<User> findPatientsByNurse(User nurse, Pager pager) throws UserNotFoundException {
        return USER_DAO.findPatientsByNurse(nurse, pager);
    }


    public static List<User> findPatientsByDoctor(User doctor, Pager pager) throws UserNotFoundException {
        return USER_DAO.findPatientsByDoctor(doctor, pager);
    }


    public static void save(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        USER_DAO.save(user);
        LOGGER.info("User saved = {}", user);
    }

    public static void update(User user) {
        USER_DAO.update(user);
        LOGGER.info("User updated = {}", user);
    }

    public static void delete(User user) throws UserNotFoundException {
        try {
            removeDoctorForPatient(user.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        USER_DAO.delete(user);
        LOGGER.info("User deleted = {}", user);
        try {
            commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void assignDoctorForPatient(Long patientId, Long doctorId) throws UserNotFoundException, SQLException {
        User doctor = findUserById(doctorId);
        User patient = findUserById(patientId);

        patient.setDoctor(doctor);
        doctor.incrementPatientVolume();

        update(patient);
        commit();
        LOGGER.info("Doctor = {} assigned for patient patient = {}", doctor, patient);
    }


    public static void assignNurseForPatient(Long patientId, Long nurseId) throws UserNotFoundException, SQLException {
        User nurse = findUserById(nurseId);
        User patient = findUserById(patientId);

        patient.setNurse(nurse);
        nurse.incrementPatientVolume();

        update(patient);
        commit();
        LOGGER.info("Nurse = {} assigned for patient patient = {}", nurse, patient);
    }


    public static void removeDoctorForPatient(Long id) throws UserNotFoundException, SQLException {
        User patient = findUserById(id);

        if (patient.getDoctor() != null) {
            patient.getDoctor().decrementPatientVolume();
        }
        if (patient.getNurse() != null) {
            patient.getNurse().decrementPatientVolume();
        }

        update(patient);
        User doctor = patient.getDoctor();
        patient.setDoctor(null);
        patient.setNurse(null);
        update(patient);
        commit();
        if (doctor != null) {
            LOGGER.info("Doctor = {} removed for patient patient = {}", doctor, patient);
        }
    }


    public static void removeNurseForPatient(Long id) throws UserNotFoundException, SQLException {
        User patient = findUserById(id);

        if (patient.getNurse() != null) {
            patient.getNurse().decrementPatientVolume();
            update(patient);
            User nurse = patient.getNurse();
            patient.setNurse(null);
            update(patient);
            commit();
            if (nurse != null) {
                LOGGER.info("Nurse = {} removed for patient patient = {}", nurse, patient);
            }
        }
    }

    public static void commit() throws SQLException {
        USER_DAO.commit();
    }
}


//package com.vladveretilnyk.clinic.model.service;
//
//        import com.vladveretilnyk.clinic.model.dao.DaoFactory;
//        import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
//        import com.vladveretilnyk.clinic.model.pagination.Pager;
//        import com.vladveretilnyk.clinic.model.dao.UserDao;
//        import com.vladveretilnyk.clinic.model.entity.Category;
//        import com.vladveretilnyk.clinic.model.entity.Role;
//        import com.vladveretilnyk.clinic.model.entity.User;
//        import org.mindrot.jbcrypt.BCrypt;
//
//        import java.sql.SQLException;
//        import java.util.List;
//        import java.util.Map;
//
//public class UserService {
//    private static final UserDao USER_DAO = DaoFactory.getInstance().createUserDao();
//
//
//    public static User findUserByUsername(String username) throws UserNotFoundException {
//        return USER_DAO.findUserByUsername(username);
//    }
//
//    public static User findUserById(Long id) throws UserNotFoundException {
//        return USER_DAO.findUserById(id);
//    }
//
//    public static User findUserByCredentials(String username, String password) throws UserNotFoundException {
//        User user = findUserByUsername(username);
//        return user != null && BCrypt.checkpw(password, user.getPassword()) ? user : null;
//    }
//
//    public static List<User> findAllDoctors() throws UserNotFoundException {
//        return USER_DAO.findUsersByRole(Role.ROLE_DOCTOR);
//    }
//
//    public static List<User> findAllDoctors(Pager pager) throws UserNotFoundException {
//        return USER_DAO.findUsersByRole(Role.ROLE_DOCTOR, pager);
//    }
//
//
//    public static Map<Category, List<User>> findDoctorsGroupByCategories() {
//        return USER_DAO.findDoctorsGroupByCategories();
//    }
//
//
//    public static List<User> findAllNurses() throws UserNotFoundException {
//        return USER_DAO.findUsersByRole(Role.ROLE_NURSE);
//    }
//
//
//    public static List<User> findAllPatients() throws UserNotFoundException {
//        return USER_DAO.findUsersByRole(Role.ROLE_PATIENT);
//    }
//
//    public static List<User> findAllNurses(Pager pager) throws UserNotFoundException {
//        return USER_DAO.findUsersByRole(Role.ROLE_NURSE, pager);
//    }
//
//
//    public static List<User> findAllPatients(Pager pager) throws UserNotFoundException {
//        return USER_DAO.findUsersByRole(Role.ROLE_PATIENT, pager);
//    }
//
//
//    public static List<User> findPatientsByNurse(User nurse) throws UserNotFoundException {
//        return USER_DAO.findPatientsByNurse(nurse);
//    }
//
//
//    public static List<User> findPatientsByDoctor(User doctor) throws UserNotFoundException {
//        return USER_DAO.findPatientsByDoctor(doctor);
//    }
//
//    public static List<User> findPatientsByNurse(User nurse, Pager pager) throws UserNotFoundException {
//        return USER_DAO.findPatientsByNurse(nurse, pager);
//    }
//
//
//    public static List<User> findPatientsByDoctor(User doctor, Pager pager) throws UserNotFoundException {
//        return USER_DAO.findPatientsByDoctor(doctor, pager);
//    }
//
//
//    public static void save(User user) {
//        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
//        USER_DAO.save(user);
//    }
//
//
//    public static void update(User user) {
//        USER_DAO.update(user);
//    }
//
//
//    public static void assignDoctorForPatient(Long patientId, Long doctorId) throws UserNotFoundException {
//        User doctor = findUserById(doctorId);
//        User patient = findUserById(patientId);
//
//        patient.setDoctor(doctor);
//        doctor.incrementPatientVolume();
//
//        update(patient);
//    }
//
//
//    public static void assignNurseForPatient(Long patientId, Long nurseId) throws UserNotFoundException {
//
//    }
//
//
//    public static void removeDoctorForPatient(Long id) throws UserNotFoundException {
//
//    }
//
//
//    public static void removeNurseForPatient(Long id) throws UserNotFoundException {
//
//    }
//
//    public static void commit() throws SQLException {
//        USER_DAO.commit();
//    }
//}