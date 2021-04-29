package ru.otus.repository;

import ru.otus.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    Optional<User> findByLogin(String login);

    List<User> findAll();
}
