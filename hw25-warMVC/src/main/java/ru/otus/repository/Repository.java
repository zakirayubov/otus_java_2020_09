package ru.otus.repository;

import ru.otus.domain.Unique;
import ru.otus.sessionmanager.SessionManager;

import java.util.Optional;

public interface Repository<T extends Unique<K>, K> {

    Optional<T> findById(K id);

    K insert(T entity);

    void update(T entity);

    K insertOrUpdate(T entity);

    void delete(T entity);

    SessionManager getSessionManager();
}
