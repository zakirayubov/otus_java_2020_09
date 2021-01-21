package ru.otus;

public class Main {
    public static void main(String[] args) {
        TestLogging calculation = Ioc.createMyClass();
        calculation.calculation(6);
        calculation.calculation(5, 7, "Zaka");
        calculation.test();
    }
}
