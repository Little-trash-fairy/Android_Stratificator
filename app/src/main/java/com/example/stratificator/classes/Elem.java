package com.example.stratificator.classes;

public class Elem {
    private double size;
    private double value;

    public Elem(double value, double size) {
        this.size = size;
        this.value = value;
    }

    public double getElem() {
        return value;
    }

    public double getSize() {
        return size;
    }
}
