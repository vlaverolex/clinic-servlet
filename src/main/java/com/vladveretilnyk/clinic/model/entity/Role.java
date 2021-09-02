package com.vladveretilnyk.clinic.model.entity;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Role {
    ROLE_ADMIN(1L), ROLE_DOCTOR(2L), ROLE_NURSE(3L), ROLE_PATIENT(4L);

    Role(Long id) {
        this.id = id;
    }

    private final Long id;

    public Long id() {
        return id;
    }

    public static Role roleById(Long id) {
        return Arrays.stream(values()).filter(role -> role.id.equals(id)).collect(Collectors.toList()).get(0);
    }
}
