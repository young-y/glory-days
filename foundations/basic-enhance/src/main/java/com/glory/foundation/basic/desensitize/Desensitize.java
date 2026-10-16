/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.desensitize;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.glory.foundation.basic.crypto.CryptoType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : YY
 * @date : 2025/12/17
 * @descprition :
 *
 */
@JacksonAnnotationsInside
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
//@JsonSerialize(using = DesensitizeSerializer.class) //impact globe serialize
public @interface Desensitize {
	CryptoType algorithm() default CryptoType.MASK;
	String patten() default "";
	String replacement() default "";
	int minLength() default 4;
	boolean withPrefix() default false;
}
