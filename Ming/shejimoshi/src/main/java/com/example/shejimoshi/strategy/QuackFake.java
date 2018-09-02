package com.example.shejimoshi.strategy;

/**
 * Created by Android-mwb on 2018/8/9.
 */
public class QuackFake implements QuackBehavior {
    public void quack() {
        System.out.println("Qwak");
    }
}
