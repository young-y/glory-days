/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.crypto.mask;


import com.glory.foundation.basic.crypto.Crypto;
import com.glory.foundation.basic.crypto.CryptoContext;
import com.glory.foundation.basic.crypto.CryptoType;
import com.glory.foundation.basic.crypto.Encryptor;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author : YY
 * @date : 2025/12/22
 * @descprition :
 *
 */

@Component
public class PattenMaskCrypto implements Crypto<CryptoContext> {
	@Resource(name = "pattenMaskEncryptor")
	private Encryptor<CryptoContext> encryptor;
	/**
	 * @return
	 */
	@Override
	public CryptoType algorithm() {
		return CryptoType.MASK;
	}

	/**
	 * @param ciphertext
	 * @return
	 */
	@Override
	public String decrypt(String ciphertext) {
		throw new RuntimeException("Mask can't support decrypt operation.");
	}

	/**
	 * @param context
	 * @return
	 */
	@Override
	public String encrypt(CryptoContext context) {
		return encryptor.encrypt(context);
	}
}
