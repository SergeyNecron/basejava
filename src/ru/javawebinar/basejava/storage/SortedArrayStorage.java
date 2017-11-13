package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResume(Resume r) {
        storage[size] = r;
    }

    @Override
    protected void deleteResume(String uuid, int i) {
        Resume[] tempMass = Arrays.copyOfRange(storage, i + 1, size + 1);//to копирует до count включительно
        System.arraycopy(tempMass, 0, storage, i, size - i);//length количество копю элементов
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}