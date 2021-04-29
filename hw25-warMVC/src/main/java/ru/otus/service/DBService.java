package ru.otus.service;

import ru.otus.domain.Unique;

import java.util.Optional;

/**
 * Сервис работы с БД.
 *
 * @param <T> entity
 * @param <K> primary key type
 */
public interface DBService<T extends Unique<K>, K> {

    K save(T entity);

    Optional<T> findById(K id);
}
