package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Link homePage;
    private List<Period> periods = new ArrayList<>();

    public Organization() {
    }

    public Organization(String name, String url, Period... periods) {
        this(name, new Link(name, url), Arrays.asList(periods));
    }

    public Organization(String name, Link homePage, List<Period> periods) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(periods, "periods must not be null");
        this.name = name;
        this.homePage = homePage;
        this.periods = periods;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public String getName() {
        return name;
    }

    public Link getHomePage() {
        return homePage;
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (Period element : periods) {
            temp.append(element.toString()).append("\n");
        }
        return name + " " + homePage.toString() + temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return name.equals(that.name) && Objects.equals(homePage, that.homePage) && periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, homePage, periods);
    }
}
