package ru.javawebinar.basejava.model;

import java.util.List;

public class ListType extends Sections<List<String>> {
    private final List<String> list;

    public ListType(List<String> list) {
        this.list = list;
    }


    @Override
    protected List<String> getType() {
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

        ListType listType = (ListType) o;

        return list != null ? list.equals(listType.list) : listType.list == null;
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }
}
