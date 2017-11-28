package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage{

    protected abstract Object getIndex(String uuid);
    protected abstract boolean isExist(Object index);


    @Override
    public int size() {
        return 0;
    }

    @Override
    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            storage[index] = r;
        }
    }

    @Override
    public void save(Resume r) {

    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public Resume get(String uuid) {
        return null;
    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }
}
