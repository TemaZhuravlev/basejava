package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.now();
    public static final LocalDate EMPTY = LocalDate.of(1, 1, 1);
    public static LocalDate of(int year, Month month){
        return LocalDate.of(year, month, 1);
    }
}
