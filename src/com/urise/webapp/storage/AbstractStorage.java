package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        checkNotExist(index, r.getUuid());
        setResume(index, r);
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        checkNotExist(index, uuid);
        return getResume(index);
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        checkNotExist(index, uuid);
        deleteResume(index);
    }

    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        checkExist(index, r.getUuid());
        saveResume(index, r);
    }

    protected void checkExist(int index, String uuid) {
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
    }

    protected void checkNotExist(int index, String uuid) {
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract Resume getResume(int index);

    protected abstract int findIndex(String uuid);

    protected abstract void setResume(int index, Resume r);

    protected abstract void deleteResume(int index);

    protected abstract void saveResume(int index, Resume r);
}
