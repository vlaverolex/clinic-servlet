package com.vladveretilnyk.clinic.controller.command.impl.assign;

import com.vladveretilnyk.clinic.controller.command.Command;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.pagination.Pager;
import com.vladveretilnyk.clinic.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;

import static com.vladveretilnyk.clinic.controller.PagePath.DOCTOR_ASSIGN_PAGE_PATH;

public class AssignDoctorCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) throws UserNotFoundException {
        if(req.getParameter("doctorId")==null){
            req.setAttribute("doctors", UserService.findAllDoctors((Pager) req.getAttribute("pager")));
            return DOCTOR_ASSIGN_PAGE_PATH;
        }

        try {
            UserService.assignDoctorForPatient(Long.valueOf(req.getParameter("patientId")),
                    Long.valueOf(req.getParameter("doctorId")));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "redirect:/admin/patients";
    }
}






















//package com.vladveretilnyk.clinic.controller.command.impl.assign;
//
//        import com.vladveretilnyk.clinic.controller.command.Command;
//        import com.vladveretilnyk.clinic.model.entity.User;
//        import com.vladveretilnyk.clinic.model.exception.NoteNotFoundException;
//        import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
//        import com.vladveretilnyk.clinic.model.pagination.Pager;
//        import com.vladveretilnyk.clinic.model.service.UserService;
//
//        import javax.servlet.http.HttpServletRequest;
//
//        import java.sql.SQLException;
//
//        import static com.vladveretilnyk.clinic.controller.PagePath.DOCTOR_ASSIGN_PAGE_PATH;
//
//public class AssignDoctorCommand implements Command {
//    @Override
//    public String execute(HttpServletRequest req) throws UserNotFoundException {
//        if(req.getParameter("doctorId")==null){
//            req.setAttribute("doctors", UserService.findAllDoctors((Pager) req.getAttribute("pager")));
//            return DOCTOR_ASSIGN_PAGE_PATH;
//        }
//
//        UserService.assignDoctorForPatient(Long.valueOf(req.getParameter("patientId")),Long.valueOf(req.getParameter("doctorId")));
//
//
//        User doctor = UserService.findUserById(Long.valueOf(req.getParameter("doctorId")));
//        User patient = (User) req.getAttribute("patient");
//
//        patient.setDoctor(doctor);
//        doctor.incrementPatientVolume();
//
//        UserService.update(patient);
//        try {
//            UserService.commit();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//        return "redirect:/admin/patients";
//    }
//}
