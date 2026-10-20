/*
 * Copyright (c) 2025-2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.domain.type;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.glory.foundation.basic.domain.PersistableEnum;
import jakarta.persistence.AttributeConverter;

/**
 * @author : YY
 * @date : 2025/11/4
 * @descprition :
 *
 */

public enum ExceptionStrategy implements PersistableEnum<Integer> {
	Try("tryCatch", 0)  //init
	, Panic("panic", 1)
	,Condition("condition",2)//
	;

	private final String name;
	private final Integer value;

	private ExceptionStrategy(String name, Integer value) {
		this.name = name;
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	@Override
	@JsonValue
	public Integer value() {
		return getValue();
	}

	@JsonCreator
	public static ExceptionStrategy fromPersistableValue(int value) {
		return PersistableEnum.valueOf(value, ExceptionStrategy.class);
	}

	public static class ExceptionStrategyConverter implements AttributeConverter<ExceptionStrategy, Integer> {

		/**
		 * @param attribute
		 * @return
		 */
		@Override
		public Integer convertToDatabaseColumn(ExceptionStrategy attribute) {
			return attribute.getValue();
		}

		/**
		 * @param dbData
		 * @return
		 */
		@Override
		public ExceptionStrategy convertToEntityAttribute(Integer dbData) {
			return ExceptionStrategy.fromPersistableValue(dbData);
		}
	}
}
