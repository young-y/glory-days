/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.desensitize.jackson;


import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.glory.foundation.basic.crypto.domain.CryptoWrapper;
import com.glory.foundation.basic.desensitize.DesensitizeHelper;


/**
 * @author : YY
 * @date : 2025/12/18
 * @descprition :
 *
 */

public class DesensitizeAnnotationIntrospector extends JacksonAnnotationIntrospector {

	@Override
	public Object findSerializer(Annotated a) {
		if (DesensitizeHelper.match(a)){
			return new DesensitizeSerializer();
		}
		return super.findSerializer(a);
	}

	@Override
	public Object findSerializationConverter(Annotated a) {
		if (DesensitizeHelper.match(a)){
			CryptoWrapper wrapper = DesensitizeHelper.createContext(a);
			if (null != wrapper){
				return new DesensitizeConverter(wrapper);
			}
		}
		return super.findSerializationConverter(a);
	}

}
