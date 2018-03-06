package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> {
            try {
                DriverManager.registerDriver(new org.postgresql.Driver());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        });
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM resume WHERE uuid =? "
            )) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM contact WHERE resume_uuid =? "
            )) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, r);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM section WHERE resume_uuid =? "
            )) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, r);
                }
            }
            return r;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() != 1) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(conn, r);
            insertContact(conn, r);
            deleteSections(conn, r);
            insertSections(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContact(conn, r);
            insertSections(conn, r);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("   SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    Resume resume = map.get(uuid);
                    if (resume == null) {
                        resume = new Resume(uuid, rs.getString("full_name"));
                        map.put(uuid, resume);
                    }
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("   SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    Resume resume = map.get(uuid);
                    addContact(rs, resume);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("   SELECT * FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    Resume resume = map.get(uuid);
                    addSection(rs, resume);
                }
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", st -> {
            ResultSet rs = st.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertContact(Connection conn, Resume r) throws SQLException {
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

    private void insertSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, content) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                SectionType type = e.getKey();
                ps.setString(2, type.name());
                ps.setString(3, sectionWriter(type, e.getValue()));

                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private String sectionWriter(SectionType type, Section value) {
        StringBuilder rezult = new StringBuilder().append("{\"CLASSNAME\":\"ru.javawebinar.basejava.model.");
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                rezult.append("TextSection");
                rezult.append("\",\"INSTANCE\":{\"content\":\"");
                rezult.append(value);
                rezult.append("\"}}");
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                rezult.append("ListSection");
                rezult.append("\",\"INSTANCE\":{\"items\":[\"");
                List listSection = ((ListSection) value).getItems();
                rezult.append(listSection.get(0));
                for (int i = 1; i < listSection.size(); i++) {
                    rezult.append("\", \"");
                    rezult.append(listSection.get(i));
                }
                rezult.append("\"]}}");
                break;
            case EXPERIENCE:
            case EDUCATION:
            default:
                throw new IllegalStateException();
        }
        return rezult.toString();
    }

    private void deleteContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE  FROM contact WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void deleteSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE  FROM section WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            r.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSection(ResultSet rs, Resume resume) throws SQLException {
        String text = rs.getString("content");

        SectionType type = SectionType.valueOf(rs.getString("type"));
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                text = text.substring(80, text.length() - 3);
                resume.addSection(type, new TextSection(text));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                text = text.substring(79, text.length() - 4);
                String[] strings = text.split("\", \"");
                for (int i = 0; i < strings.length; i++) {
                    strings[i] = strings[i].replaceAll("\\\\", "");
                }
                resume.addSection(type, new ListSection(strings));
                break;
            case EXPERIENCE:
            case EDUCATION:
            default:
                throw new IllegalStateException();
        }
    }
}