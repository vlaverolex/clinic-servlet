package com.vladveretilnyk.clinic.controller.command.impl.authentication;

import com.vladveretilnyk.clinic.controller.command.Command;
import com.vladveretilnyk.clinic.model.entity.Role;

import javax.servlet.http.HttpServletRequest;

import static com.vladveretilnyk.clinic.controller.PagePath.LOGIN_PAGE_PATH;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().removeAttribute("authorizedUser");
        return "redirect:/login?logout";
    }
}
