package com.example.stratificator.classes;

public class Condition_elem {
    private float top;
    private float down;

    public Condition_elem(float top, float down) {
        this.down = down;
        this.top = top;
    }

    public float getTop() {
        return top;
    }

    public float getDown() {
        return down;
    }
}
