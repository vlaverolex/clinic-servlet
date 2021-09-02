package com.vladveretilnyk.clinic.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
public class User {
    private Long id;

    private String username;

    private String password;

    private String confirmPassword;

    private LocalDate birthday;

    private Integer patientVolume;

    private Role role;

    private Category category;

    private User doctor;

    private User nurse;

    public void incrementPatientVolume() {
        if (patientVolume != null) {
            patientVolume++;
        }
    }

    public void decrementPatientVolume() {
        if (patientVolume != null && patientVolume > 0) {
            patientVolume--;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
