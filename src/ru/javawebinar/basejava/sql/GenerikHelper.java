package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class GenerikHelper<T> {
    public abstract T execute(PreparedStatement st) throws SQLException;
}