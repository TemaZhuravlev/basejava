import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        for (int to = size, i = size = 0; i < to; i++)
            storage[i] = null;
    }

    void save(Resume r) {
        storage[size++] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].toString(), uuid)) return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].toString(), uuid)) {
                for (int j = i; j < size; j++) {
                    storage[j] = storage[j + 1];
                    storage[j + 1] = null;
                }
                size -= 1;
                break;
            }
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] temp = new Resume[size];
        for (int i = 0; i < size; i++) {
            temp[i] = storage[i];
        }
        return temp;
    }

    int size() {
        return size;
    }
}
