/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.type;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author : YY
 * @date : 2025/11/28
 * @descprition :
 *
 */

public class WithTypeObject {
	private Class<?> rawClass;
	private Object rawObject;
	private static final Set<Class <?>> whiteList = new HashSet<>();
	static {
		whiteList.add(String.class);
		whiteList.add(Integer.class);
		whiteList.add(Long.class);
		whiteList.add(Float.class);
		whiteList.add(Double.class);
		whiteList.add(Boolean.class);
		whiteList.add(Byte.class);
		whiteList.add(Character.class);
		whiteList.add(Number.class);
		whiteList.add(Short.class);
		whiteList.add(BigInteger.class);
		whiteList.add(CharSequence.class);
		whiteList.add(BigDecimal.class);
		whiteList.add(LocalDate.class);
		whiteList.add(LocalDateTime.class);
		whiteList.add(Date.class);
	}


	public void setRawClass(Class<?> rawClass) {
		Assert.isTrue(validate(rawClass), "WithType must be used in conjunction with WithTypeSupport.");
		this.rawClass = rawClass;
	}

	public Class<?> getRawClass() {
		return rawClass;
	}

	public void setRawObject(Object rawObject) {
		this.rawObject = rawObject;
	}

	public Object getRawObject() {
		return rawObject;
	}

	@JsonIgnore
	public static WithTypeObject create(@Nonnull Object obj) {
		WithTypeObject type = new WithTypeObject();
		type.setRawClass(obj.getClass());
		type.setRawObject( obj);
		return type;
	}

	private boolean validate(@Nonnull Class<?> clazz) {
		return whiteList.contains(clazz)||clazz.getAnnotation(WithTypeSupport.class)!=null;
	}

	@SuppressWarnings("unchecked")
	@JsonIgnore
	public Object getRawTypeObject() {
		if (rawObject instanceof Map map) {
			Object object = BeanUtils.instantiateClass(rawClass);
			try {
				map.forEach((k, v) -> {
					PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(rawClass, k.toString());
					if (null != descriptor) {
						try {
							descriptor.getWriteMethod().invoke(object, v);
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
				});
				return object;
			} catch (Exception e) {
				return rawObject;
			}
		}
		return rawObject;
	}
}
