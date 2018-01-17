package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.Month;

import static java.time.Month.JANUARY;

public class DateUtil {
    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate of(int year) {
        return LocalDate.of(year, JANUARY, 1);
    }
}