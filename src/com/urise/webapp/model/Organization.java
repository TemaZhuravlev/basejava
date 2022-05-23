package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Organization {
    private final Link homePage;
    private final List<Period> periods = new ArrayList<>();

    public Organization(String name, String url, Period... periods) {
        this.homePage = new Link(name, url);
        this.periods.addAll(Arrays.asList(periods));
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (Period element : periods) {
            temp.append(element.toString()).append("\n");
        }
        return homePage.toString() + temp;
    }

    public List<Period> getPeriods() {
        return periods;
    }
}
