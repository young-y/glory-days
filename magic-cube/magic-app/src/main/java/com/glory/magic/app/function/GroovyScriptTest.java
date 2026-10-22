/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.app.function;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YY
 * @date 2026/10/20
 * @description: TODO
 */
public class GroovyScriptTest {

    public static void main(String[] args) {
        String script = "(butie+salary)*(1-tax)*member";
        Map<String,Object> params = new HashMap<>();
        params.put("member", 4);
        params.put("butie", 456.00);
        params.put("tax",0.2);
        params.put("salary", 40000.00);
        test(script,params);

    }

    private static void sum(){
        List<BigDecimal> list = List.of(BigDecimal.valueOf(100),BigDecimal.valueOf(2000),BigDecimal.valueOf(30));
        list.stream().mapToDouble(BigDecimal::doubleValue).sum();
    }

    private static void test(String  script, Map<String,Object> params){
        Binding binding = new Binding(params);
//        params.forEach(binding::setVariable);
        GroovyShell shell = new GroovyShell(binding);
        Script parse = shell.parse(script);
        Object run = parse.run();
        System.out.println("result = "+run);

    }
}
