package com.vladveretilnyk.clinic.model.filter;

import com.vladveretilnyk.clinic.model.entity.Role;
import com.vladveretilnyk.clinic.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.vladveretilnyk.clinic.controller.PagePath.FORBIDDEN_PAGE_PATH;

public class SecurityFilter implements Filter {
    private static final Map<Role, String> MAP = new HashMap<>();

    static {
        MAP.put(Role.ROLE_ADMIN, "/admin");
        MAP.put(Role.ROLE_DOCTOR, "/doctor");
        MAP.put(Role.ROLE_NURSE, "/nurse");
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("authorizedUser");
        String path = request.getRequestURI();

        if (user == null) {
            if (path.equals("/app/login")) {
                filterChain.doFilter(request, response);
                return;
            }
        } else {
            if (path.contains("/app/login")) {
                response.sendRedirect("/app" + MAP.get(user.getRole()));
                return;
            }
            if (path.equals("/app/logout")) {
                filterChain.doFilter(request, response);
                return;
            }
            if (path.contains(MAP.get(user.getRole()))) {
                request.getRequestDispatcher(path).forward(request, response);
                return;
            }
        }

        request.getRequestDispatcher("/view/forbidden.jsp").forward(request, response);
    }

    @Override
    public void destroy() {

    }
}
