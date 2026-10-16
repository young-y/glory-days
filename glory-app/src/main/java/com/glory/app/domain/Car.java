/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.app.domain;

/**
 * @author YY
 * @date 2026/9/28
 * @description: TODO
 */
public class Car {
    private String name;
    private String color;
    private int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public static Car createCar(String  name){
        return createCar(name, "black");
    }
    public static Car createCar(String  name, String color) {
        return createCar(name, color, 1000);
    }
    public static Car createCar(String  name, String color, int price){
        Car car = new Car();
        car.setName(name);
        car.setColor(color);
        car.setPrice(price);
        return car;
    }
}
