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
import com.glory.magic.core.domain.node.ResultKeyNameStrategy;
import jakarta.persistence.AttributeConverter;

/**
 * @author : YY
 * @date : 2025/11/4
 * @descprition :
 *
 */
@Deprecated
public enum KeyNameStrategy implements PersistableEnum<String> {
	Normal("normal", ResultKeyNameStrategy.normalCase)  //init
	, First("first", ResultKeyNameStrategy.firstCase)
	,Upper("upper",ResultKeyNameStrategy.upperCase)//success
	,Lower("lower",ResultKeyNameStrategy.lowerCase)//success
	;

	private final String name;
	private final ResultKeyNameStrategy<String> strategy;

	private KeyNameStrategy(String name, ResultKeyNameStrategy<String> strategy) {
		this.name = name;
		this.strategy = strategy;
	}

	public ResultKeyNameStrategy<String> getStrategy() {
		return strategy;
	}

	public String getName() {
		return name;
	}

	@Override
	@JsonValue
	public String value() {
		return getName();
	}

	@JsonCreator
	public static KeyNameStrategy fromPersistableValue(String value) {
		if (value == null) return null;
		return PersistableEnum.valueOf(value, KeyNameStrategy.class);
	}

	public static class KeyNameStrategyConverter implements AttributeConverter<KeyNameStrategy, String> {

		/**
		 * @param attribute
		 * @return
		 */
		@Override
		public String convertToDatabaseColumn(KeyNameStrategy attribute) {
			return attribute.getName();
		}

		/**
		 * @param dbData
		 * @return
		 */
		@Override
		public KeyNameStrategy convertToEntityAttribute(String dbData) {
			return KeyNameStrategy.fromPersistableValue(dbData);
		}
	}
}
