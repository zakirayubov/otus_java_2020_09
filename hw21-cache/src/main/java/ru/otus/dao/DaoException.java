package ru.otus.dao;

public class DaoException extends RuntimeException {

    public DaoException(Exception e) {
        super(e);
    }
}
