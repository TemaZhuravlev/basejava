package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String DUMMY = "dummy";

    private static final Resume r1 = new Resume(UUID_1);
    private static final Resume r2 = new Resume(UUID_2);
    private static final Resume r3 = new Resume(UUID_3);


    @Before
    public void setUp() {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    public void fillStorage() {
        storage.clear();
        for (int i = 0; i < STORAGE_LIMIT; i++) {
            storage.save(new Resume("uuid" + i));
        }
    }

    public void checkSize(int expectedSize) {
        Assert.assertEquals(expectedSize, storage.size());
    }

    public void checkGet(Resume r, String uuid) {
        Assert.assertEquals(r, storage.get(uuid));
    }

    @Test
    public void size() {
        checkSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        checkSize(0);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_2);
        storage.update(resume);
        Assert.assertTrue(resume.equals(r2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume = new Resume(DUMMY);
        storage.update(resume);
    }

    @Test
    public void get() {
        checkGet(r1, UUID_1);
        checkGet(r2, UUID_2);
        checkGet(r3, UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    @Test
    public void save() {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        checkGet(resume, "uuid4");
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(r1);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            fillStorage();
        } catch (StorageException exception) {
            Assert.fail("Storage Overflow ahead of time");
        }
        Resume resume = new Resume("uuid");
        storage.save(resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        int size = storage.size();
        storage.delete(UUID_2);
        checkSize(size-1);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        Resume resume = new Resume(DUMMY);
        storage.delete(resume.getUuid());
    }

    @Test
    public void getAll() {
        Resume[] allResume = storage.getAll();
        Assert.assertEquals(storage.size(), allResume.length);
        checkGet(r1,allResume[0].getUuid());
        checkGet(r2,allResume[1].getUuid());
        checkGet(r3,allResume[2].getUuid());
    }
}