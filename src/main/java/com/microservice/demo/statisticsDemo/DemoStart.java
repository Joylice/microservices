package com.microservice.demo.statisticsDemo;

import java.io.*;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicBoolean;

public class DemoStart {
    public static void main(String[] args) {
        //  AppWrapper
    }

    private static InputStream createInputStream() {
        final AtomicBoolean readerClosed = new AtomicBoolean(false);
        InputStream dataIn = DemoStart.class.getResourceAsStream("");
        final BufferedReader bfr = new BufferedReader(new InputStreamReader(dataIn)) {
            @Override
            public void close() throws IOException {
                super.close();
                readerClosed.set(true);
            }
        };
        SequenceInputStream si = new SequenceInputStream(new Enumeration<InputStream>() {
            String fileName = null;

            @Override
            public boolean hasMoreElements() {
                if (readerClosed.get()) {
                    return false;
                }
                try {
                    fileName = bfr.readLine();
                    if (null == fileName) {
                        bfr.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    fileName = null;
                }
                return null != fileName;
            }

            @Override
            public InputStream nextElement() {
                InputStream in = null;
                if (null != fileName) {
                    in = DemoStart.class.getResourceAsStream("" + fileName);
                }
                return in;
            }
        });
        return si;
    }
}
