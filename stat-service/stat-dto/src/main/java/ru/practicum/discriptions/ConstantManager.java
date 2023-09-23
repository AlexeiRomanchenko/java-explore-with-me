package ru.practicum.discriptions;

import java.time.format.DateTimeFormatter;

public class ConstantManager {
    public static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
}