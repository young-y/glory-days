/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.app.domain;

import com.glory.foundation.basic.crypto.annotation.Secret;
import com.glory.foundation.basic.type.WithType;

import java.util.List;
import java.util.Map;

/**
 * @author YY
 * @date 2026/9/28
 * @description: TODO
 */
public class TestWithTypeObject {
    private String name;
    private List<Car> cars;
//    @WithType
    private List<?> cars1;
    private List<?> cars2;
//    @WithType
    private Map<String, ?> carMap;
    private Map<String, Car> carMap1;
    @WithType
    private Map<String, String> carMap2;
    private String phone;
    @Secret
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<?> getCars1() {
        return cars1;
    }

    public void setCars1(List<?> cars1) {
        this.cars1 = cars1;
    }

    public List<?> getCars2() {
        return cars2;
    }

    public void setCars2(List<?> cars2) {
        this.cars2 = cars2;
    }

    public Map<String, ?> getCarMap() {
        return carMap;
    }

    public void setCarMap(Map<String, ?> carMap) {
        this.carMap = carMap;
    }

    public Map<String, Car> getCarMap1() {
        return carMap1;
    }

    public void setCarMap1(Map<String, Car> carMap1) {
        this.carMap1 = carMap1;
    }

    public Map<String, String> getCarMap2() {
        return carMap2;
    }

    public void setCarMap2(Map<String, String> carMap2) {
        this.carMap2 = carMap2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
