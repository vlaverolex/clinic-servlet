package com.vladveretilnyk.clinic;

import com.vladveretilnyk.clinic.model.entity.Role;
import com.vladveretilnyk.clinic.model.entity.User;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.service.UserService;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class UserServiceTest {

    @Test(expected = Exception.class)
    public void tryingToSaveAnExistingUserShouldThrowAnException() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setRole(Role.ROLE_ADMIN);
        UserService.save(user);
    }

    @Test(expected = UserNotFoundException.class)
    public void findByIdShouldThrowAnExceptionWhenUserNotFound() throws UserNotFoundException {
        UserService.findUserById(-2L);
    }

    @Test
    public void allDoctorsShouldHaveDoctorRole() throws UserNotFoundException {
        List<User> doctors = UserService.findAllDoctors();
        Assert.assertEquals(doctors.size(),
                doctors.stream().filter(user -> user.getRole() == Role.ROLE_DOCTOR).count());
    }

    @Test
    public void allNursesShouldHaveNurseRole() throws UserNotFoundException {
        List<User> nurses = UserService.findAllNurses();
        Assert.assertEquals(nurses.size(),
                nurses.stream().filter(user -> user.getRole() == Role.ROLE_NURSE).count());
    }

    @Test
    public void allPatientsShouldHavePatientRole() throws UserNotFoundException {
        List<User> patients = UserService.findAllPatients();
        Assert.assertEquals(patients.size(),
                patients.stream().filter(user -> user.getRole() == Role.ROLE_PATIENT).count());
    }

    @Test
    public void patientsShouldHaveSpecificDoctor() throws SQLException, UserNotFoundException {
        User doctor = new User();
        doctor.setId(-1L);
        doctor.setUsername("test_doctor");
        doctor.setPassword("test_doctor");
        doctor.setRole(Role.ROLE_DOCTOR);
        UserService.save(doctor);

        for (int i = 0; i < 100; i++) {
            User patient = new User();
            patient.setId(-i-2L);
            patient.setUsername("test_patient"+i);
            patient.setPassword("test_patient");
            patient.setRole(Role.ROLE_PATIENT);
            patient.setDoctor(doctor);
            UserService.save(patient);
        }
        UserService.commit();

        List<User> patients = UserService.findPatientsByDoctor(doctor);
        Assert.assertEquals(patients.size(), patients.stream().filter(patient -> patient.getDoctor().getUsername().equals(doctor.getUsername())).count());

        patients.forEach(UserService::delete);
        UserService.delete(doctor);
    }

    @Test
    public void patientsShouldHaveSpecificNurse() throws SQLException, UserNotFoundException {
        User nurse = new User();
        nurse.setId(-1L);
        nurse.setUsername("test_nurse");
        nurse.setPassword("test_nurse");
        nurse.setRole(Role.ROLE_NURSE);
        UserService.save(nurse);

        for (int i = 0; i < 100; i++) {
            User patient = new User();
            patient.setId(-i-2L);
            patient.setUsername("test_patient"+i);
            patient.setPassword("test_patient");
            patient.setRole(Role.ROLE_PATIENT);
            patient.setNurse(nurse);
            UserService.save(patient);
        }
        UserService.commit();

        List<User> patients = UserService.findPatientsByNurse(nurse);
        Assert.assertEquals(patients.size(), patients.stream().filter(patient -> patient.getNurse().getUsername().equals(nurse.getUsername())).count());

        patients.forEach(UserService::delete);
        UserService.delete(nurse);
    }

    @Test
    public void patientShouldHaveDoctorAfterAssigning() throws SQLException, UserNotFoundException {
        User doctor = new User();
        doctor.setId(-1L);
        doctor.setUsername("test_doctor");
        doctor.setPassword("test_doctor");
        doctor.setRole(Role.ROLE_DOCTOR);
        UserService.save(doctor);

        User patient = new User();
        patient.setId(-2L);
        patient.setUsername("test_patient");
        patient.setPassword("test_patient");
        patient.setRole(Role.ROLE_PATIENT);
        UserService.save(patient);

        UserService.assignDoctorForPatient(patient.getId(), doctor.getId());

        UserService.commit();

        Assert.assertEquals(doctor, UserService.findUserById(patient.getId()).getDoctor());

        UserService.delete(patient);
        UserService.delete(doctor);
    }


    @Test
    public void patientShouldHaveNurseAfterAssigning() throws SQLException, UserNotFoundException {
        User nurse = new User();
        nurse.setId(-1L);
        nurse.setUsername("test_nurse");
        nurse.setPassword("test_nurse");
        nurse.setRole(Role.ROLE_NURSE);
        UserService.save(nurse);

        User patient = new User();
        patient.setId(-2L);
        patient.setUsername("test_patient");
        patient.setPassword("test_patient");
        patient.setRole(Role.ROLE_PATIENT);
        UserService.save(patient);

        UserService.assignNurseForPatient(patient.getId(), nurse.getId());

        UserService.commit();

        Assert.assertEquals(nurse, UserService.findUserById(patient.getId()).getNurse());

        UserService.delete(patient);
        UserService.delete(nurse);
    }

    @Test
    public void patientShouldNotHaveDoctorAfterRemoving() throws UserNotFoundException, SQLException {
        User doctor = new User();
        doctor.setId(-1L);
        doctor.setUsername("test_doctor");
        doctor.setPassword("test_doctor");
        doctor.setRole(Role.ROLE_DOCTOR);
        UserService.save(doctor);

        User patient = new User();
        patient.setId(-2L);
        patient.setUsername("test_patient");
        patient.setPassword("test_patient");
        patient.setRole(Role.ROLE_PATIENT);
        UserService.save(patient);

        UserService.assignDoctorForPatient(patient.getId(), doctor.getId());
        UserService.commit();

        UserService.removeDoctorForPatient(patient.getId());

        Assert.assertNull(UserService.findUserById(patient.getId()).getDoctor());

        UserService.delete(patient);
        UserService.delete(doctor);
    }

    @Test
    public void patientShouldNotHaveNurseAfterRemoving() throws UserNotFoundException, SQLException {
        User nurse = new User();
        nurse.setId(-1L);
        nurse.setUsername("test_nurse");
        nurse.setPassword("test_nurse");
        nurse.setRole(Role.ROLE_NURSE);
        UserService.save(nurse);

        User patient = new User();
        patient.setId(-2L);
        patient.setUsername("test_patient");
        patient.setPassword("test_patient");
        patient.setRole(Role.ROLE_PATIENT);
        UserService.save(patient);

        UserService.assignNurseForPatient(patient.getId(), nurse.getId());
        UserService.commit();

        UserService.removeNurseForPatient(patient.getId());

        Assert.assertNull(UserService.findUserById(patient.getId()).getNurse());

        UserService.delete(patient);
        UserService.delete(nurse);
    }
}
