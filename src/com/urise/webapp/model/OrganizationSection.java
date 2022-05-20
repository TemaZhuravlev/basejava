package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {

    private final List<Organization> organizations = new ArrayList<>();

    public OrganizationSection(Organization... organizations) {
        this.organizations.addAll(Arrays.asList(organizations));
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (Organization element : organizations) {
            temp.append(element.toString()).append("\n");
        }
        return temp.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }
}
