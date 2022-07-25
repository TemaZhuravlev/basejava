package com.urise.webapp.util;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T executeQuery(String query, SqlProcessor<T> sqlProcessor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return sqlProcessor.execute(ps);
        } catch (SQLException e) {
            if(e.getSQLState().equals("23505")) {
                throw new ExistStorageException("uuid exist");
            }
            throw new StorageException(e);
        }
    }


    @FunctionalInterface
    public interface SqlProcessor<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }
}
