package com.urise.webapp;

import java.time.LocalDate;
import java.util.Date;

public class MainDate {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);

        LocalDate ld = LocalDate.now();
        System.out.println(ld);


    }
}
