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
    protected void saveResume(int index, Resume r) {
        index = (index + 1) * (-1);
        System.arraycopy(storage, index, storage, (index + 1), (size - index));
        storage[index] = r;
    }

    @Override
    protected void removeResume(int index) {
        System.arraycopy(storage, (index + 1), storage, index, (size - (index + 1)));
        storage[size - 1] = null;
    }
}
