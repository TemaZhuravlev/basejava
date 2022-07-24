package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.util.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    public final SqlHelper sqlHelper;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public int size() {
        LOG.info("size");
        return sqlHelper.executeQuery("SELECT count(*) AS total FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException("Not value count");
                    }
                    return rs.getInt("total");
                });
    }

    @Override
    public void clear() {
        LOG.info("clear");
        sqlHelper.executeQuery("DELETE FROM resume", ps -> ps.execute());
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        sqlHelper.executeQuery("UPDATE resume SET uuid=?, full_name=? WHERE uuid=?",
                ps -> {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, r.getFullName());
                    ps.setString(3, r.getUuid());
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(r.getUuid());
                    }
                    return null;
                });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.executeQuery("SELECT * FROM resume WHERE uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    return r;
                });
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.executeQuery("INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                ps -> {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, r.getFullName());
                    return ps.execute();
                });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.executeQuery("DELETE FROM resume WHERE uuid=?",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        return sqlHelper.executeQuery("SELECT * FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    List<Resume> list = new ArrayList<>();
                    while (rs.next()) {
                        list.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
                    }
                    return list;
                });
    }
}
