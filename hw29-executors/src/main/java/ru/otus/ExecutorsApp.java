package ru.otus;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorsApp {

    private static final AtomicBoolean orderFlag = new AtomicBoolean(false);
    private static final AtomicBoolean directionFlag = new AtomicBoolean(true);
    private static final AtomicInteger counter = new AtomicInteger(1);

    public static void main(String[] args) {
        new Thread(ExecutorsApp::printNumbersOne, "Поток 1").start();
        new Thread(ExecutorsApp::printNumbersTwo, "Поток 2").start();
    }

    private static void printNumbersOne() {
        while (counter.get() > 0) {
            while (orderFlag.get()) { }
            printNumber();
            orderFlag.set(true);
        }
    }

    private static void printNumbersTwo() {
        while (counter.get() > 1) {
            while (!orderFlag.get()) { }
            printNumber();
            orderFlag.set(false);
        }
    }

    private static void printNumber() {
        int value = directionFlag.get()
                ? counter.getAndIncrement()
                : counter.getAndDecrement();
        System.out.printf("%s: %d%n", Thread.currentThread().getName(), value);
        if (value > 8) {
            directionFlag.set(false);
        }
    }
}
