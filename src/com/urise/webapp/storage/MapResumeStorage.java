package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void deleteResume(Object resume) {
        storage.remove(((Resume)resume).getUuid());
    }

    @Override
    protected void saveResume(Object resume, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected List<Resume> doGetAllSorted() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected Resume getResume(Object resume) {
        return (Resume)resume;
    }

    @Override
    protected void setResume(Object resume, Resume r) {
        storage.put(((Resume)resume).getUuid(), r);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.get(uuid);
    }
}
