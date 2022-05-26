package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public void update(Resume r) {
        LOG.info("Update " + r);
        SK index = getNotExistSearchKey(r.getUuid());
        updateResume(index, r);
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK index = getNotExistSearchKey(uuid);
        return getResume(index);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK index = getNotExistSearchKey(uuid);
        deleteResume(index);
    }

    public void save(Resume r) {
        LOG.info("Save " + r);
        SK index = getExistSearchKey(r.getUuid());
        saveResume(index, r);
    }

    private SK getExistSearchKey(String uuid) {
        SK index = getSearchKey(uuid);
        if (isExist(index)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    private SK getNotExistSearchKey(String uuid) {
        SK index = getSearchKey(uuid);
        if (!isExist(index)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> resumeList = doGetAllSorted();
        resumeList.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return resumeList;
    }

    protected abstract List<Resume> doGetAllSorted();

    protected abstract Resume getResume(SK index);

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK index);

    protected abstract void updateResume(SK index, Resume r);

    protected abstract void deleteResume(SK index);

    protected abstract void saveResume(SK index, Resume r);
}
