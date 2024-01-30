package com.project.stackoverflow.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateTimeParser {
    public LocalDateTime convertStringToLocalDateTime(String dateString) {
        // Define the formatter for the given date-time pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Parse the string into LocalDateTime
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);

        return dateTime;
    }
}
