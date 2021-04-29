package ru.otus.flyway;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MigrationsExecutorFlyway {

    private final Flyway flyway;

    private static final Logger log = LoggerFactory.getLogger(MigrationsExecutorFlyway.class);

    public MigrationsExecutorFlyway(String dbUrl, String dbUserName, String dbPassword) {
        flyway = Flyway.configure()
                         .dataSource(dbUrl, dbUserName, dbPassword)
                         .locations("classpath:/db/migration")
                         .load();
    }

    public void executeMigrations() {
        log.info("db migration started...");
        flyway.migrate();
        log.info("db migration finished.");
    }
}
