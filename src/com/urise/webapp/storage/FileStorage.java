package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.serialize.SerializeStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private SerializeStrategy serializeStrategy;

    protected FileStorage(File directory, SerializeStrategy serializeStrategy) {
        this.serializeStrategy = serializeStrategy;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] list = directory.listFiles();
        if (list != null) {
            for (File file : list) {
                deleteResume(file);
            }
        } else {
            throw new StorageException("Directory is null", directory.getName());
        }
    }

    @Override
    public int size() {
        File[] list = directory.listFiles();
        if (list == null) {
            throw new StorageException("Directory is null", directory.getName());
        } else {
            return list.length;
        }
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return serializeStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void updateResume(File file, Resume r) {
        try {
            serializeStrategy.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void deleteResume(File file) {
        if (!file.delete()) {
            throw new StorageException("File not delete", file.getName());
        }
    }

    @Override
    protected void saveResume(File file, Resume r) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        updateResume(file, r);
    }

    @Override
    protected List<Resume> doGetAllSorted() {
        File[] list = directory.listFiles();
        if (list != null) {
            List<Resume> resumeList = new ArrayList<>();
            for (File file : list) {
                resumeList.add(getResume(file));
            }
            return resumeList;
        } else {
            throw new StorageException("Directory is null", directory.getName());
        }
    }
}
