package com.microservice.demo.volatileDemo;


public interface LoadBalancer {
    void updateCandidate(final Candidate candidate);

    Endpoint nextEndpoint();
}
