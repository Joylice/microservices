package com.microservice.demo.requestIdGenerator;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class lockDemo {

    private final Lock lock=new ReentrantLock();
}
