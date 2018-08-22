package com.microservice.demo.DecoratorPattern;

public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("rectangle is draw!");
    }
}
