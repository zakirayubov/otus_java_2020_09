package ru.otus.dao;

import ru.otus.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.model.Client;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ClientDao extends AbstractDao<Client, Long> {

    private static final Logger log = LoggerFactory.getLogger(ClientDao.class);

    public ClientDao(SessionManagerHibernate sessionManager) {
        super(sessionManager);
    }

    @Override
    public Optional<Client> findById(Long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Client client = currentSession.getHibernateSession().get(Client.class, id);
            if (client != null) {
                Hibernate.initialize(client.getPhones());
            }

            return Optional.ofNullable(client);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }
}
