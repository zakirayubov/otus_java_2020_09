package ru.otus.dao;

import org.hibernate.Session;
import ru.otus.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.model.Unique;
import ru.otus.sessionmanager.SessionManager;

public abstract class AbstractDao<T extends Unique<K>, K> implements Dao<T, K> {

    protected final SessionManagerHibernate sessionManager;

    protected AbstractDao(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public K insert(T entity) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.persist(entity);
            hibernateSession.flush();
            return entity.getId();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(T entity) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.merge(entity);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public K insertOrUpdate(T entity) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (entity.getId() != null) {
                hibernateSession.merge(entity);
            } else {
                hibernateSession.persist(entity);
                hibernateSession.flush();
            }
            return entity.getId();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
