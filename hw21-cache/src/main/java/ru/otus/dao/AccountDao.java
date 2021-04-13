package ru.otus.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.model.Account;

import java.util.Optional;

public class AccountDao extends AbstractDao<Account, String> {

    private static final Logger log = LoggerFactory.getLogger(AccountDao.class);

    public AccountDao(SessionManagerHibernate sessionManager) {
        super(sessionManager);
    }

    @Override
    public Optional<Account> findById(String id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(Account.class, id));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }
}
