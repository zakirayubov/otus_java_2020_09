package ru.otus;


import java.util.ArrayList;
import java.util.List;

class Benchmark implements BenchmarkMBean {
    private final int loopCounter;
    private volatile int size = 0;
    private List<Object> list = new ArrayList<>();

    public Benchmark(int loopCounter) {
        this.loopCounter = loopCounter;
    }

/*    void run() throws InterruptedException {
        for (int idx = 0; idx < loopCounter; idx++) {
            int local = size;
            Object[] array = new Object[local];
            for (int i = 0; i < local; i++) {
                array[i] = new String(new char[0]);
            }
            Thread.sleep(10); //Label_1
        }
    }*/


    void run() throws InterruptedException {
        long beginTime = System.currentTimeMillis();
        while (true) {
           /* if ((System.currentTimeMillis() - beginTime) / 1000 == 300) {
                break;
            }*/

            list.add(new String(new char[0]));

            long l = ((System.currentTimeMillis() - beginTime) / 1000) % 10;
            if (l == 0) {
                for (int i = list.size() - 1; i >= list.size() / 2; i--) {
                    list.remove(i);
                }
                Thread.sleep(10);
            }
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
}
