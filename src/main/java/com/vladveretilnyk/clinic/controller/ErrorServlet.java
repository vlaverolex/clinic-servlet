package com.vladveretilnyk.clinic.controller;

import com.vladveretilnyk.clinic.model.exception.NoteNotFoundException;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;
import com.vladveretilnyk.clinic.model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorServlet extends HttpServlet {
    private final static Logger LOGGER = LoggerFactory.getLogger(ErrorServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        Object status = request.getAttribute("javax.servlet.error.status_code");

        if (status != null) {
            Integer statusCode = (Integer) status;

            if (statusCode == 404) {
                request.getRequestDispatcher("/view/error/error-404.jsp").forward(request, response);
                return;
            } else if (statusCode == 403) {
                request.getRequestDispatcher("/view/error/error-403.jsp").forward(request, response);
                return;
            }
        }


        LOGGER.error("Exception handled! Message = {}", throwable.getMessage());
        if (throwable.getClass() == UserNotFoundException.class) {
            request.getRequestDispatcher("/view/user_not_found.jsp").forward(request, response);
            return;
        }
        if (throwable.getClass() == NoteNotFoundException.class) {
            request.getRequestDispatcher("/view/note_not_found.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("/view/error/error.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}

