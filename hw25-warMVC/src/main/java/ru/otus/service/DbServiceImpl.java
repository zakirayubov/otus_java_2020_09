package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.domain.Unique;
import ru.otus.repository.Repository;
import ru.otus.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceImpl<T extends Unique<K>, K> implements DBService<T, K> {

    private final Repository<T, K> repository;

    private static final Logger log = LoggerFactory.getLogger(DbServiceImpl.class);

    public DbServiceImpl(Repository<T, K> repository) {
        this.repository = repository;
    }

    @Override
    public K save(T entity) {
        try (SessionManager sessionManager = repository.getSessionManager()) {
            sessionManager.beginSession();
            try {
                K id = repository.insertOrUpdate(entity);
                sessionManager.commitSession();

                log.info("created entity: {}", id);
                return id;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<T> findById(K id) {
        try (SessionManager sessionManager = repository.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<T> entity = repository.findById(id);

                log.info("entity: {}", entity.orElse(null));
                return entity;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
