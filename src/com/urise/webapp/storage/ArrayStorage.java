package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public boolean find(Resume r) {
        boolean found = false;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == r.getUuid()) {
                found = true;
            }
        }
        return found;
    }

    public boolean find(String uuid) {
        boolean found = false;
        for (int i = 0; i < size; i++) {
            if (uuid == storage[i].getUuid()) {
                found = true;
            }
        }
        return found;
    }

    public void update(Resume r) {
        if (this.find(r)) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid() == r.getUuid()) {
                    storage[i] = r;
                }
            }
        } else System.out.println("ERROR. Not update. Resume " + r.getUuid() + " not present");
    }

    public void save(Resume r) {
        if (!this.find(r) && size < 10000) {
            storage[size++] = r;
        } else if (this.find(r)) System.out.println("ERROR. Not save. Resume " + r.getUuid() + " present");
        else if (size >= 10000) System.out.println("ERROR. Not save. Massive overflow");
    }

    public Resume get(String uuid) {
        if (this.find(uuid)) {
            for (int i = 0; i < size; i++) {
                if (uuid == storage[i].getUuid()) return storage[i];
            }
        }
        System.out.println("ERROR. Not get. Resume " + uuid + " not present");
        return null;
    }

    public void delete(String uuid) {
        if (this.find(uuid)) {
            for (int i = 0; i < size; i++) {
                if (uuid == storage[i].getUuid()) {
                    storage[i] = storage[size - 1];
                    storage[size - 1] = null;
                    size--;
                }
            }
        } else System.out.println("ERROR. Not delete. Resume " + uuid + " not present");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    public int size() {
        return size;
    }
}
