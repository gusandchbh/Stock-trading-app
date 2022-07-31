package com.bonqa.bonqa.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateFormatter {
    public static String formatDate(LocalDate date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(dateTimeFormatter);
    }
}
