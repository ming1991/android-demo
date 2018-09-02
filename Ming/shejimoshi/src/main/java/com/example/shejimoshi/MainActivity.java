package com.example.shejimoshi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shejimoshi.decorator.Beverage;
import com.example.shejimoshi.decorator.DarkRoast;
import com.example.shejimoshi.decorator.Espresso;
import com.example.shejimoshi.decorator.HouseBlend;
import com.example.shejimoshi.decorator.Mocha;
import com.example.shejimoshi.decorator.Soy;
import com.example.shejimoshi.decorator.Whip;
import com.example.shejimoshi.observer.CurrentConditionsDisplay;
import com.example.shejimoshi.observer.ForecastDisplay;
import com.example.shejimoshi.observer.HeatIndexDisplay;
import com.example.shejimoshi.observer.StatisticsDisplay;
import com.example.shejimoshi.observer.WeatherData;
import com.example.shejimoshi.strategy.Duck;
import com.example.shejimoshi.strategy.FlyRocketPowered;
import com.example.shejimoshi.strategy.MallardDuck;
import com.example.shejimoshi.strategy.ModelDuck;


/**
 * HeadFirst设计模式-pdf
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //testStrategyPattern();
                //testObserverPattern();
                testDecoratorPattern();

            }
        });
    }

    /**
     * 装饰者模式
     */
    private void testDecoratorPattern() {
        Beverage beverage = new Espresso();
        System.out.println(beverage.getDescription()
                + " $" + beverage.cost());

        Beverage beverage2 = new DarkRoast();
        beverage2 = new Mocha(beverage2);
        beverage2 = new Mocha(beverage2);
        beverage2 = new Whip(beverage2);
        System.out.println(beverage2.getDescription()
                + " $" + beverage2.cost());

        Beverage beverage3 = new HouseBlend();
        beverage3 = new Soy(beverage3);
        beverage3 = new Mocha(beverage3);
        beverage3 = new Whip(beverage3);
        System.out.println(beverage3.getDescription()
                + " $" + beverage3.cost());
    }

    /**
     * 观察者模式
     */
    private void testObserverPattern() {
        WeatherData weatherData = new WeatherData();

        CurrentConditionsDisplay currentConditions = new CurrentConditionsDisplay(weatherData);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);
        HeatIndexDisplay heatIndexDisplay = new HeatIndexDisplay(weatherData);

        weatherData.setMeasurements(80, 65, 30.4f);
        weatherData.setMeasurements(82, 70, 29.2f);
        weatherData.setMeasurements(78, 90, 29.2f);
    }

    /**
     * 策略模式
     */
    private void testStrategyPattern() {
        Duck mallard = new MallardDuck();
        mallard.performQuack();
        mallard.performFly();

        Duck model = new ModelDuck();
        model.performFly();
        model.setFlyBehavior(new FlyRocketPowered());
        model.performFly();
    }
}
