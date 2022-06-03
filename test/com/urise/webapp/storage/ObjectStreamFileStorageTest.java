package com.urise.webapp.storage;

import com.urise.webapp.serialize.SerializeObjectStream;

public class ObjectStreamFileStorageTest extends AbstractStorageTest{
    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new SerializeObjectStream()));
    }
}
