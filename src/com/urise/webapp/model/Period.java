package com.urise.webapp.model;

import java.util.Objects;

public class Period {
    private String dateFrom;
    private String dateTo;
    private String description;

    public Period(String dateFrom, String dateTo, String description) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.description = description;
    }

    @Override
    public String toString() {
        return dateFrom + "-" + dateTo + "\n" + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return dateFrom.equals(period.dateFrom) && dateTo.equals(period.dateTo) && description.equals(period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateFrom, dateTo, description);
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public String getDescription() {
        return description;
    }
}
