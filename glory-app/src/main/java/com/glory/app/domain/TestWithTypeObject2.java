/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.app.domain;

import com.glory.foundation.basic.type.WithType;

import java.util.List;
import java.util.Map;

/**
 * @author YY
 * @date 2026/9/28
 * @description: TODO
 */
public class TestWithTypeObject2 {
    private String name;
    @WithType
    private Car car;
    @WithType
    private CarSupport carSupport;
    @WithType
    private List<Car> cars;
    @WithType
    private List<String> names;
    @WithType
    private List<CarSupport> carSupports;
    @WithType
    private Map<String, Car> carMap;
    @WithType
    private Map<String, CarSupport> carMapSupport;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public CarSupport getCarSupport() {
        return carSupport;
    }

    public void setCarSupport(CarSupport carSupport) {
        this.carSupport = carSupport;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<CarSupport> getCarSupports() {
        return carSupports;
    }

    public void setCarSupports(List<CarSupport> carSupports) {
        this.carSupports = carSupports;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public Map<String, Car> getCarMap() {
        return carMap;
    }

    public void setCarMap(Map<String, Car> carMap) {
        this.carMap = carMap;
    }

    public Map<String, CarSupport> getCarMapSupport() {
        return carMapSupport;
    }

    public void setCarMapSupport(Map<String, CarSupport> carMapSupport) {
        this.carMapSupport = carMapSupport;
    }

}
