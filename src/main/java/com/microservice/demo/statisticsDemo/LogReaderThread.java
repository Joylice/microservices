package com.microservice.demo.statisticsDemo;

import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LogReaderThread extends AbstractLogReader {
    //线程安全的队列
    final BlockingQueue<RecordSet> channel = new ArrayBlockingQueue<RecordSet>(2);


    public LogReaderThread(InputStream in, int inputBufferSize, int batchSize) {
        super(in, inputBufferSize, batchSize);
    }

    @Override
    protected RecordSet nextBatch() throws InterruptedException {
        RecordSet batch;
        //从队列中取出一个记录集
        batch = channel.take();
        if (batch.isEmpty()) {
            batch = null;
        }
        return batch;
    }

    @Override
    protected void publish(RecordSet recordBatch) throws InterruptedException {
        //把实体加到BlockingQueue里，如果BlockingQueue没有空间，则调用此方法的线程被阻断 直到有空间在继续
        channel.put(recordBatch);
    }
}
