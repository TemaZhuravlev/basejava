package com.urise.webapp.model;

import java.util.Objects;

public class Period {
    private final String dateFrom;
    private final String dateTo;
    private final String title;
    private final String description;

    public Period(String dateFrom, String dateTo, String title, String description) {
        Objects.requireNonNull(dateFrom, "dateFrom must not be null");
        Objects.requireNonNull(dateTo, "dateTo must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return dateFrom + "-" + dateTo + "\n" + title + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return dateFrom.equals(period.dateFrom) && dateTo.equals(period.dateTo) && title.equals(period.title) && Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateFrom, dateTo, title, description);
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
