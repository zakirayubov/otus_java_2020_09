package ru.otus.service.db;

public class DbServiceException extends RuntimeException {

    public DbServiceException(Exception e) {
        super(e);
    }
}
