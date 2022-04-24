package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        Object index = checkNotExist(r.getUuid());
        setResume(index, r);
    }

    public Resume get(String uuid) {
        Object index = checkNotExist(uuid);
        return getResume(index);
    }

    public void delete(String uuid) {
        Object index = checkNotExist(uuid);
        deleteResume(index);
    }

    public void save(Resume r) {
        Object index = checkExist(r.getUuid());
        saveResume(index, r);
    }

    protected Object checkExist(String uuid) {
        if (containsIndex(uuid)) {
            throw new ExistStorageException(uuid);
        }
        return getSearchKey(uuid);
    }

    protected Object checkNotExist(String uuid) {
        if (!containsIndex(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        return getSearchKey(uuid);
    }

    protected abstract Resume getResume(Object index);

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean containsIndex(String uuid);

    protected abstract void setResume(Object index, Resume r);

    protected abstract void deleteResume(Object index);

    protected abstract void saveResume(Object index, Resume r);
}
