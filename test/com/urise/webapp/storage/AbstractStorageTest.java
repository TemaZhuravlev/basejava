package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractStorageTest {
    protected Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String DUMMY = "dummy";

    private static final String FULLNAME_1 = "Ivanov Ivan";
    private static final String FULLNAME_2 = "Petrov Petr";
    private static final String FULLNAME_3 = "Petrov Petr";

    protected static final Resume RESUME_1 = new Resume(UUID_1, FULLNAME_1);
    protected static final Resume RESUME_2 = new Resume(UUID_2, FULLNAME_2);
    protected static final Resume RESUME_3 = new Resume(UUID_3, FULLNAME_3);

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    protected void fillStorage() {
        storage.clear();
        for (int i = 0; i < STORAGE_LIMIT; i++) {
            storage.save(new Resume("uuid" + i));
        }
    }

    private void checkSize(int expectedSize) {
        Assert.assertEquals(expectedSize, storage.size());
    }

    protected void checkGet(Resume r, String uuid) {
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
        Assert.assertSame(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume = new Resume(DUMMY);
        storage.update(resume);
    }

    @Test
    public void get() {
        checkGet(RESUME_1, UUID_1);
        checkGet(RESUME_2, UUID_2);
        checkGet(RESUME_3, UUID_3);
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
        checkSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }


    @Test(expected = NotExistStorageException.class)
    public void delete() {
        int size = storage.size();
        storage.delete(UUID_2);
        checkSize(size - 1);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        Resume resume = new Resume(DUMMY);
        storage.delete(resume.getUuid());
    }

    @Test
    public void getAll() {
        List<Resume> allResume = storage.getAllSorted();
        Assert.assertEquals(storage.size(), allResume.size());
        checkGet(RESUME_1, allResume.get(0).getUuid());
        checkGet(RESUME_2, allResume.get(1).getUuid());
        checkGet(RESUME_3, allResume.get(2).getUuid());
    }
}