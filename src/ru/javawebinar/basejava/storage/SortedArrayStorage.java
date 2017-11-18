package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResume(Resume r, int i) {
        // http://codereview.stackexchange.com/questions/36221/binary-search-for-inserting-in-array#answer-36239
        int insrtIndx = -i - 1;
        System.arraycopy(storage, insrtIndx, storage, insrtIndx + 1, size - insrtIndx + 1);
        storage[insrtIndx] = r;
    }

    @Override
    protected void deleteResume(int i) {
        // Resume[] tempMass = Arrays.copyOfRange(storage, i + 1, size + 1);//to копирует до count включительно
        //  System.arraycopy(tempMass, 0, storage, i, size - i);//length количество копируемый элементов ----->>> упростилось в
        int lengthMoved = size - i;
        if (lengthMoved > 0)
            System.arraycopy(storage, i + 1, storage, i, lengthMoved);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}