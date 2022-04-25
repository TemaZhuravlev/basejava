package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    @Override
    public void getAll() {
        Resume[] allResume = storage.getAll();
        Arrays.sort(allResume);
        Assert.assertEquals(storage.size(), allResume.length);
        checkGet(RESUME_1, allResume[0].getUuid());
        checkGet(RESUME_2, allResume[1].getUuid());
        checkGet(RESUME_3, allResume[2].getUuid());
    }
}
