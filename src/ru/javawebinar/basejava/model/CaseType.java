package ru.javawebinar.basejava.model;

import java.util.List;

public class CaseType extends Sections<List<Firms>> {
    private final List<Firms> list;

    public CaseType(List<Firms> list) {
        this.list = list;
    }


    @Override
    protected List<Firms> getType() {
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

        CaseType caseType = (CaseType) o;

        return list != null ? list.equals(caseType.list) : caseType.list == null;
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }
}
