package ru.javawebinar.basejava.storage;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void clear() {
      Arrays.fill(storage,0,size,null);// for (int i = 0; i < size; i++) storage[i] = null;
      size = 0;
    }

    public void update(Resume r) {
        int i = getIndex(r.getUuid());
        if (i<0) System.out.println("Resume " + r.getUuid() + " not exist");
        else storage[i]= r;
    }

  public void save(Resume r) {
       if (getIndex(r.getUuid()) <0) {
            if (size < storage.length) {
                storage[size] = r;
                size++;
            } else System.out.println("Storage overflow");
        }
        else System.out.println("Resume " + r.getUuid() + " already exist");
    }



    public void delete(String uuid) {

        int i = getIndex(uuid);
        if (i<0) System.out.println("Resume " + uuid + " not exist");
        else {
            size--;
            storage[i] = storage[size];
            storage[size]=null;
            }
        }

    protected int getIndex (String uuid){
        for (int i = 0; i < size ; i++)
            if(uuid.equals(storage[i].getUuid()))
                return i; //найдено
        return -1;
    }
    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage,size);
    }



}
