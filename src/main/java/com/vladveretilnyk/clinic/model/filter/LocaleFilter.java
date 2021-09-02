package com.vladveretilnyk.clinic.model.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public class LocaleFilter implements Filter {
    private static final Locale ENG = new Locale("en");
    private static final Locale UKR = new Locale("uk");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();

        String lang = req.getParameter("lang");

        if (session.getAttribute("lang") == null) {
            if (lang != null) {
                session.setAttribute("lang", lang);
            } else {
                session.setAttribute("lang", ENG.getLanguage());
            }
        }else{
            if(lang!=null){
                session.setAttribute("lang", lang);
            }
        }


//        Locale currentLocale;
//        String lang = req.getParameter("lang");
//
//        if (lang != null &&
//                (lang.equals(ENG.getLanguage()) || lang.equals(UKR.getLanguage()))) {
//            currentLocale = new Locale(lang);
//        } else {
//            if (session.getAttribute("locale") == null) {
//                currentLocale = ENG;
//            } else {
//                currentLocale = (Locale) session.getAttribute("locale");
//            }
//        }


//        session.setAttribute("locale", currentLocale);

        filterChain.doFilter(servletRequest, servletResponse);
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        HttpSession session = req.getSession();
//
//        if (session.getAttribute("locale") == null) {
//            session.setAttribute("locale", ENG);
//        } else {
//            String lang = req.getParameter("lang");
//
//            if (lang != null && (lang.equals(ENG.getLanguage()) || lang.equals(UKR.getLanguage()))) {
//                session.setAttribute("locale", lang.equals(ENG.getLanguage()) ? ENG : UKR);
//            }
//        }
//
//        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}