package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    Resume r1 = new Resume(UUID_1);
    Resume r2 = new Resume(UUID_2);
    Resume r3 = new Resume(UUID_3);


    @Before
    public void setUp() {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    public void fillStorage() {
        for (int i = 4; i <= STORAGE_LIMIT; i++) {
            storage.save(new Resume("uuid" + i));
        }
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume resume = new Resume("uuid2");
        storage.update(resume);
        Assert.assertEquals(storage.get(r2.getUuid()), resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume = new Resume("uuid4");
        storage.update(resume);
    }

    @Test
    public void get() {
        Assert.assertEquals(r1, storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void save() {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(r1);
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() {
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
        storage.delete(UUID_2);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        Resume resume = new Resume("uuid4");
        storage.delete(resume.getUuid());
    }

    @Test
    public void getAll() {
        Resume[] allResume = storage.getAll();
        Assert.assertEquals(storage.size(), allResume.length);
    }
}