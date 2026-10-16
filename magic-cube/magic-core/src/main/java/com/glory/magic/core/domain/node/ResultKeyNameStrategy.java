/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.domain.node;

/**
 * @author YY
 * @date 2026/10/8
 * @description: TODO
 */
@FunctionalInterface
public interface ResultKeyNameStrategy<T> {

    T apply(T srcKey);

    ResultKeyNameStrategy<String> normalCase = srcKey -> srcKey;

    ResultKeyNameStrategy<String> upperCase = String::toUpperCase;

    ResultKeyNameStrategy<String> lowerCase = String::toLowerCase;

    ResultKeyNameStrategy<String> firstCase = srcKey -> {
        if (srcKey == null || srcKey.isEmpty()) {
            return srcKey;
        }
        return Character.toUpperCase(srcKey.charAt(0)) + srcKey.substring(1);
    };
}
