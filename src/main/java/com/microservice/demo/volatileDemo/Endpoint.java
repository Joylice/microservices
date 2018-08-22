package com.microservice.demo.volatileDemo;

import java.util.Objects;

/**
 * 表示下游部件的节点
 */
public class Endpoint {
    public final String host;
    public final int port;
    public final int weight;
    private volatile boolean online = true;

    public Endpoint(String host, int port, int weight) {
        this.host = host;
        this.port = port;
        this.weight = weight;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endpoint endpoint = (Endpoint) o;
        return port == endpoint.port &&
                weight == endpoint.weight &&
                online == endpoint.online &&
                Objects.equals(host, endpoint.host);
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((host == null) ? 0 : host.hashCode());
        result = prime * result + port;
        result = prime * result + weight;

        return result;
    }

    @Override
    public String toString() {
        return "Endpoint{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", weight=" + weight +
                ", online=" + online +
                '}';
    }
}
