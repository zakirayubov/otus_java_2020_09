package ru.otus.service.auth;

public interface UserAuthService {

    boolean authenticate(String login, String password);
}
