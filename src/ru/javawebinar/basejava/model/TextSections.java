package ru.javawebinar.basejava.model;

import java.util.List;

public class TextSections extends Sections<List<String>> {
    private final List<String> list;

    public TextSections(List<String> list) {
        this.list = list;
    }


    @Override
    protected List<String> getContent() {
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

        TextSections textSections = (TextSections) o;

        return list != null ? list.equals(textSections.list) : textSections.list == null;
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }
}
