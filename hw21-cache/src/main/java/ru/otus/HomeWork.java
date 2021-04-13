package ru.otus;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
import ru.otus.dao.AccountDao;
import ru.otus.dao.ClientDao;
import ru.otus.dao.Dao;
import ru.otus.flyway.MigrationsExecutorFlyway;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.model.Account;
import ru.otus.model.AddressDataSet;
import ru.otus.model.Client;
import ru.otus.model.PhoneDataSet;
import ru.otus.service.DBService;
import ru.otus.service.DbServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HomeWork {

    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    private static final Logger log = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {

// Общая часть
        Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        String dbUrl = configuration.getProperty("hibernate.connection.url");
        String dbUserName = configuration.getProperty("hibernate.connection.username");
        String dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(configuration,
                Client.class, Account.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

// Работа с клиентами
        Dao<Client, Long> clientDao = new ClientDao(sessionManager);

        HwCache<Long, Client> clientCache = new MyCache<>();
        HwListener<Long, Client> clientListener = new HwListener<Long, Client>() {
            @Override
            public void notify(Long key, Client value, String action) {
                log.info("notify() - info: action = {} for key = {}, value = {}", action, key, value);
            }
        };
        clientCache.addListener(clientListener);

        DBService<Client, Long> dbServiceClient = new DbServiceImpl<>(clientDao, clientCache);

        var clientId = dbServiceClient.save(new Client("dbServiceClient", 17));

        Optional<Client> clientOptional = dbServiceClient.getById(clientId);
        clientOptional.ifPresentOrElse(
                client -> log.info("created client, name:{}", client.getName()),
                () -> log.info("client was not created")
        );

        Client persisted = clientOptional.orElseThrow();
        persisted.setAge(25);
        dbServiceClient.save(persisted);

        clientOptional = dbServiceClient.getById(clientId);
        clientOptional.ifPresentOrElse(
                client -> log.info("updated client, name:{}", client.getName()),
                () -> log.info("client was not updated")
        );

        AddressDataSet address = new AddressDataSet("street", persisted);
        persisted.setAddress(address);

        List<PhoneDataSet> phones = List.of(new PhoneDataSet("phone1", persisted), new PhoneDataSet("phone2", persisted));
        persisted.setPhones(phones);

        dbServiceClient.save(persisted);

        clientOptional = dbServiceClient.getById(clientId);
        clientOptional.ifPresentOrElse(
                client -> log.info("updated client, address:{}", client.getAddress()),
                () -> log.info("client was not updated")
        );

        clientOptional = dbServiceClient.getById(clientId);
        clientOptional.ifPresentOrElse(
                client -> log.info("updated client, phones:{}", client.getPhones()),
                () -> log.info("client was not updated")
        );

// Работа со счетом
        Dao<Account, String> accountDao = new AccountDao(sessionManager);

        HwCache<String, Account> accountCache = new MyCache<>();
        HwListener<String, Account> accountListener = new HwListener<String, Account>() {
            @Override
            public void notify(String key, Account value, String action) {
                log.info("notify() - info: action = {} for key = {}, value = {}", action, key, value);
            }
        };
        accountCache.addListener(accountListener);

        var dbServiceAccount = new DbServiceImpl<>(accountDao, accountCache);

        String accountId = UUID.randomUUID().toString();

        dbServiceAccount.save(new Account(accountId, "dbServiceAccountType", 17.78));

        Optional<Account> accountOptional = dbServiceAccount.getById(accountId);
        accountOptional.ifPresentOrElse(
                account -> log.info("created account, type:{}", account.getType()),
                () -> log.info("account was not created")
        );

        dbServiceAccount.save(new Account(accountId, "dbServiceAccountTypeUpdated", 12.39));

        accountOptional = dbServiceAccount.getById(accountId);
        accountOptional.ifPresentOrElse(
                account -> log.info("updated account, type:{}", account.getType()),
                () -> log.info("account was not updated")
        );
    }
}
