package com.urise.webapp.model;

import java.util.Objects;

public class Link {
    private final String name;
    private final String url;

    public Link(String name, String url) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link1 = (Link) o;
        return name.equals(link1.name) && Objects.equals(url, link1.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }

    @Override
    public String toString() {
        return name + "(" + url + ")\n";
    }
}
