package com.example.shejimoshi.decorator;

/**
 * Created by Android-mwb on 2018/8/10.
 */
public class HouseBlend extends Beverage {
    public HouseBlend() {
        description = "House Blend Coffee";
    }

    public double cost() {
        return .89;
    }
}