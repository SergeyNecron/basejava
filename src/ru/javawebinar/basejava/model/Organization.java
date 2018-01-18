package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class Organization {

    private final Link homePage;
    private List<DateOrganization> dateOrganization = new ArrayList<>();


    public Organization(String name, String url, List dateOrganization) {
        this.homePage = new Link(name, url);
        this.dateOrganization = dateOrganization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (homePage != null ? !homePage.equals(that.homePage) : that.homePage != null) return false;
        return dateOrganization != null ? dateOrganization.equals(that.dateOrganization) : that.dateOrganization == null;
    }

    @Override
    public int hashCode() {
        int result = homePage != null ? homePage.hashCode() : 0;
        result = 31 * result + (dateOrganization != null ? dateOrganization.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", dateOrganization=" + dateOrganization +
                '}';
    }
}

