package com.vladveretilnyk.clinic.model.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateUtils {
    private LocalDateUtils(){}

    public static LocalDate parse(String stringDate){
        return LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
