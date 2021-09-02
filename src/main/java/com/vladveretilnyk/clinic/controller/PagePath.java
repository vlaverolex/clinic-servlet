package com.vladveretilnyk.clinic.controller;

public interface PagePath {
    String FORBIDDEN_PAGE_PATH = "/view/forbidden.jsp";

    String LOGIN_PAGE_PATH = "/view/login.jsp";
    String ADMIN_INDEX_PAGE_PATH = "/view/admin/index.jsp";
    String DOCTOR_INDEX_PAGE_PATH = "/view/doctor/index.jsp";
    String NURSE_INDEX_PAGE_PATH = "/view/nurse/index.jsp";

    String DOCTORS_SHOW_PAGE_PATH = "/view/admin/doctors/show.jsp";
    String NURSES_SHOW_PAGE_PATH = "/view/admin/nurses/show.jsp";
    String PATIENTS_SHOW_PAGE_PATH = "/view/admin/patients/show.jsp";

    String DOCTOR_CREATE_PAGE_PATH = "/view/admin/doctors/create.jsp";
    String NURSE_CREATE_PAGE_PATH = "/view/admin/nurses/create.jsp";
    String PATIENT_CREATE_PAGE_PATH = "/view/admin/patients/create.jsp";

    String DOCTOR_ASSIGN_PAGE_PATH = "/view/admin/patients/assign-doctor.jsp";
    String NURSE_ASSIGN_PAGE_PATH = "/view/doctor/assign-nurse.jsp";

    String DOCTOR_MEDICAL_BOOK_PAGE_PATH = "/view/doctor/notes/notes.jsp";
    String NURSE_MEDICAL_BOOK_PAGE_PATH = "/view/nurse/notes/notes.jsp";

    String DOCTOR_NOTE_CREATE_PAGE_PATH = "/view/doctor/notes/create.jsp";

    String DOCTOR_MEDICAL_NOTE_PAGE_PATH = "/view/doctor/notes/show.jsp";
    String NURSE_MEDICAL_NOTE_PAGE_PATH = "/view/nurse/notes/show.jsp";
}
