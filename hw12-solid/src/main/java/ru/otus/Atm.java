package ru.otus;

import java.util.Map;

public interface Atm {
    void put(Map<Banknote, Integer> banknotes);

    Map<Banknote, Integer> get(int amount);

    int sum();
}
