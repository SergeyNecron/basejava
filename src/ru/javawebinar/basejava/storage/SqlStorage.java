package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.GenerikHelper;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid =?", new GenerikHelper<Resume>() {
            @Override
            public Resume execute(PreparedStatement ps) throws SQLException {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                return new Resume(uuid, rs.getString("full_name"));
            }
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.execute("UPDATE resume SET full_name = ? WHERE uuid = ?", new GenerikHelper<Object>() {
            @Override
            public Object execute(PreparedStatement ps) throws SQLException {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
                return null;
            }
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.<Void>execute("INSERT INTO resume (uuid, full_name) VALUES (?,?)", new GenerikHelper<Void>() {
            @Override
            public Void execute(PreparedStatement ps) throws SQLException {
                String uuid = r.getUuid();
                ps.setString(1, uuid);
                ps.setString(2, r.getFullName());
                try {
                    ps.execute();
                } catch (Exception e) {
                    throw new ExistStorageException(uuid);
                }
                return null;
            }
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", new GenerikHelper<Object>() {
            @Override
            public Object execute(PreparedStatement ps) throws SQLException {
                ps.setString(1, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
                return null;
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume r ORDER BY full_name,uuid", new GenerikHelper<List<Resume>>() {
            @Override
            public List<Resume> execute(PreparedStatement ps) throws SQLException {
                ResultSet rs = ps.executeQuery();
                List<Resume> resumes = new ArrayList<>();
                while (rs.next()) {
                    resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
                return resumes;
            }
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", new GenerikHelper<Integer>() {
            @Override
            public Integer execute(PreparedStatement st) throws SQLException {
                ResultSet rs = st.executeQuery();
                return rs.next() ? rs.getInt(1) : 0;
            }
        });
    }
}