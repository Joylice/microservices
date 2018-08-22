package com.microservice.demo.volatileDemo;



public class ServiceInvoker {
    private static final ServiceInvoker instance = new ServiceInvoker();

    //负载均衡实例，使用volatile变量保证可见性
    private volatile LoadBalancer loadBalancer;

    public static ServiceInvoker getInstance() {
        return instance;
    }


    /**
     * 根据指定的负载均衡器派发请求到特定的下游部件
     *
     * @param request 带派发的请求
     */
    public void dispatchRequest(Request request) {
        //这里读取volatile变量loadBalancer
        Endpoint endpoint = getLoadBalancer().nextEndpoint();
        if (null == endpoint) {
            //省略
            return;
        }
        //将请求发给下游部件
        dispatchToDownstream(request, endpoint);
    }

    //真正将指定的请求派发给下游部件
    private void dispatchToDownstream(Request request, Endpoint endpoint) {
        System.out.println("Dispatch request to " + endpoint + ":" + request);

    }

    public LoadBalancer getLoadBalancer() {
        return loadBalancer;
    }

    public void setLoadBalancer(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }
}
