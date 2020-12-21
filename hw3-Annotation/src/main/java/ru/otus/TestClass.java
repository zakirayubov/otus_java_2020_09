package ru.otus;

import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;

public class TestClass {

    @Before
    public void before() {
        System.out.println("Before method");
    }

    @Before
    public void before2() {
        System.out.println("Before method");
    }

    @Test
    public void test1() {
        System.out.println("Test1 method");
    }

    @Test
    public void test2() {
        System.out.println("Test2 method");
    }

    @Test
    public void test3() throws Exception {
        System.out.println("Test3 method");
        throw new Exception();
    }

    @After
    public void after() {
        System.out.println("After method");
        System.out.println("----------------\n");
    }
}
