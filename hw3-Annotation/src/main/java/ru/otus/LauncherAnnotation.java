package ru.otus;

import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LauncherAnnotation {
    private List<Method> before = new ArrayList<>();
    private List<Method> after = new ArrayList<>();
    private List<Method> test = new ArrayList<>();

    private int pass = 0;
    private int fail = 0;

    public void launcher(Class<?> clazz) throws Exception {
        distributor(clazz);

        if (test.size() != 0) {
            for (Method method : test) {
                launcher(clazz, method);
            }
        }

        System.out.println("Всего тестов: " + (pass + fail));
        System.out.println("Успешные тесты: " + pass);
        System.out.println("Не успешные тесты: " + fail);

    }

    private void distributor(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                before.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                test.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                after.add(method);
            }
        }
    }

    private void launcher(Class<?> clazz, Method testMethod) throws Exception {
        Object instance = clazz.getConstructor().newInstance();

        if (before.size() != 0) {
            for (Method method : before) {
                try {
                    method.invoke(instance);
                    pass++;
                } catch (Exception e) {
                    fail++;
                }
            }
        }

        try {
            testMethod.invoke(instance);
            pass++;
        } catch (Exception e) {
            fail++;
        }

        if (after.size() != 0) {
            for (Method method : after) {
                try {
                    method.invoke(instance);
                    pass++;
                } catch (Exception e) {
                    fail++;
                }
            }
        }
    }

}