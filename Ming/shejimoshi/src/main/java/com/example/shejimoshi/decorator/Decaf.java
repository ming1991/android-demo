package com.example.shejimoshi.decorator;

/**
 * Created by Android-mwb on 2018/8/10.
 */
public class Decaf extends Beverage {
    public Decaf() {
        description = "Decaf Coffee";
    }

    public double cost() {
        return 1.05;
    }
}
