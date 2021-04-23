package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dao.UserDaoImpl;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.processor.TemplateProcessor;
import ru.otus.processor.TemplateProcessorImpl;
import ru.otus.server.SecuredWebServer;
import ru.otus.server.WebServer;
import ru.otus.service.auth.UserAuthService;
import ru.otus.service.auth.UserAuthServiceImpl;
import ru.otus.service.db.UserService;
import ru.otus.service.db.UserServiceImpl;
import ru.otus.utils.DbHelper;

import java.util.Arrays;

public final class Application {

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        log.info("main() - start: args = {}", Arrays.toString(args));

        SessionManagerHibernate sessionManager = DbHelper.buildSessionManager();
        UserService userService = new UserServiceImpl(new UserDaoImpl(sessionManager));
        DbHelper.populateDb(userService);
        log.info("main() - info: db prepared");

        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(userService);
        WebServer usersWebServer =
                new SecuredWebServer(WEB_SERVER_PORT, authService, userService, templateProcessor);
        log.info("main() - info: webserver prepared");

        usersWebServer.start();
        log.info("main() - info: webserver started");

        usersWebServer.join();

        log.info("main() - end");
    }
}
