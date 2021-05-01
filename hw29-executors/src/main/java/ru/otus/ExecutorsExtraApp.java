package ru.otus;

public class ExecutorsExtraApp {

    private final Object lock = new Object();

    private volatile boolean orderFlag = false;
    private volatile boolean directionFlag = true;
    private volatile int counter = 1;

    public static void main(String[] args) {
        ExecutorsExtraApp app = new ExecutorsExtraApp();
        new Thread(app::printNumbersOne, "Поток 1").start();
        new Thread(app::printNumbersTwo, "Поток 2").start();
    }

    private void printNumbersOne() {
        synchronized (lock) {
            while (counter > 0) {
                while (orderFlag) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        // empty
                    }
                }
                printNumber();
                orderFlag = true;
                lock.notifyAll();
            }
        }
    }

    private void printNumbersTwo() {
        synchronized (lock) {
            while (counter > 1) {
                while (!orderFlag) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        // empty
                    }
                }
                printNumber();
                orderFlag = false;
                lock.notifyAll();
            }
        }
    }

    private void printNumber() {
        int value = directionFlag ? counter++ : counter--;
        System.out.printf("%s: %d%n", Thread.currentThread().getName(), value);
        if (value > 8) {
            directionFlag = false;
        }
    }
}
