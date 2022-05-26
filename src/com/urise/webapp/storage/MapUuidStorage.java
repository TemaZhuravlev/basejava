package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
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
    protected void deleteResume(String index) {
        storage.remove(index);
    }

    @Override
    protected void saveResume(String index, Resume r) {
        storage.put(index, r);
    }

    @Override
    protected boolean isExist(String index) {
        return storage.containsKey(index);
    }

    @Override
    protected List<Resume> doGetAllSorted() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected Resume getResume(String index) {
        return storage.get(index);
    }

    @Override
    protected void updateResume(String index, Resume r) {
        storage.put(index, r);
    }

    @Override
    protected String getSearchKey(String uuid) {
        for (Map.Entry<String, Resume> resumeEntry : storage.entrySet()) {
            if (uuid.equals(resumeEntry.getValue().getUuid())) {
                return resumeEntry.getKey();
            }
        }
        return uuid;
    }
}
