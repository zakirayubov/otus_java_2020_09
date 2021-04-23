package ru.otus.dao;

import ru.otus.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<User, Long> {

    Optional<User> findByLogin(String login);

    List<User> findAll();
}
