package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * SortedArray based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (size < STORAGE_LIMIT) {
            if (index < 0) {
                index = (index + 1) * -1;
                Resume[] resumes = Arrays.copyOf(storage, size);
                storage[index] = r;
                size++;
                for (int i = index + 1; i < size; i++) {
                    storage[i] = resumes[i - 1];
                }
            } else System.out.println("ERROR. Not save. Resume " + r.getUuid() + " present");
        } else System.out.println("ERROR. Not save. Array overflow");
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            for (int j = index; j < size - 1; j++) {
                storage[j] = storage[j + 1];
            }
            storage[size - 1] = null;
            size--;
        } else System.out.println("ERROR. Not delete. Resume " + uuid + " not present");
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
