package ru.otus.dao;

import ru.otus.model.Unique;
import ru.otus.sessionmanager.SessionManager;

import java.util.Optional;

public interface Dao<T extends Unique<K>, K> {

    Optional<T> findById(K id);

    K insert(T entity);

    void update(T entity);

    K insertOrUpdate(T entity);

    void delete(T entity);

    SessionManager getSessionManager();
}
