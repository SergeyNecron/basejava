package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String sql) {
        execute(sql, new GenerikHelper<Boolean>() {
            @Override
            public Boolean execute(PreparedStatement preparedStatement) throws SQLException {
                return preparedStatement.execute();
            }
        });
    }

    public <T> T execute(String sql, GenerikHelper<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw (e.getSQLState().equals("23505") ? new ExistStorageException(null) : new StorageException(e));
            //                   unique_violation  https://www.postgresql.org/docs/8.2/static/errcodes-appendix.html
        }
    }

}