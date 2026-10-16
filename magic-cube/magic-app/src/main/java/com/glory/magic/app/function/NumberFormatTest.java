/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.app.function;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;

/**
 * @author YY
 * @date 2026/10/4
 * @description: TODO
 */
public class NumberFormatTest {
    public static void main(String[] args) {
        number();
        currency();
        percent();
    }

    private static void number(){
        DecimalFormat format = (DecimalFormat) NumberFormat.getNumberInstance();
//        format.applyPattern("#,##0.00");
//        format.setCurrency(java.util.Currency.getInstance("CNY"));
//        format.applyLocalizedPattern("#,##0.00");
        format.setRoundingMode(java.math.RoundingMode.HALF_UP);
        System.out.println(format.format(123456.789));
    }

    private static void currency(){
        DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance();
//        format.setCurrency(java.util.Currency.getInstance("CNY"));
//        format.applyPattern("#,##0.00");
//        format.setRoundingMode(java.math.RoundingMode.HALF_UP);
        System.out.println(format.format(1232222456.789));
    }

    private static void percent(){
        DecimalFormat format = (DecimalFormat) NumberFormat.getPercentInstance();
//        format.applyLocalizedPattern("####0.00");
        format.setMultiplier(10000);
        System.out.println(format.format(0.123456789));
    }
}
