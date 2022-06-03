package com.urise.webapp.storage;

import com.urise.webapp.storage.serialize.SerializeObjectStream;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new SerializeObjectStream()));
    }
}
