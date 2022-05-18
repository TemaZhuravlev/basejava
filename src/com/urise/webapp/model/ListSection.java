package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListSection extends AbstractSection{

    List<String> elements;

    public ListSection(String...elements){
        this.elements = new ArrayList<>();
        this.elements.addAll(Arrays.asList(elements));
    }

    @Override
    public void showInfo() {
        for (String element : elements) {
            System.out.println("- " + element);
        }
    }
}
