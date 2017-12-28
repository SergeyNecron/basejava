package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.List;

public interface Storage {

    int size();

    void clear();

    void update(Resume r);

    void save(Resume r);

    void delete(String uuid);

    Resume get(String uuid);

    List<Resume> getAllSorted();
}