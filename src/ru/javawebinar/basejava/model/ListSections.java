package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class ListSections extends Sections {
    private final List<String> items;

    public ListSections(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    protected List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return items.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSections that = (ListSections) o;

        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}
