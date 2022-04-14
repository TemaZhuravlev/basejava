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

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index != -1) {
            storage[index] = r;
        } else System.out.println("ERROR. Not update. Resume " + r.getUuid() + " not present");
    }

    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (size < storage.length){
            if (index == -1){
                storage[size++] = r;
            }
            else System.out.println("ERROR. Not save. Resume " + r.getUuid() + " present");
        }
        else System.out.println("ERROR. Not save. Massive overflow");
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.println("ERROR. Not get. Resume " + uuid + " not present");
        return null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else System.out.println("ERROR. Not delete. Resume " + uuid + " not present");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid == storage[i].getUuid()) {
                return i;
            }
        }
        return -1;
    }
}
