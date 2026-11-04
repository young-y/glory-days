/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.desensitize.jackson;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.std.MapProperty;
import com.glory.foundation.basic.crypto.domain.CryptoWrapper;
import com.glory.foundation.basic.desensitize.DesensitizeHelper;
import com.glory.foundation.basic.desensitize.rule.DesensitizeRule;

/**
 * @author : YY
 * @date : 2025/12/18
 * @descprition :
 *	map
 */

public class DesensitizePropertyFilter extends SimpleBeanPropertyFilter {

	@Override
	protected boolean include(PropertyWriter writer) {
		return DesensitizeHelper.include(writer.getName())&&super.include(writer);
	}

	@Override
	protected boolean include(BeanPropertyWriter writer) {
		return DesensitizeHelper.include(writer.getName())&&super.include(writer);
	}

	@Override
	public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
		if (writer instanceof MapProperty) {
			String name = writer.getName();
			Object value = ((MapProperty) writer).getValue();
			if (value instanceof String str){
				if (DesensitizeHelper.match(name)){
					DesensitizeRule rule = DesensitizeHelper.findRule(name);
					if (null != rule){
						CryptoWrapper context = rule.generate();
						context.setText(str);
						((MapProperty) writer).setValue(DesensitizeHelper.desensitize(context));
					}
				}
			}
		}
		super.serializeAsField(pojo, jgen, provider, writer);
	}
}
