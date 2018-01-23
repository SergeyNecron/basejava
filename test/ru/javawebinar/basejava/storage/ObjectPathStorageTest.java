package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new AbstractPathStorage(STORAGE_DIR.getAbsolutePath()) {
            @Override
            protected void doWrite(Resume r, OutputStream os) throws IOException {
            }

            @Override
            protected Resume doRead(InputStream is) throws IOException {
                return null;
            }
        });
    }
}
