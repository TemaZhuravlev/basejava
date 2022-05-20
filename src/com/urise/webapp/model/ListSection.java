package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListSection extends AbstractSection {

    List<String> elements = new ArrayList<>();

    public ListSection(String... elements) {
        this.elements.addAll(Arrays.asList(elements));
    }

    @Override
    public String toString() {
        String temp = "";
        for (String element : elements) {
            temp += "- " + element + "\n";
        }
        return temp;
    }
}
