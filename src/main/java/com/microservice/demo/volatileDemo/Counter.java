package com.microservice.demo.volatileDemo;

public class Counter {
    private volatile long count;

    public long value() {
        return count;
    }

    public void increment() {
        synchronized (this) {
            count++;
        }
    }
}
