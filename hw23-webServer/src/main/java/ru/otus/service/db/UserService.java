package ru.otus.service.db;

import ru.otus.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends DBService<User, Long> {

    Optional<User> findByLogin(String login);

    List<User> findAll();

    void delete(User user);
}
