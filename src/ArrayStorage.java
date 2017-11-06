/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
   private Resume[] storage = new Resume[1000];
    private int count = 0;

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            storage[i] = null;
            count = 0;
        }
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
                Resume[] tempMass = new Resume[count-i];
                //System.arraycopy(storage, 0, tempMass, 0, storage.length);
                System.arraycopy(storage, i + 1, tempMass, 0, count - i);
                System.arraycopy(tempMass,0, storage, i, count- i);
                storage[count]=null;
                break;
            }
        }

    }
    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] newStorage = new Resume[count];
        System.arraycopy(storage, 0, newStorage, 0, count);
        return newStorage;
    }

    int size() {
        return count;
    }
}
