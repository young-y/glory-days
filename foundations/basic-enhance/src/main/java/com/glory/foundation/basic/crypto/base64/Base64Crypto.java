/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.crypto.base64;


import com.glory.foundation.basic.crypto.*;
import com.glory.foundation.basic.crypto.domain.CryptoWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author : YY
 * @date : 2025/12/12
 * @descprition :
 *
 */
@Component
public class Base64Crypto implements Crypto<CryptoWrapper> {

	@Resource(name = "base64Encryptor")
	private Encryptor encryptor;
	@Resource(name = "base64Decryptor")
	private Decryptor decryptor;
	/**
	 * @return
	 */
	@Override
	public CryptoType algorithm() {
		return CryptoType.BASE64;
	}

	/**
	 * @param crypto
	 * @return
	 */
	@Override
	public String decrypt(CryptoWrapper crypto) {
		return decryptor.decrypt(crypto);
	}

	/**
	 * @param crypto
	 * @return
	 */
	@Override
	public String encrypt(CryptoWrapper crypto) {
		return encryptor.encrypt(crypto);
	}
}
