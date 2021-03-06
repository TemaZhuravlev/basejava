package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.*;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void deleteResume(Integer index) {
        removeResume(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void saveResume(Integer index, Resume r) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            addResume(index, r);
            size++;
        }
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected List<Resume> doGetAllSorted() {
        List<Resume> resumeList = new ArrayList<>();
        Collections.addAll(resumeList, Arrays.copyOfRange(storage, 0, size));
        return resumeList;
    }

    @Override
    protected Resume getResume(Integer index) {
        return storage[index];
    }

    @Override
    protected void updateResume(Integer index, Resume r) {
        storage[index] = r;
    }

    protected abstract void addResume(int index, Resume r);

    protected abstract void removeResume(int index);
}
