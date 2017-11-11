package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage{
    @Override
    public void clear() {
        Arrays.fill(storage,0,size,null);
        size = 0;
    }

    @Override
    public void update(Resume r) {
        int i = getIndex(r.getUuid());
        if (i<0) System.out.println("Resume " + r.getUuid() + " not exist");
        else storage[i]= r;
    }

    @Override
    public void save(Resume r) {
        int i = getIndex(r.getUuid());
        if (i <0) {
            if (size < storage.length) {
                storage[size]=r;
                size++;
            } else System.out.println("Storage overflow");
        }
        else System.out.println("Resume " + r.getUuid() + " already exist");
//       if(size>1) Arrays.sort(storage);
    }

    @Override
    public void delete(String uuid) {
        int i = getIndex(uuid);
        if (i<0) System.out.println("Resume " + uuid + " not exist");
        else {
            size--;
            Resume[] tempMass = Arrays.copyOfRange(storage, i + 1,  size+1);//to копирует до count включительно
            System.arraycopy(tempMass,0, storage, i, size- i);//length количество копю элементов
            storage[size]=null;
        }
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage,size);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}