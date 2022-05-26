package com.urise.webapp.model;

import java.util.Objects;

public class Link {
    private final String title;
    private final String url;

    public Link(String title, String url) {
        Objects.requireNonNull(title, "name must not be null");
        this.title = title;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link1 = (Link) o;
        return title.equals(link1.title) && Objects.equals(url, link1.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url);
    }

    @Override
    public String toString() {
        return "(" + title + " " + url + ")\n";
    }
}
