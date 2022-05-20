package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrganizationSection extends AbstractSection {

    private List<Organization> organizations = new ArrayList<>();

    public OrganizationSection(Organization... organizations) {
        this.organizations.addAll(Arrays.asList(organizations));
    }

    @Override
    public String toString() {
        String temp = "";
        for (Organization element : organizations) {
            temp += element.toString() + "\n";
        }
        return temp;
    }
}
