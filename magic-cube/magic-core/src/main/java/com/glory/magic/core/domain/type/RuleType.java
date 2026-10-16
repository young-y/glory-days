/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
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
 * @author yong
 * @date 2026/9/18
 * @description: TODO
 */
public enum RuleType implements PersistableEnum<Integer> {
    NORMAL("normal",0),DYNAMIC("dynamic",1);

    private final String name;
    private final int value;
    private RuleType(String name, int value){
        this.name = name;
        this.value = value;
    }

    @Override
    @JsonValue
    public Integer value() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
    @JsonCreator
    public static RuleType fromPersistableValue(int value) {
        return PersistableEnum.valueOf(value, RuleType.class);
    }

    public static class RuleTypeConverter implements AttributeConverter<RuleType, Integer> {


        /** {@link RuleType} to db value
         * @param attribute business data
         * @return int
         */
        @Override
        public Integer convertToDatabaseColumn(RuleType attribute) {
            return attribute.getValue();
        }

        /** db trans to {@link RuleType}
         * @param dbData db data
         * @return {@link RuleType}
         */
        @Override
        public RuleType convertToEntityAttribute(Integer dbData) {
            return RuleType.fromPersistableValue(dbData);
        }
    }
}
