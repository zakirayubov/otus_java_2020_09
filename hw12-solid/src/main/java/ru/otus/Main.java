package ru.otus;

import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        Map<Banknote, Integer> container = new TreeMap<>();
        container.put(Banknote.HUNDRED, 5);
        container.put(Banknote.FIVE_HUNDRED, 5);
        container.put(Banknote.THOUSAND, 5);
        container.put(Banknote.TWO_THOUSAND, 5);
        container.put(Banknote.FIVE_THOUSAND, 5);

        AtmImpl atm = new AtmImpl(container);

        Map<Banknote, Integer> banknoteIntegerMap = atm.get(700);

        String s = "";
    }
}
