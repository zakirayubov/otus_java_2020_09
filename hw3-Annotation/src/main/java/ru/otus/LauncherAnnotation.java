package ru.otus;

import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LauncherAnnotation {
    int all = 0;
    int pass = 0;
    int fail = 0;

    public void launcher(Class<?> clazz)  {
        List<Method> before = new ArrayList<>();
        List<Method> test = new ArrayList<>();
        List<Method> after = new ArrayList<>();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                before.add(method);
                all++;
            } else if (method.isAnnotationPresent(Test.class)) {
                test.add(method);
                all++;
            } else if (method.isAnnotationPresent(After.class)) {
                after.add(method);
                all++;
            }
        }

        launcherBefore(clazz, before);
        launcherTest(clazz, test);
        launcherAfter(clazz, after);

        System.out.println("Всего тестов: " + all);
        System.out.println("Успешные тесты: " + pass);
        System.out.println("Не успешные тесты: " + fail);

    }

    public void launcherBefore(Class<?> clazz, List<Method> methods) {
        for (Method method : methods) {
            try {
                method.invoke(clazz.getDeclaredConstructor().newInstance());
                pass++;
            } catch (Exception e) {
                fail++;
            }
        }
    }

    public void launcherTest(Class<?> clazz, List<Method> methods) {
        for (Method method : methods) {
            try {
                method.invoke(clazz.getDeclaredConstructor().newInstance());
                pass++;
            } catch (Exception e) {
                fail++;
            }

        }
    }

    public void launcherAfter(Class<?> clazz, List<Method> methods) {
        for (Method method : methods) {
            try {
                method.invoke(clazz.getDeclaredConstructor().newInstance());
                pass++;
            } catch (Exception e) {
                fail++;
            }
        }

    }
}