package com.microservice.demo.volatileDemo;

import util.Tools;

public class RequestCase {
    public static void main(String[] args) {
        //初始化请求派发器RequestDispather
        SystemBooter.main(new String[]{});
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            new RequestSender().start();
        }
    }

    static class RequestSender extends Thread {
        private static long id = -1;

        public RequestSender() {
        }

        static synchronized long nextId() {
            return ++id;
        }

        @Override
        public void run() {
            ServiceInvoker rd = ServiceInvoker.getInstance();
            for (int i = 0; i < 100; i++) {
                rd.dispatchRequest(new Request(nextId(), 1));
                Tools.randomPause(100);
            }
        }
    }
}
