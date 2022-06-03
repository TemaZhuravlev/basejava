package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.serialize.SerializeStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private SerializeStrategy serializeStrategy;

    protected PathStorage(String dir, SerializeStrategy serializeStrategy) {
        this.serializeStrategy = serializeStrategy;
        this.directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Path size error", directory.getFileName().toString());
        }
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return serializeStrategy.doRead(Files.newInputStream(path));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void updateResume(Path path, Resume r) {
        try {
            serializeStrategy.doWrite(r, Files.newOutputStream(path));
        } catch (IOException e) {
            throw new StorageException("Path write error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void deleteResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File delete error", path.getFileName().toString());
        }
        if (Files.exists(path)) {
            throw new StorageException("File not delete", path.getFileName().toString());
        }
    }

    @Override
    protected void saveResume(Path path, Resume r) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
        updateResume(path, r);
    }

    @Override
    protected List<Resume> doGetAllSorted() {
        List<Resume> resumeList = new ArrayList<>();
        try {
            Files.list(directory).forEach(element -> resumeList.add(getResume(element)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resumeList;
    }
}
