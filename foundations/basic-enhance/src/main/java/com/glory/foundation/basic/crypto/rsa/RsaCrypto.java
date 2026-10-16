/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.crypto.rsa;

import com.glory.foundation.basic.crypto.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author : YY
 * @date : 2025/12/12
 * @descprition :
 *
 */
@Component
public class RsaCrypto implements Crypto<String> {

	@Resource(name = "rasEncryptor")
	private Encryptor encryptor;
	@Resource(name = "rsaDecryptor")
	private Decryptor decryptor;
	/**
	 * @return
	 */
	@Override
	public CryptoType algorithm() {
		return CryptoType.RSA;
	}

	/**
	 * @param ciphertext
	 * @return
	 */
	@Override
	public String decrypt(String ciphertext) {
		return decryptor.decrypt(ciphertext);
	}

	/**
	 * @param str
	 * @return
	 */
	@Override
	public String encrypt(String str) {
		return encryptor.encrypt(str);
	}
}
