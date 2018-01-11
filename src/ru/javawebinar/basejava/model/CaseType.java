package ru.javawebinar.basejava.model;

import java.util.List;

public class CaseType extends Sections {
    private final List<String> list;

    public CaseType(List<String> list) {
        this.list = list;
    }

    @Override
    protected Object getType() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
