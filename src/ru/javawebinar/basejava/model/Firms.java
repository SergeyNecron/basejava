package ru.javawebinar.basejava.model;

import java.time.LocalDate;

public class Firms {
    private final String title;
    private final String description;
    private final String web;
    private final LocalDate startDate;
    private final LocalDate endDate;


    public Firms(String title, String description, String web, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.web = web;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Firms firms = (Firms) o;

        if (title != null ? !title.equals(firms.title) : firms.title != null) return false;
        if (description != null ? !description.equals(firms.description) : firms.description != null) return false;
        if (web != null ? !web.equals(firms.web) : firms.web != null) return false;
        if (startDate != null ? !startDate.equals(firms.startDate) : firms.startDate != null) return false;
        return endDate != null ? endDate.equals(firms.endDate) : firms.endDate == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (web != null ? web.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Firms{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", web='" + web + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}

