package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    public void update(Resume r) {
        SK index = getNotExistSearchKey(r.getUuid());
        setResume(index, r);
    }

    public Resume get(String uuid) {
        SK index = getNotExistSearchKey(uuid);
        return getResume(index);
    }

    public void delete(String uuid) {
        SK index = getNotExistSearchKey(uuid);
        deleteResume(index);
    }

    public void save(Resume r) {
        SK index = getExistSearchKey(r.getUuid());
        saveResume(index, r);
    }

    private SK getExistSearchKey(String uuid) {
        SK index = getSearchKey(uuid);
        if (isExist(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    private SK getNotExistSearchKey(String uuid) {
        SK index = getSearchKey(uuid);
        if (!isExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    public List<Resume> getAllSorted() {
        List<Resume> resumeList = doGetAllSorted();
        resumeList.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return resumeList;
    }

    protected abstract List<Resume> doGetAllSorted();

    protected abstract Resume getResume(SK index);

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK index);

    protected abstract void setResume(SK index, Resume r);

    protected abstract void deleteResume(SK index);

    protected abstract void saveResume(SK index, Resume r);
}
