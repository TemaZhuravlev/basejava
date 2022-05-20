package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Organization {
    private String link;
    private String title;
    private List<Period> periods = new ArrayList<>();

    public Organization(String title, String link, Period... periods) {
        this.link = link;
        this.title = title;
        this.periods.addAll(Arrays.asList(periods));
    }

    @Override
    public String toString() {
        String temp = "";
        for (Period element : periods) {
            temp += element.toString() + "\n";
        }
        return title + "(" + link + ")\n" + temp;
    }
}
