package com.microservice.demo.volatileDemo;

import java.util.HashSet;
import java.util.Set;

public class SystemBooter {
    public static void main(String[] args) {
        SystemBooter systemBooter = new SystemBooter();
        ServiceInvoker rd = ServiceInvoker.getInstance();

        LoadBalancer lb = systemBooter.createLoadBalancer();

        //在main线程中设置负载均衡器实例
        rd.setLoadBalancer(lb);
    }

    //根据系统配置创建负载均衡器实例
    private LoadBalancer createLoadBalancer() {
        LoadBalancer lb;
        Candidate candidate = new Candidate(loadEndpoints());
        lb = WeightedRoundRobinLoadBalancer.newInstance(candidate);
        return lb;
    }

    private Set<Endpoint> loadEndpoints() {
        Set<Endpoint> endpoints = new HashSet<Endpoint>();

        endpoints.add(new Endpoint("192.168.101.100", 8080, 3));
        endpoints.add(new Endpoint("192.168.101.101", 8080, 2));
        endpoints.add(new Endpoint("192.168.101.102", 8080, 5));
        endpoints.add(new Endpoint("192.168.101.103", 8080, 7));
        return endpoints;
    }
}
