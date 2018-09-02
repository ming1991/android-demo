package com.example.shejimoshi.strategy;

/**
 * Created by Android-mwb on 2018/8/9.
 */
public class RubberDuck extends Duck {

    public RubberDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new QuackSqueak();
    }

    public void display() {
        System.out.println("I'm a rubber duckie");
    }
}

