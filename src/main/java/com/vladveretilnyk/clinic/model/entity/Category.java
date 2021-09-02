package com.vladveretilnyk.clinic.model.entity;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Category {
    PEDIATRICIAN(1L, "Pediatrician", "Педіатр"), TRAUMATOLOGIST(2L, "Traumatologist", "Травматолог"),
    SURGEON(3L, "Surgeon", "Хірург"), OPHTHALMOLOGIST(4L, "Ophthalmologist", "Окуліст"),
    DENTIST(5L, "Dentist", "Дантист"), LOR(6L, "Lor", "Лор");

    Category(Long id, String engName, String ukrName) {
        this.id = id;
        this.engName = engName;
        this.ukrName = ukrName;
    }

    private final Long id;
    private final String engName;
    private final String ukrName;

    public Long id() {
        return id;
    }

    public String engName() {
        return engName;
    }

    public String ukrName() {
        return ukrName;
    }

    public static Category categoryById(Long id) {
        return Arrays.stream(values()).filter(category -> category.id.equals(id)).collect(Collectors.toList()).get(0);
    }

    public static Category categoryByName(String categoryName) {
      return  Arrays.stream(values()).filter(category -> category.engName.equals(categoryName) ||
                category.ukrName.equals(categoryName)).
                collect(Collectors.toList()).get(0);
    }
}
