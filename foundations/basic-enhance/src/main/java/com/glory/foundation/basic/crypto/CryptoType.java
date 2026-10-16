/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.crypto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.glory.foundation.basic.domain.PersistableEnum;
import jakarta.persistence.AttributeConverter;

/**
 * @author yong
 * @date 2026/9/25
 * @description: TODO
 */
public enum CryptoType implements PersistableEnum<String> {
    AES("AES",true,"AES/CBC/PKCS5Padding"),
    DES("DES",true,"DES/CBC/PKCS5Padding"),
    RSA("RSA",true,null),
    BASE64("BASE64",true,null),
    MD5("MD5",false,null),
    SHA256("SHA-256",false,null),
    HmacSHA256("HmacSHA256",false,null),
    MASK("MASK",false,null),
    ;

    private final String algorithm;
    private final   boolean reversible;
    private final String transformation;
    CryptoType(String algorithm,boolean reversible,String transformation){
        this.algorithm = algorithm;
        this.reversible = reversible;
        this.transformation = transformation;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getTransformation() {
        return transformation;
    }

    public boolean isReversible() {
        return reversible;
    }

    @Override
    @JsonValue
    public String value() {
        return algorithm;
    }

    @JsonCreator
    public static CryptoType fromPersistableValue(String value) {
        if (null== value) return null;
        return PersistableEnum.valueOf(value, CryptoType.class);
    }

    public static class CryptoTypeConverter implements AttributeConverter<CryptoType, String> {


        /** {@link CryptoType} to db value
         * @param attribute business data
         * @return int
         */
        @Override
        public String convertToDatabaseColumn(CryptoType attribute) {
            return attribute.value();
        }

        /** db trans to {@link CryptoType}
         * @param dbData db data
         * @return {@link CryptoType}
         */
        @Override
        public CryptoType convertToEntityAttribute(String dbData) {
            return CryptoType.fromPersistableValue(dbData);
        }
    }
}
