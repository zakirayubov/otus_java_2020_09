package ru.otus.server;


import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.processor.TemplateProcessor;
import ru.otus.service.auth.UserAuthService;
import ru.otus.service.db.UserService;
import ru.otus.servlet.AuthorizationFilter;
import ru.otus.servlet.LoginServlet;

import java.util.Arrays;

public class SecuredWebServer extends SimpleWebServer {

    private final UserAuthService authService;

    public SecuredWebServer(int port,
                            UserAuthService authService,
                            UserService userService,
                            TemplateProcessor templateProcessor) {
        super(port, userService, templateProcessor);
        this.authService = authService;
    }

    @Override
    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }
}
