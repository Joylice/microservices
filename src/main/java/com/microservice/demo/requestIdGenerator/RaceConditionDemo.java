package com.microservice.demo.requestIdGenerator;

public class RaceConditionDemo {
    public static void main(String[] args) {
        //客户端线程数
        int numberOfThreads = args.length > 0 ? Short.valueOf(args[0]) : Runtime.getRuntime().availableProcessors();
        Thread[] workerThreads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            workerThreads[i] = new WorkerThread(i, 10);
        }
        for (int i = 0; i < workerThreads.length; i++) {
            workerThreads[i].start();
        }
    }

    static class WorkerThread extends Thread {
        private final int requestCount;

        public WorkerThread(int id, int requestCount) {
            super("worker-" + id);
            this.requestCount = requestCount;
        }

        public void run() {
            int i = requestCount;
            String requestID;
            RequestIDGenerator requestIDGenerator = RequestIDGenerator.getInstance();
            while (i-- > 0) {
                requestID = requestIDGenerator.nextID();
                try {
                    processRequest(requestID);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //模拟请求处理
        private void processRequest(String requestID) throws InterruptedException {
            Thread.sleep(50);
            System.out.printf("%s got requestID: %s %n", Thread.currentThread().getName(), requestID);
        }
    }

}
