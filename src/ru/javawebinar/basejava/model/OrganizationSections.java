package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class OrganizationSections extends Sections {
    private final List<Organization> organizations;

    public OrganizationSections(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    protected List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public String toString() {
        return organizations.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSections that = (OrganizationSections) o;

        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }
}
