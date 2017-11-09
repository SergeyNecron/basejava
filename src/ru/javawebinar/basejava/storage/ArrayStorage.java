package ru.javawebinar.basejava.storage;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
   private Resume[] storage = new Resume[1000];
   private int size = 0;

    private int index (String uuid){
        for (int i = 0; i < size ; i++)
            if(uuid.equals(storage[i].getUuid()))
                return i; //найдено
        return -1;
    }

    public void clear() {
      Arrays.fill(storage,0,size,null);// for (int i = 0; i < size; i++) storage[i] = null;
      size = 0;
    }

    public void update(Resume r) {
        // TODO check if resume present
        int i = index(r.getUuid());
        if (i<0) System.out.println("ERROR : resume not present");
        else storage[i]= r;
    }

  public void save(Resume r) {
        // TODO check if resume not present
       if (index(r.getUuid()) <0) {
            if (size < storage.length) {
                storage[size] = r;
                size++;
            } else System.out.println("ERROR : resume limit is exceeded");
        }
        else System.out.println("ERROR : resume present ");
    }

   public Resume get(String uuid) {
        // TODO check if resume present
        int i = index(uuid);
        if (i<0) {
            System.out.println("ERROR : resume not present");
            return null;
            }
        else
            return storage[i];
    }

    public void delete(String uuid) {
        // TODO check if resume present
        int i = index(uuid);
        if (i<0) System.out.println("ERROR 3");
        else {
            size--;
            storage[i] = storage[size];
            storage[size]=null;
            }
        }
    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage,size);
    }

    public int size() {
        return size;
    }

}
