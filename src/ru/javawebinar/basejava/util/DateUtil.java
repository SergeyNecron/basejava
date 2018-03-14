package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.exception.NotDateException;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
    private static final String textNow = "сейчас";

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate date) {
        if (date == null) return "";
        else if (date.equals(NOW)) return textNow;
        else return date.getMonthValue() + "/" + date.getYear();

    }

    public static String format(int year) {
        if (year == 0) return "";
        else if (year == NOW.getYear()) return textNow;
        else return "" + year;
    }

    public static LocalDate parser(String date) {
        if (date.trim().length() == 0 || textNow.equals(date)) return NOW;
        String[] s = date.split("/");
        LocalDate d = LocalDate.now();
        int yearNow = d.getYear();
        int month;
        int year;
        try {
            month = Integer.valueOf(s[0]);
            if (s.length > 1)
                year = Integer.valueOf(s[1]);
            else {
                year = month;
                month = 1;
            }
        } catch (
                Exception e) {
            throw new NotDateException(date);
        }
        if (month < 0 || month > 13 || year < 1950 || year > yearNow) {
            throw new NotDateException(date);
        }
        return LocalDate.of(year, month, 1);
    }


}
