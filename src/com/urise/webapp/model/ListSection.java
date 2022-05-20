package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {

    private final List<String> elements = new ArrayList<>();

    public ListSection(String... elements) {
        this.elements.addAll(Arrays.asList(elements));
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (String element : elements) {
            temp.append("- ").append(element).append("\n");
        }
        return temp.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return elements.equals(that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

    public List<String> getElements() {
        return elements;
    }
}
