package com.example.shejimoshi.decorator;

/**
 * Created by Android-mwb on 2018/8/10.
 */
public class Espresso extends Beverage {

    public Espresso() {
        description = "Espresso";
    }

    public double cost() {
        return 1.99;
    }
}