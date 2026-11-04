/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.crypto.mask;

import com.glory.foundation.basic.crypto.Encryptor;
import com.glory.foundation.basic.crypto.domain.CryptoWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author : YY
 * @date : 2025/12/23
 * @descprition :
 *
 */

@Configuration
public class PattenMaskConfiguration {
	@Value("${glory.desensitize.mask.patten:^(.{4}).*$}")
	private String patten;
	@Value("${glory.desensitize.mask.replacement:$1****}")
	private String replacement;
	@Value("${glory.desensitize.mask.min-len:****}")
	private String minLengthStr;

	@Bean("pattenMaskEncryptor")
	@ConditionalOnMissingBean(name = "pattenMaskEncryptor")
	public Encryptor<CryptoWrapper> pattenMaskEncryptor(){
		return wrapper->{
			String value = wrapper.getText();
			if (StringUtils.hasLength(value)){
				if (value.length() >= wrapper.getMinLength()){
					return value.replaceAll(getPatten(wrapper),getReplacement(wrapper));
				}else {
					return getMinLengthString(wrapper);
				}
			}
			return value;
		};
	}

	private String getReplacement(CryptoWrapper wrapper){
		return StringUtils.hasLength(wrapper.getReplacement())?wrapper.getReplacement():replacement;
	}

	private String getPatten(CryptoWrapper wrapper){
		return StringUtils.hasLength(wrapper.getPattern())?wrapper.getPattern():patten;
	}

	private String getMinLengthString(CryptoWrapper wrapper){
		return StringUtils.hasLength(wrapper.getMinLengthStr())?wrapper.getMinLengthStr():minLengthStr;
	}
}
