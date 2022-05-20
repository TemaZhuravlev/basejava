package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
        StringBuilder temp = new StringBuilder();
        for (Period element : periods) {
            temp.append(element.toString()).append("\n");
        }
        return title + "(" + link + ")\n" + temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return link.equals(that.link) && title.equals(that.title) && periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, title, periods);
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public List<Period> getPeriods() {
        return periods;
    }
}
