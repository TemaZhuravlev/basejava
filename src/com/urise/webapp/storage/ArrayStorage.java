package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (size < STORAGE_LIMIT) {
            if (index == -1) {
                storage[size++] = r;
            } else System.out.println("ERROR. Not save. Resume " + r.getUuid() + " present");
        } else System.out.println("ERROR. Not save. Array overflow");
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else System.out.println("ERROR. Not delete. Resume " + uuid + " not present");
    }

    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
