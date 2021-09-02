package com.vladveretilnyk.clinic.controller.command.impl.assign;

import com.vladveretilnyk.clinic.controller.command.Command;
import com.vladveretilnyk.clinic.model.entity.Role;
import com.vladveretilnyk.clinic.model.entity.User;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.pagination.Pager;
import com.vladveretilnyk.clinic.model.pagination.Sorting;
import com.vladveretilnyk.clinic.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class AssignStaffForPatientCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) throws UserNotFoundException {
        User patient = UserService.findUserById(Long.valueOf(req.getParameter("patientId")));

        User user = (User) req.getSession().getAttribute("authorizedUser");

        Pager pager = Pager.of(req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page")), req.getParameter("column") == null ?
                Sorting.USER_DEFAULT : new Sorting(req.getParameter("column"), req.getParameter("direction")));

        req.setAttribute("patientId", patient.getId());
        req.setAttribute("pager", pager);

        if (user.getRole() == Role.ROLE_ADMIN) {
            return new AssignDoctorCommand().execute(req);
        } else {
            return new AssignNurseCommand().execute(req);
        }
    }
}


//package com.vladveretilnyk.clinic.controller.command.impl.assign;
//
//        import com.vladveretilnyk.clinic.controller.command.Command;
//        import com.vladveretilnyk.clinic.model.entity.Role;
//        import com.vladveretilnyk.clinic.model.entity.User;
//        import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
//        import com.vladveretilnyk.clinic.model.pagination.Pager;
//        import com.vladveretilnyk.clinic.model.pagination.Sorting;
//        import com.vladveretilnyk.clinic.model.service.UserService;
//
//        import javax.servlet.http.HttpServletRequest;
//
//public class AssignStaffForPatientCommand implements Command {
//    @Override
//    public String execute(HttpServletRequest req) throws UserNotFoundException {
//        if (req.getParameter("patientId") == null) {throw new UserNotFoundException("User not found!");}
//
//        User user = (User) req.getSession().getAttribute("authorizedUser");
//        User patient = UserService.findUserById(Long.valueOf(req.getParameter("patientId")));
//
//        Pager pager = Pager.of(req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page")), req.getParameter("column") == null ?
//                Sorting.USER_DEFAULT : new Sorting(req.getParameter("column"), req.getParameter("direction")));
//
//        req.setAttribute("patient", patient);
//        req.setAttribute("pager", pager);
//
//        if (user.getRole() == Role.ROLE_ADMIN) {
//            return new AssignDoctorCommand().execute(req);
//        } else {
//            return new AssignNurseCommand().execute(req);
//        }
//    }
//}

