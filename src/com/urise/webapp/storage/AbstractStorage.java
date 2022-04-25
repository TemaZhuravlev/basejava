package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        Object index = getNotExistSearchKey(r.getUuid());
        setResume(index, r);
    }

    public Resume get(String uuid) {
        Object index = getNotExistSearchKey(uuid);
        return getResume(index);
    }

    public void delete(String uuid) {
        Object index = getNotExistSearchKey(uuid);
        deleteResume(index);
    }

    public void save(Resume r) {
        Object index = getExistSearchKey(r.getUuid());
        saveResume(index, r);
    }

    private Object getExistSearchKey(String uuid) {
        Object index = getSearchKey(uuid);
        if (isExist(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    private Object getNotExistSearchKey(String uuid) {
        Object index = getSearchKey(uuid);
        if (!isExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    protected abstract Resume getResume(Object index);

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object index);

    protected abstract void setResume(Object index, Resume r);

    protected abstract void deleteResume(Object index);

    protected abstract void saveResume(Object index, Resume r);
}
