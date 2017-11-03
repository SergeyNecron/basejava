/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[5];
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

    Resume get(String uuid) { // А через boolean не легче ?
        for (int i = 0; i < count ; i++) {
            if (uuid.equals(storage[i].uuid)) return storage[i];
        } // если находит нужное резюме то возвращает его
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < count; i++) {
            if (uuid.equals(storage[i].uuid))
                storage[i] = null;
                break;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return storage;
    }

    int size() {
        return count;
    }
}
