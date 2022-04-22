package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
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
}