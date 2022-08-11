package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        });
    }

    @Override
    public int size() {
        LOG.info("size");
        return sqlHelper.executeQuery("SELECT count(*) AS total FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    return rs.next() ? rs.getInt("total") : 0;
                });
    }

    @Override
    public void clear() {
        LOG.info("clear");
        sqlHelper.executeQuery("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContact(r, conn);
            insertContact(r, conn);
            deleteSection(r, conn);
            insertSection(r, conn);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.executeQuery("SELECT uuid, full_name, c.type AS c_type, c.value AS c_value, s.type AS s_type, s.value AS s_value " +
                        "FROM resume r " +
                        "LEFT JOIN contact c ON c.resume_uuid = r.uuid " +
                        "LEFT JOIN section s ON s.resume_uuid = r.uuid " +
                        "WHERE r.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(rs, r);
                        addSection(rs, r);
                    } while (rs.next());
                    return r;
                });
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContact(r, conn);
            insertSection(r, conn);
            return null;
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
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    map.put(rs.getString("uuid"), new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT type AS c_type, value AS c_value, resume_uuid " +
                    "FROM contact ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (map.get(rs.getString("resume_uuid")) != null) {
                        Resume r = map.get(rs.getString("resume_uuid"));
                        addContact(rs, r);
                    }
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT type AS s_type, value AS s_value, resume_uuid " +
                    "FROM section ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (map.get(rs.getString("resume_uuid")) != null) {
                        Resume r = map.get(rs.getString("resume_uuid"));
                        addSection(rs, r);
                    }
                }
            }
            return new ArrayList<>(map.values());
        });
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("c_value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("c_type"));
            r.addContact(type, value);
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        String sectionValue = rs.getString("s_value");
        if (sectionValue != null) {
            SectionType sectionType = SectionType.valueOf(rs.getString("s_type"));
            switch (sectionType) {
                case OBJECTIVE, PERSONAL -> {
                    r.addSection(sectionType, new TextSection(sectionValue));
                }
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    String[] elements = sectionValue.split("\n");
                    r.addSection(sectionType, new ListSection(Arrays.asList(elements)));
                }
            }
        }
    }

    private void insertContact(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSection(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            Map<SectionType, AbstractSection> sections = r.getSections();
            for (Map.Entry<SectionType, AbstractSection> section : sections.entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, section.getKey().name());
                ps.setString(3, section.getValue().toString());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContact(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void deleteSection(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }
}
