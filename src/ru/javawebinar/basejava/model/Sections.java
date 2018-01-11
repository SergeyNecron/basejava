package ru.javawebinar.basejava.model;

public abstract class Sections<TP> {
    protected abstract TP getType();

    public abstract String toString();

    public abstract int hashCode();

    public abstract boolean equals(Object obj);
}
