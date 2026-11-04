/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.desensitize.jackson;


import com.fasterxml.jackson.databind.util.StdConverter;
import com.glory.foundation.basic.crypto.domain.CryptoWrapper;
import com.glory.foundation.basic.desensitize.DesensitizeHelper;

/**
 * @author : YY
 * @date : 2025/12/23
 * @descprition :
 *
 */

public class DesensitizeConverter extends StdConverter<String,String> {
	private final CryptoWrapper wrapper;

	public DesensitizeConverter(CryptoWrapper wrapper){
		this.wrapper = wrapper;
	}
	/**
	 * @param value
	 * @return
	 */
	@Override
	public String convert(String value) {
		wrapper.setText(value);
		return DesensitizeHelper.desensitize(wrapper);
	}
}
