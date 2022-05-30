package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Period {
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final String title;
    private final String description;

    public Period(LocalDate dateFrom, LocalDate dateTo, String title, String description) {
        Objects.requireNonNull(dateFrom, "dateFrom must not be null");
        Objects.requireNonNull(dateTo, "dateTo must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.title = title;
        this.description = description;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return dateFrom.format(DateTimeFormatter.ofPattern("MM/YYYY")) + "-" + dateTo.format(DateTimeFormatter.ofPattern("MM/YYYY")) + "\n" + title + description;
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
}
