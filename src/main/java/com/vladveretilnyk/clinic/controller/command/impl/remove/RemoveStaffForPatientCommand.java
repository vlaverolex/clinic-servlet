package com.vladveretilnyk.clinic.controller.command.impl.remove;

import com.vladveretilnyk.clinic.controller.command.Command;
import com.vladveretilnyk.clinic.model.entity.Role;
import com.vladveretilnyk.clinic.model.entity.User;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class RemoveStaffForPatientCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) throws UserNotFoundException {
        if (req.getParameter("patientId") == null) {throw new UserNotFoundException("User not found!");}

        User user = (User) req.getSession().getAttribute("authorizedUser");

        if (user.getRole() == Role.ROLE_ADMIN) {
            try {
                UserService.removeDoctorForPatient(Long.valueOf(req.getParameter("patientId")));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (user.getRole() == Role.ROLE_DOCTOR) {
            try {
                UserService.removeNurseForPatient(Long.valueOf(req.getParameter("patientId")));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return redirect(user.getRole());
    }

    private String redirect(Role role) {
        return role == Role.ROLE_ADMIN ? "redirect:/admin/patients" : "redirect:/doctor";
    }
}
















//package com.vladveretilnyk.clinic.controller.command.impl.remove;
//
//        import com.vladveretilnyk.clinic.controller.command.Command;
//        import com.vladveretilnyk.clinic.model.entity.Role;
//        import com.vladveretilnyk.clinic.model.entity.User;
//        import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
//        import com.vladveretilnyk.clinic.model.service.UserService;
//
//        import javax.servlet.http.HttpServletRequest;
//        import java.sql.SQLException;
//
//public class RemoveStaffForPatientCommand implements Command {
//    @Override
//    public String execute(HttpServletRequest req) throws UserNotFoundException {
//        if (req.getParameter("patientId") == null) {throw new UserNotFoundException("User not found!");}
//
//        User user = (User) req.getSession().getAttribute("authorizedUser");
//        User patient = UserService.findUserById(Long.valueOf(req.getParameter("patientId")));
//
//        if (user.getRole() == Role.ROLE_ADMIN) {
//            if (patient.getDoctor() != null) {
//                patient.getDoctor().decrementPatientVolume();
//            }
//            if (patient.getNurse() != null) {
//                patient.getNurse().decrementPatientVolume();
//            }
//            UserService.update(patient);
//            patient.setDoctor(null);
//            patient.setNurse(null);
//        } else if (user.getRole() == Role.ROLE_DOCTOR) {
//            if (patient.getNurse() != null) {
//                patient.getNurse().decrementPatientVolume();
//            }
//            UserService.update(patient);
//            patient.setNurse(null);
//        }
//
//        try {
//            UserService.update(patient);
//            UserService.commit();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return redirect(user.getRole());
//        }
//
//        return redirect(user.getRole());
//    }
//
//    private String redirect(Role role) {
//        return role == Role.ROLE_ADMIN ? "redirect:/admin/patients" : "redirect:/doctor";
//    }
//}
