package com.microservice.demo.statisticsDemo;

import java.io.InputStream;
import java.text.ParseException;

public class MutithreadedStatTask extends AbstractStatTask {
    //日志文件输入缓冲大小
    protected final int inputBufferSize;
    //日志记录集大小
    protected final int batchSize;
    //日志文件输入流
    protected final InputStream in;

    /* 实例初始化*/ {
        String strBufferSize = System.getProperty("x.input.buffer");
        inputBufferSize = null != strBufferSize ? Integer.valueOf(strBufferSize) : 8192;
        String strBatchSize = System.getProperty("x.batch.size");
        batchSize = null != strBatchSize ? Integer.valueOf(strBatchSize) : 2000;
    }

    public MutithreadedStatTask(int sampleInterval, StatProcessor recordProcessor) {
        super(sampleInterval, recordProcessor);
        this.in = null;
    }

    public MutithreadedStatTask(InputStream in, int sampleInterval, int traceIdDiff, String expectedOperationName, String expectedExternalDeviceList) {
        super(sampleInterval, traceIdDiff, expectedOperationName, expectedExternalDeviceList);
        this.in = in;
    }

    @Override
    protected void doCalculate() throws InterruptedException, ParseException {
        final AbstractLogReader logReaderThread =createLogReader();
        //启动工作者线程
        logReaderThread.start();
        RecordSet recordSet;
        String record;
        for (;;) {
            recordSet=logReaderThread.nextBatch();
            if (null == recordSet) {
                break;
            }
            while (null != (record = recordSet.nextRecord())) {
                recordProcessor.process(record);
            }
        }
    }

    protected AbstractLogReader createLogReader() {
        AbstractLogReader logReader = new LogReaderThread(in, inputBufferSize, batchSize);
        return logReader;
    }
}
