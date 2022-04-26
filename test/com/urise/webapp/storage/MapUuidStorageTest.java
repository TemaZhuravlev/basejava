package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

public class MapUuidStorageTest extends AbstractStorageTest {
    public MapUuidStorageTest() {
        super(new MapUuidStorage());
    }

    @Test
    @Override
    public void getAll() {
        List<Resume> allResume = storage.getAllSorted();
        Assert.assertEquals(storage.size(), allResume.size());
        checkGet(RESUME_1, allResume.get(0).getUuid());
        checkGet(RESUME_2, allResume.get(1).getUuid());
        checkGet(RESUME_3, allResume.get(2).getUuid());
    }
}
