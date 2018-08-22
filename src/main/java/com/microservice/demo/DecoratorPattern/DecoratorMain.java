package com.microservice.demo.DecoratorPattern;

public class DecoratorMain {
    public static void main(String[] args) {
        Circle circle = new Circle();
        Shape redCircle = new RedShapeDecorator(circle);

        Shape redRectangle = new RedShapeDecorator(new Rectangle());

        circle.draw();
        redCircle.draw();
        redRectangle.draw();
    }
}
