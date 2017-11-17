package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void update(Resume r) {
        int i = getIndex(r.getUuid());
        if (i < 0) System.out.println("Resume " + r.getUuid() + " not exist");
        else storage[i] = r;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public void save(Resume r) {
        int i = getIndex(r.getUuid());
        if (i < 0) {
            if (size < storage.length) {
                saveResume(r, i);
                size++;
            } else System.out.println("Storage overflow");
        } else System.out.println("Resume " + r.getUuid() + " already exist");
    }

    @Override
    public void delete(String uuid) {
        int i = getIndex(uuid);
        if (i < 0) System.out.println("Resume " + uuid + " not exist");
        else {
            size--;
            deleteResume(i);
            storage[size] = null;
        }
    }

    protected abstract void saveResume(Resume r, int i);

    protected abstract void deleteResume(int i);

    protected abstract int getIndex(String uuid);
}