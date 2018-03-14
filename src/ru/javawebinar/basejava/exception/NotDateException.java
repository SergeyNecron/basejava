package ru.javawebinar.basejava.exception;

public class NotDateException extends StorageException {
    public NotDateException(String message) {
        super("Invalid data format " + message);
    }
}
