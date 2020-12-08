package ru.otus;

import java.util.Collections;

public class Test {
    public static void main(String[] args) {
        DIYarrayList<Integer> addList = new DIYarrayList<>();
        DIYarrayList<Integer> getList =  new DIYarrayList<>();

        for (int i = 1; i <= 20; i++) {
            getList.add(i);
        }

        for (int i = 25; i >= 0; i--) {
            addList.add(i);
        }

        Collections.copy(addList, getList);
        boolean b = Collections.addAll(addList, 77, 88, 99);
        Collections.sort(addList);

    }
}
