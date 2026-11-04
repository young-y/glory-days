/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.crypto.base64;


import com.glory.foundation.basic.crypto.Decryptor;
import com.glory.foundation.basic.crypto.Encryptor;
import com.glory.foundation.basic.crypto.domain.CryptoWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author : YY
 * @date : 2025/12/12
 * @descprition :
 *
 */

@Configuration
public class Base64CryptoConfiguration {

	@Bean("base64Encryptor")
	@ConditionalOnMissingBean(name = "base64Encryptor")
	public Encryptor<CryptoWrapper> base64Encryptor() {
		return wrapper -> {
			String text = wrapper.getText();
			if (StringUtils.hasLength(text)) {
				return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
			}
			return text;
		};
	}

	@Bean("base64Decryptor")
	@ConditionalOnMissingBean(name = "base64Decryptor")
	public Decryptor<CryptoWrapper> base64Decryptor() {
		return wrapper -> {
			String ciphertext = wrapper.getCipherText();
			if (StringUtils.hasLength(ciphertext)) {
				return new String(Base64.getDecoder().decode(ciphertext));
			}
			return ciphertext;
		};
	}
}
