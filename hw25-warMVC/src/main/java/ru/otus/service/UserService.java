package ru.otus.service;

import ru.otus.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends DBService<User, Long> {

    Optional<User> findByLogin(String login);

    User findRandom();

    List<User> findAll();

    void delete(User user);
}
