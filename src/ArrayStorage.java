import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
   private Resume[] storage = new Resume[1000];
    private int count = 0;

    void clear() {
      /*  for (int i = 0; i < count; i++) {
            storage[i] = null;
        }*/
      Arrays.fill(storage,null);
        count = 0;
    }

    void save(Resume r) {
        if (count < storage.length) {
            storage[count] = r;
            count++;
        }
        else System.out.println("Количество резюме превышено");
    }

    Resume get(String uuid) {
        for (int i = 0; i < count ; i++) {
            if (uuid.equals(storage[i].uuid)) return storage[i];
        } // если находит нужное резюме то возвращает его
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < count; i++) {
            if (uuid.equals(storage[i].uuid)) {
                count--;
                Resume[] tempArr = Arrays.copyOfRange(storage, i + 1,  count+1);//to копирует до count включительно
                System.arraycopy(tempArr,0, storage, i, count- i);//length количество копю элементов
                storage[count]=null;
                break;
            }
        }

    }
    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage,count);
    }

    int size() {
        return count;
    }
}
