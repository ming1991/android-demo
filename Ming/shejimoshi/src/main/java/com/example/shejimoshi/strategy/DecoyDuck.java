package com.example.shejimoshi.strategy;

/**
 * Created by Android-mwb on 2018/8/9.
 */
public class DecoyDuck extends Duck{
    public DecoyDuck() {
        setFlyBehavior(new FlyNoWay());
        setQuackBehavior(new QuackMute());
    }
    public void display() {
        System.out.println("I'm a duck Decoy");
    }
}
