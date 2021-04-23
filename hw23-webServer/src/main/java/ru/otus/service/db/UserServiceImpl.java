package ru.otus.service.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dao.UserDao;
import ru.otus.model.User;
import ru.otus.sessionmanager.SessionManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDao dao;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public Long save(User user) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                User entity = user;

                Long userId = user.getId();
                if (userId != null && userId > 0) {
                    entity = dao.findById(userId).orElse(user);
                    entity.setName(user.getName());
                    entity.setLogin(user.getLogin());
                    entity.setPassword(user.getPassword());
                }

                Long id = dao.insertOrUpdate(entity);

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
    public Optional<User> getById(Long id) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> entity = dao.findById(id);

                log.info("entity: {}", entity.orElse(null));
                return entity;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> entity = dao.findByLogin(login);

                log.info("entity: {}", entity.orElse(null));
                return entity;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                List<User> users = dao.findAll();
                log.info("users found: {}", users.size());
                return users;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Collections.emptyList();
        }
    }

    @Override
    public void delete(User user) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                dao.delete(user);
                log.info("user deleted");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
        }
    }
}
