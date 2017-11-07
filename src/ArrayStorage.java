import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
   private Resume[] storage = new Resume[1000];
   private int size = 0;

    public void clear() {
      /*  for (int i = 0; i < size; i++) {
            storage[i] = null;
        }*/
      Arrays.fill(storage,0,size,null);
        size = 0;
    }
    private boolean isExist (Resume r){
        for (int i = 0; i < size ; i++) {
            if(r.equals(storage[i])) return true; //найдено
        }
        return false;
    }
    private boolean isExist (String uuid) {
        for (int i = 0; i < size ; i++) {
            if(uuid.equals(storage[i].uuid)) return true; //найдено
        }
        return false;
    }
    public void update(Resume r) {
        // TODO check if resume present
        if (!isExist(r)) System.out.println("ERROR 1");
        else
        for (int i = 0; i < size ; i++) {
            if (r.equals(storage[i])) storage[i]= r;
        }
    }

    public void save(Resume r) {
        // TODO check if resume not present
        if (isExist(r)) System.out.println("ERROR 2");
        else
        if (size < storage.length) {
            storage[size] = r;
            size++;
        }
        else System.out.println("Количество резюме превышено");
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size ; i++) {
            if (uuid.equals(storage[i].uuid)) return storage[i];
        } // если находит нужное резюме то возвращает его
        return null;
    }

    public void delete(String uuid) {
        // TODO check if resume present
        if (!isExist(uuid)) System.out.println("ERROR 3");
        else
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                size--;
                /** реализация сдвига, когда нужна последовательность элементов
                * Resume[] tempArr = Arrays.copyOfRange(storage, i + 1,  size+1);//to копирует до size включительно
                * System.arraycopy(tempArr,0, storage, i, size- i);//length количество копю элементов
                storage[size]=null;
                 */
                storage[i] = storage[size];
                storage[size]=null;
                break;// удаляемый элемент нашли, прекращаем поиск
            }
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
