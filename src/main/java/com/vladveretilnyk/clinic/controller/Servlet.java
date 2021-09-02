package com.vladveretilnyk.clinic.controller;

import com.vladveretilnyk.clinic.controller.command.Command;
import com.vladveretilnyk.clinic.controller.command.impl.assign.AssignStaffForPatientCommand;
import com.vladveretilnyk.clinic.controller.command.impl.authentication.LoginCommand;
import com.vladveretilnyk.clinic.controller.command.impl.authentication.LogoutCommand;
import com.vladveretilnyk.clinic.controller.command.impl.create.CreateDoctorCommand;
import com.vladveretilnyk.clinic.controller.command.impl.create.CreateNoteCommand;
import com.vladveretilnyk.clinic.controller.command.impl.create.CreateNurseCommand;
import com.vladveretilnyk.clinic.controller.command.impl.create.CreatePatientCommand;
import com.vladveretilnyk.clinic.controller.command.impl.make.MakeProceduresCommand;
import com.vladveretilnyk.clinic.controller.command.impl.make.MakeSurgeryCommand;
import com.vladveretilnyk.clinic.controller.command.impl.remove.DischargeUserCommand;
import com.vladveretilnyk.clinic.controller.command.impl.remove.RemoveStaffForPatientCommand;
import com.vladveretilnyk.clinic.controller.command.impl.remove.RemoveUserCommand;
import com.vladveretilnyk.clinic.controller.command.impl.view.ViewNoteCommand;
import com.vladveretilnyk.clinic.controller.command.impl.view.ViewNotesCommand;
import com.vladveretilnyk.clinic.controller.command.impl.view.ViewUserIndexPageCommand;
import com.vladveretilnyk.clinic.controller.command.impl.view.ViewUsersCommand;
import com.vladveretilnyk.clinic.model.exception.NoteNotFoundException;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Servlet extends HttpServlet {
    private static final String regexPatch = ".*/app/";
    private final Map<String, Command> commands = new HashMap<>();

    public void init() {
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("admin", new ViewUserIndexPageCommand());
        commands.put("doctor", new ViewUserIndexPageCommand());
        commands.put("nurse", new ViewUserIndexPageCommand());
        commands.put("admin/doctors", new ViewUsersCommand());
        commands.put("admin/nurses", new ViewUsersCommand());
        commands.put("admin/patients", new ViewUsersCommand());
        commands.put("admin/doctors/create", new CreateDoctorCommand());
        commands.put("admin/nurses/create", new CreateNurseCommand());
        commands.put("admin/patients/create", new CreatePatientCommand());
        commands.put("admin/patients/delete", new RemoveUserCommand());
        commands.put("admin/patients/remove-doctor", new RemoveStaffForPatientCommand());
        commands.put("admin/patients/assign-doctor", new AssignStaffForPatientCommand());
        commands.put("doctor/patients/remove-nurse", new RemoveStaffForPatientCommand());
        commands.put("doctor/patients/assign-nurse", new AssignStaffForPatientCommand());
        commands.put("doctor/patients/discharge", new DischargeUserCommand());
        commands.put("doctor/patients/medical-book", new ViewNotesCommand());
        commands.put("nurse/patients/medical-book", new ViewNotesCommand());
        commands.put("doctor/patients/medical-book/create", new CreateNoteCommand());
        commands.put("doctor/patients/medical-book/note", new ViewNoteCommand());
        commands.put("nurse/patients/medical-book/note", new ViewNoteCommand());
        commands.put("doctor/patients/medical-book/note/make-procedures", new MakeProceduresCommand());
        commands.put("nurse/patients/medical-book/note/make-procedures", new MakeProceduresCommand());
        commands.put("doctor/patients/medical-book/note/make-surgery", new MakeSurgeryCommand());
    }

    @SneakyThrows
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }


    @SneakyThrows
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UserNotFoundException, NoteNotFoundException {
        String path = request.getRequestURI();
        path = path.replaceAll(regexPatch, "");
        Command command = commands.getOrDefault(path,
                (r) -> "/view/login.jsp)");
        String page = command.execute(request);
        if (page.contains("redirect:")) {
            response.sendRedirect(page.replace("redirect:", "/app"));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}