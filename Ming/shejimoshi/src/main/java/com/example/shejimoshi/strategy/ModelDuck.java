package com.example.shejimoshi.strategy;

/**
 * Created by Android-mwb on 2018/8/9.
 */
public class ModelDuck extends Duck {
    public ModelDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new Quack();
    }

    public void display() {
        System.out.println("I'm a model duck");
    }
}