package ru.otus.service.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dao.Dao;
import ru.otus.model.Unique;
import ru.otus.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceImpl<T extends Unique<K>, K> implements DBService<T, K> {

    private final Dao<T, K> dao;

    private static final Logger log = LoggerFactory.getLogger(DbServiceImpl.class);

    public DbServiceImpl(Dao<T, K> dao) {
        this.dao = dao;
    }

    @Override
    public K save(T entity) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                K id = dao.insertOrUpdate(entity);
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
    public Optional<T> getById(K id) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<T> entity = dao.findById(id);

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
