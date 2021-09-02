package com.vladveretilnyk.clinic.controller.command;

import com.vladveretilnyk.clinic.model.exception.NoteNotFoundException;
import com.vladveretilnyk.clinic.model.exception.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest req) throws UserNotFoundException, NoteNotFoundException;
}
