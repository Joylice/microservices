package com.microservice.demo.volatileDemo;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractLoadBalancer implements LoadBalancer {
    private final static Logger logger = Logger.getAnonymousLogger();

    //使用volatile变量替代锁（有条件替换）
    protected volatile Candidate candidate;
    protected final Random random;

    //心跳线程
    private Thread heartbeatThread;

    public AbstractLoadBalancer(Candidate candidate) {
        if (null == candidate || 0 == candidate.getEndpointCount()) {
            throw new IllegalArgumentException("Invalid candidate " + candidate);
        }
        this.candidate = candidate;
        random = new Random();
    }

    public synchronized void init() {
        if (null == heartbeatThread) {
            heartbeatThread = new Thread(new HeartbeatTask(), "LB_Heartbeat");
            heartbeatThread.setDaemon(true);
            heartbeatThread.start();
        }
    }

    @Override
    public void updateCandidate(Candidate candidate) {
        if (null == candidate || 0 == candidate.getEndpointCount()) {
            throw new IllegalArgumentException("Invalid candidate " + candidate);
        }
        //更新volatilen变量candidate
        this.candidate = candidate;
    }

    /**
     * 留给子类实现的抽象方法
     *
     * @return
     */
    @Override
    public abstract Endpoint nextEndpoint();

    protected void monitorEndpoints() {
        //获取volatile变量
        final Candidate currCandidate = candidate;
        boolean isTheEndpointOnline;

        //检测下游部件状态是否正常
        for (Endpoint endpoint : currCandidate) {
            isTheEndpointOnline = endpoint.isOnline();
            if (doDetect(endpoint) != isTheEndpointOnline) {
                endpoint.setOnline(!isTheEndpointOnline);
                if (isTheEndpointOnline) {
                    logger.log(Level.SEVERE, endpoint + "offline!");
                    System.out.println(endpoint + "offline!");
                } else {
                    logger.log(Level.INFO, endpoint + "is online now!");
                    System.out.println(endpoint + "online!");
                }
            }
        }
    }

    private boolean doDetect(Endpoint endpoint) {
        boolean online = true;
        //模拟待测服务器随机故障
        int rand = random.nextInt(1000);
        if (rand <= 500) {
            online = false;
        }
        return online;
    }

    private class HeartbeatTask implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    //检测节点列表中所有节点是否在线
                    monitorEndpoints();
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
