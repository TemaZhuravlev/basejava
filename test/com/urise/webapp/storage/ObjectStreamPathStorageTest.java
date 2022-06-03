package com.urise.webapp.storage;

import com.urise.webapp.serialize.SerializeObjectStream;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new SerializeObjectStream()));
    }
}
