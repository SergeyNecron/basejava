package ru.javawebinar.basejava.model;

public abstract class Sections<T> {
    protected abstract T getContent();

    public abstract String toString();

    public abstract int hashCode();

    public abstract boolean equals(Object obj);
}
