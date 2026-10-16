/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.crypto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JavaType;
import com.glory.foundation.basic.crypto.annotation.Secret;
import com.glory.foundation.basic.crypto.domain.CryptoWrapper;
import com.glory.foundation.basic.desensitize.Desensitize;
import jakarta.persistence.Convert;


/**
 * @author : YY
 * @date : 2025/12/22
 * @descprition :
 * 	value: eg:1234567890
 *	pattern: eg:^(.{4}).*$,^(.{4}).*(.{4}).$
 *  replacement eg:$1****,$1****$2
 *  result: 1234****,1234****90
 */

public class CryptoContext {
	private String name;
	@Convert(converter = CryptoType.CryptoTypeConverter.class)
	private CryptoType type = CryptoType.MASK;
	private String pattern;//eg:^(.{4}).*$,^(.{4}).*(.{4}).$
	private String replacement;//eg:$1****,$1****$2
	private String value;
	private JavaType javaType;
	private int minLength=4;
	private String minLengthStr;
	private boolean withPrefix = false;
	private String prefix;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public CryptoType getType() {
		return type;
	}

	public void setType(CryptoType type) {
		this.type = type;
	}

	public JavaType getJavaType() {
		return javaType;
	}

	public void setJavaType(JavaType javaType) {
		this.javaType = javaType;
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

	public boolean isWithPrefix() {
		return withPrefix;
	}

	public void setWithPrefix(boolean withPrefix) {
		this.withPrefix = withPrefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@JsonIgnore
	public CryptoWrapper wrapper(){
		CryptoWrapper wrapper = new CryptoWrapper();
		wrapper.setType(getType());
		wrapper.setText(getValue());
		wrapper.setWithPrefix(isWithPrefix());
		return wrapper;
	}
	@JsonIgnore
	public static CryptoContext create(Desensitize desensitize){
		CryptoContext context = new CryptoContext();
		context.setReplacement(desensitize.replacement());
		context.setPattern(desensitize.patten());
		context.setType(desensitize.algorithm());
		context.setWithPrefix(desensitize.withPrefix());
		context.setMinLength(desensitize.minLength());
		return context;
	}

	@JsonIgnore
	public static CryptoContext create(Secret secret){
		CryptoContext context = new CryptoContext();
		context.setType(secret.algorithm());
		context.setWithPrefix(secret.withPrefix());
		context.setPrefix(secret.prefix());
		return context;
	}

	@JsonIgnore
	public static CryptoContext create(CryptoWrapper wrapper){
		CryptoContext context = new CryptoContext();
		context.setType(wrapper.getType());
		context.setValue(wrapper.getText());
		context.setWithPrefix(wrapper.isWithPrefix());
		return context;
	}

}
