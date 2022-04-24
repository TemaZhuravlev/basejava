package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    protected ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return storage.indexOf(searchKey);
    }

    @Override
    protected boolean containsIndex(String uuid) {
        int index = (int) getSearchKey(uuid);
        return index >= 0;
    }

    @Override
    protected Resume getResume(Object index) {
        return storage.get((int)index);
    }

    @Override
    protected void setResume(Object index, Resume r) {
        storage.set((int)index, r);
    }

    @Override
    protected void deleteResume(Object index) {
        storage.remove((int)index);
    }

    @Override
    protected void saveResume(Object index, Resume r) {
        storage.add(r);
    }
}
