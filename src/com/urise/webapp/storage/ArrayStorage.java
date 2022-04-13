package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int find(Resume r) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == r.getUuid()) {
                return i;
            }
        }
        return -1;
    }

    public int find(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid == storage[i].getUuid()) {
                return i;
            }
        }
        return -1;
    }

    public void update(Resume r) {
        if (find(r) != -1) {
            storage[find(r)] = r;
        } else System.out.println("ERROR. Not update. Resume " + r.getUuid() + " not present");
    }

    public void save(Resume r) {
        if (find(r) == -1 && size < storage.length) {
            storage[size++] = r;
        } else if (find(r) != -1) System.out.println("ERROR. Not save. Resume " + r.getUuid() + " present");
        else if (size >= storage.length) System.out.println("ERROR. Not save. Massive overflow");
    }

    public Resume get(String uuid) {
        if (find(uuid) != -1) {
            return storage[find(uuid)];
        } else {
            System.out.println("ERROR. Not get. Resume " + uuid + " not present");
            return null;
        }
    }

    public void delete(String uuid) {
        if (find(uuid) != -1) {
            storage[find(uuid)] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else System.out.println("ERROR. Not delete. Resume " + uuid + " not present");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = Arrays.copyOf(storage, size);
        return resumes;
    }

    public int size() {
        return size;
    }
}
