/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.type;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : YY
 * @date : 2025/11/28
 * @descprition
 * Because the type is erased during serialization, when deserialization does not specify a type,
 * the object will be serialized as a LinkedHashMap.
 * UsingWithType allows the type information to be saved during serialization,
 * but there may be security issues during deserialization.
 * Therefore, when using withType, the target object must be accompanied by withTypeSupport to ensure type safety.
 * Exception message is 'WithType must be used in conjunction with WithTypeSupport.'
 * eg :List<Car> cars; add @WithTypeSupport to Car class
 * The following types in the whitelist do not require addition.
 *      whiteList.add(String.class);
 * 		whiteList.add(Integer.class);
 * 		whiteList.add(Long.class);
 * 		whiteList.add(Float.class);
 * 		whiteList.add(Double.class);
 * 		whiteList.add(Boolean.class);
 * 		whiteList.add(Byte.class);
 * 		whiteList.add(Character.class);
 * 		whiteList.add(Short.class);
 * 		whiteList.add(BigInteger.class);
 * 		whiteList.add(CharSequence.class);
 * 		whiteList.add(BigDecimal.class);
 * 		whiteList.add(LocalDate.class);
 * 		whiteList.add(LocalDateTime.class);
 * 		whiteList.add(Date.class);
 */
@JacksonAnnotationsInside
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JsonSerialize(using = WithTypeSerializer.class)
@JsonDeserialize(using = WithTypeDeserializer.class)
public @interface WithType {

 }
