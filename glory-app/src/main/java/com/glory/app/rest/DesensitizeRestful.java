/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.app.rest;

import com.glory.app.domain.Car;
import com.glory.app.domain.DesensitizeObject;
import com.glory.app.domain.WithTypeObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author YY
 * @date 2026/9/27
 * @description: TODO
 */
@RestController
@RequestMapping("/base")
public class DesensitizeRestful {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @PostMapping("desensitize")
    public Object desensitize(@RequestBody DesensitizeObject object){
        logger.info("desensitize:{}",object);
        object.setSex("M");
        return object;
    }
    @PostMapping("/withType")
    public Object withType(@RequestBody WithTypeObject object){
        logger.info("withType:{}",object);
        return object;
    }

    @GetMapping("/withType/{name}")
    public Object desensitizeUtil(@PathVariable("name") String name){
        WithTypeObject object = new WithTypeObject();
        object.setName(name);
        List<Car> cars = new ArrayList<>();
        cars.add(Car.createCar("AAAA"));
        cars.add(Car.createCar("BBBB"));
//        object.setCars(cars);
//        object.setCars1( cars);
//        object.setCars2( cars);
        Map<String, Car> collect = cars.stream().collect(Collectors.toMap(Car::getName, c -> c));
        object.setCarMap(collect);
        object.setCarMap1(collect);
        Map<String,String> collect1 = collect.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getName()));
        object.setCarMap2(collect1);
        object.setEmail("123456@qq.com");
        object.setPhone("12345678901");
        return object;
    }
}
