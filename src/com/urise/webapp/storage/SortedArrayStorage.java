package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * SortedArray based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void addResume(int index, Resume r) {
        index = (index + 1) * -1;
        Resume[] resumes = Arrays.copyOf(storage, size);
        storage[index] = r;
        size++;
        for (int i = index + 1; i < size; i++) {
            storage[i] = resumes[i - 1];
        }
    }

    @Override
    protected void removeResume(int index) {
        for (int j = index; j < size - 1; j++) {
            storage[j] = storage[j + 1];
        }
        storage[size - 1] = null;
        size--;
    }
}
