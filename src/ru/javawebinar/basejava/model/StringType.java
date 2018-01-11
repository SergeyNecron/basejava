package ru.javawebinar.basejava.model;

public class StringType extends Sections<String> {
    private final String s;

    public StringType(String s) {
        this.s = s;
    }

    @Override
    public String getType() {
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringType that = (StringType) o;

        return s != null ? s.equals(that.s) : that.s == null;
    }

    @Override
    public int hashCode() {
        return s != null ? s.hashCode() : 0;
    }


    @Override
    public String toString() {
        return s;
    }
}
