package ru.javawebinar.basejava.model;

import java.util.List;

public class OrganizationSections extends Sections<List<Organization>> {
    private final List<Organization> list;

    public OrganizationSections(List<Organization> list) {
        this.list = list;
    }


    @Override
    protected List<Organization> getContent() {
        return list;
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSections that = (OrganizationSections) o;

        return list != null ? list.equals(that.list) : that.list == null;
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }
}
