package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
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
    protected void deleteResume(Object index) {
        storage.remove((String) index);
    }

    @Override
    protected void saveResume(Object index, Resume r) {
        storage.put((String) index, r);
    }

    @Override
    protected boolean isExist(Object index) {
        return storage.containsKey((String) index);
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    protected Resume getResume(Object index) {
        return storage.get((String) index);
    }

    @Override
    protected void setResume(Object index, Resume r) {
        storage.put((String) index, r);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (Map.Entry<String, Resume> resumeEntry : storage.entrySet()) {
            if (uuid.equals(resumeEntry.getValue().getUuid())) {
                return resumeEntry.getKey();
            }
        }
        return uuid;
    }
}
