/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.crypto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JavaType;
import com.glory.foundation.basic.crypto.CryptoType;
import com.glory.foundation.basic.crypto.annotation.Secret;
import com.glory.foundation.basic.desensitize.Desensitize;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author YY
 * @date 2026/9/24
 * @description: TODO
 */
public class CryptoWrapper {
    private CryptoType type;
    private String text;
    private String secretKey;
    private String ivParameter;
    private boolean withPrefix = false;
    private String prefixStr;
    private String pattern = "^(.{4}).*(.{4})$";
    private String replacement = "$1****$2";
    private int minLength =4;
    private String minLengthStr = "****";
    private JavaType javaType;
    private String name;

    public CryptoType getType() {
        return type;
    }

    public void setType(CryptoType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public CryptoWrapper setText(String text) {
        this.text = text;
        return this;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public CryptoWrapper setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public String getIvParameter() {
        return ivParameter;
    }

    public CryptoWrapper setIvParameter(String ivParameter) {
        this.ivParameter = ivParameter;
        return this;
    }

    public boolean isWithPrefix() {
        return withPrefix;
    }

    public CryptoWrapper setWithPrefix(boolean withPrefix) {
        this.withPrefix = withPrefix;
        return  this;
    }

    public String getPrefixStr() {
        return prefixStr;
    }

    public CryptoWrapper setPrefixStr(String prefixStr) {
        this.prefixStr = prefixStr;
        return this;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public String getMinLengthStr() {
        return minLengthStr;
    }

    public void setMinLengthStr(String minLengthStr) {
        this.minLengthStr = minLengthStr;
    }

    public JavaType getJavaType() {
        return javaType;
    }

    public void setJavaType(JavaType javaType) {
        this.javaType = javaType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getCipherText(){
        if (withPrefix){
            String preStr = StringUtils.hasLength(prefixStr)? prefixStr :"{"+type.getAlgorithm()+"}";
            return text.substring(preStr.length());
        }
    	return  getText();
    }

    @JsonIgnore
    public String formatCipherText(String cipherText){
        if (withPrefix){
            String preStr = StringUtils.hasLength(prefixStr)? prefixStr :"{"+type.getAlgorithm()+"}";
            return preStr+cipherText;
        }
    	return cipherText;
    }

    public static CryptoWrapper ofRSA( ){
    	return of(CryptoType.RSA);
    }

    public static CryptoWrapper ofDES(){
    	return of(CryptoType.DES);
    }

    public static CryptoWrapper ofAES(){
    	return of(CryptoType.AES);
    }

    public static CryptoWrapper ofBase64(){
    	return of(CryptoType.BASE64);
    }

    public static CryptoWrapper ofMask(){
    	return of(CryptoType.MASK);
    }

    public static CryptoWrapper of(CryptoType type){
    	return of(type,  null,null);
    }

    public static CryptoWrapper of(CryptoType type, String secretKey, String ivParameter){
    	CryptoWrapper wrapper = new CryptoWrapper();
    	wrapper.setType(type);
    	wrapper.setSecretKey(secretKey);
    	wrapper.setIvParameter(ivParameter);
    	return wrapper;
    }

    public static CryptoWrapper of(Secret secret, String text){
    	CryptoWrapper wrapper = new CryptoWrapper();
    	wrapper.setType(secret.algorithm());
    	wrapper.setText(text);
        wrapper.setWithPrefix(secret.withPrefix());
        wrapper.setPrefixStr(secret.prefix());
    	return wrapper;
    }

    @JsonIgnore
    public static CryptoWrapper of(Desensitize desensitize){
        CryptoWrapper wrapper = new CryptoWrapper();
        wrapper.setReplacement(desensitize.replacement());
        wrapper.setPattern(desensitize.patten());
        wrapper.setType(desensitize.algorithm());
        wrapper.setWithPrefix(desensitize.withPrefix());
        wrapper.setMinLength(desensitize.minLength());
        return wrapper;
    }


}
