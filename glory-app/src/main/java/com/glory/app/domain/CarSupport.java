/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.app.domain;

import com.glory.foundation.basic.type.WithTypeSupport;

/**
 * @author YY
 * @date 2026/9/28
 * @description: TODO
 */
@WithTypeSupport
public class CarSupport extends Car {

    public static CarSupport createCar(String  name){
        return createCar(name, "black");
    }
    public static CarSupport createCar(String  name, String color) {
        return createCar(name, color, 1000);
    }
    public static CarSupport createCar(String  name, String color, int price){
        CarSupport car = new CarSupport();
        car.setName(name);
        car.setColor(color);
        car.setPrice(price);
        return car;
    }
}
