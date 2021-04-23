package ru.otus.service.auth;

import ru.otus.service.db.UserService;

public class UserAuthServiceImpl implements UserAuthService {

    private final UserService userService;

    public UserAuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return userService.findByLogin(login)
                       .map(user -> user.getPassword().equals(password))
                       .orElse(false);
    }
}
