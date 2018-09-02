package com.example.shejimoshi.decorator;

/**
 * Created by Android-mwb on 2018/8/10.
 */
public class DarkRoast extends Beverage {
    public DarkRoast() {
        description = "Dark Roast Coffee";
    }

    public double cost() {
        return .99;
    }
}