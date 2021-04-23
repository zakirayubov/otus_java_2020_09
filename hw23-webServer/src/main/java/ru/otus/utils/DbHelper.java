package ru.otus.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.flyway.MigrationsExecutorFlyway;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.model.User;
import ru.otus.service.db.UserService;

import java.util.List;

public final class DbHelper {

    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    private static final Logger log = LoggerFactory.getLogger(DbHelper.class);

    private DbHelper() {
    }

    public static SessionManagerHibernate buildSessionManager() {
        Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        String dbUrl = configuration.getProperty("hibernate.connection.url");
        String dbUserName = configuration.getProperty("hibernate.connection.username");
        String dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(configuration, User.class);
        return new SessionManagerHibernate(sessionFactory);
    }

    public static void populateDb(UserService userService) {
        log.info("populateDb() - start");

        if (userService.findAll().isEmpty()) {
            List<User> users = List.of(
                    new User(1L, "user1", "login1", "pass1"),
                    new User(2L, "user2", "login2", "pass2"),
                    new User(3L, "user3", "login3", "pass3"),
                    new User(4L, "user4", "login4", "pass4"),
                    new User(5L, "user5", "login5", "pass5")
            );
            users.forEach(userService::save);
        }

        log.info("populateDb() - end");
    }
}
