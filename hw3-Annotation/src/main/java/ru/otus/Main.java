package ru.otus;

public class Main {
    public static void main(String[] args) throws Exception {
        LauncherAnnotation testLauncher = new LauncherAnnotation();
        testLauncher.launcher(TestClass.class);
    }
}