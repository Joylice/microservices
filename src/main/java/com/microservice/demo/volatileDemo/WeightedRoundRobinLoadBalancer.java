package com.microservice.demo.volatileDemo;

public class WeightedRoundRobinLoadBalancer extends AbstractLoadBalancer {
    //私有构造器
    private WeightedRoundRobinLoadBalancer(Candidate candidate) {
        super(candidate);
    }

    //通过该静态方法创建该类的实例
    public static LoadBalancer newInstance(Candidate candidate) {
        WeightedRoundRobinLoadBalancer weightedRoundRobinLoadBalancer = new WeightedRoundRobinLoadBalancer(candidate);
        weightedRoundRobinLoadBalancer.init();
        return weightedRoundRobinLoadBalancer;
    }

    //在该方法中实现相应的负载均衡算法
    @Override
    public Endpoint nextEndpoint() {
        Endpoint selectedEndpoint = null;
        int subWeight = 0;
        int dynamicTotoalWeight;
        final double rawRnd = super.random.nextDouble();
        int rand;

        final Candidate candidate = super.candidate;
        dynamicTotoalWeight = candidate.totalWeight;
        for (Endpoint endpoint : candidate) {
            //选取节点以及计算总权重时跳过非在线节点
            if (!endpoint.isOnline()) {
                dynamicTotoalWeight -= endpoint.weight;
                continue;
            }
            rand = (int) (rawRnd * dynamicTotoalWeight);
            subWeight += endpoint.weight;
            if (rand <= subWeight) {
                selectedEndpoint = endpoint;
                break;
            }
        }
        return selectedEndpoint;
    }
}
