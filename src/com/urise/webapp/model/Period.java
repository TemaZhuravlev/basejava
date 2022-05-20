package com.urise.webapp.model;

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
}
