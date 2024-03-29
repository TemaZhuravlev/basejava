package com.urise.webapp.model;


import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.NOW;
import static com.urise.webapp.util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public class Period implements Serializable {
    public static final Period EMPTY = new Period();
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateFrom;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateTo;
    private String title;
    private String description;

    public Period() {
    }

    public Period(int startYear, Month startMonth, String title, String description) {
        this(of(startYear, startMonth), NOW, title, description);
    }

    public Period(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
        this(of(startYear, startMonth), of(endYear, endMonth), title, description);
    }

    public Period(LocalDate dateFrom, LocalDate dateTo, String title, String description) {
        Objects.requireNonNull(dateFrom, "dateFrom must not be null");
        Objects.requireNonNull(dateTo, "dateTo must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.title = title;
        this.description = description == null ? "" : description;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return dateFrom.format(DateTimeFormatter.ofPattern("MM/YYYY")) + "-" +
                dateTo.format(DateTimeFormatter.ofPattern("MM/YYYY")) + "\n" + title + description;
    }

    public String toHtml() {
        return "<div class='period-position'><div class='period'>"
                + dateConvert(dateFrom) + "-" + dateConvert(dateTo) + "</div>"
                + "<div class='position'>" + title + "</div></div>" + "<div class='description'>" + description + "</div>";
    }

    private String dateConvert(LocalDate date) {
        if (!date.equals(DateUtil.EMPTY)) {
            if(date.equals(DateUtil.NOW) || date.isAfter(DateUtil.NOW)){
                return "Сейчас";
            }
            return date.format(DateTimeFormatter.ofPattern("MM/YYYY"));
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return dateFrom.equals(period.dateFrom) &&
                dateTo.equals(period.dateTo) &&
                title.equals(period.title) &&
                Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateFrom, dateTo, title, description);
    }
}
