package com.microservice.demo.DecoratorPattern;

public class RedShapeDecorator extends ShapeDecorator {
    public RedShapeDecorator(Shape shape) {
        super(shape);
    }

    public void setRedBorder() {
        System.out.println("Red AND Border is set!");
    }

    public void draw() {
        shape.draw();
        setRedBorder();
    }
}
