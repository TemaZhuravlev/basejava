package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
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
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = new ArrayList<>(storage.values());
        resumeList.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return resumeList;
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
