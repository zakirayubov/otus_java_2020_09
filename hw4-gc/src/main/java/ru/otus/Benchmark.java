package ru.otus;


import java.util.concurrent.atomic.AtomicInteger;

class Benchmark implements BenchmarkMBean {
    private final int loopCounter;
    private volatile int size = 0;
    private final AtomicInteger numberOfIterationCompleted;

    public Benchmark(int loopCounter) {
        this.loopCounter = loopCounter;
        numberOfIterationCompleted = new AtomicInteger(0);
    }

    void run() throws InterruptedException {
        for (int idx = 0; idx < loopCounter; idx++) {
            int local = size;
            Object[] array = new Object[local];
            for (int i = 0; i < local; i++) {
                array[i] = new String(new char[0]);
            }
            numberOfIterationCompleted.incrementAndGet();
            Thread.sleep(10); //Label_1
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        System.out.println("new size:" + size);
        this.size = size;
    }

    public AtomicInteger getNumberOfIterationCompleted() {
        return numberOfIterationCompleted;
    }
}
