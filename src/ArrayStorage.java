/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[1000];
    int count = 0;

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
        boolean isSdvig= false;
        for (int i = 0; i < count; i++) {
            if (uuid.equals(storage[i].uuid)) {
                storage[i] = null;
                isSdvig = true;
                count--;
            }
            if (isSdvig) storage[i]=storage[i+1];
        }

    }
    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] newStorage = new Resume[count];
        for (int i = 0; i < count; i++) {
            newStorage[i] = storage[i];
        }
        return newStorage;
    }

    int size() {
        return count;
    }
}
