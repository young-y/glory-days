/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.crypto.mask;


import com.glory.foundation.basic.crypto.Crypto;
import com.glory.foundation.basic.crypto.CryptoType;
import com.glory.foundation.basic.crypto.Encryptor;
import com.glory.foundation.basic.crypto.domain.CryptoWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author : YY
 * @date : 2025/12/22
 * @descprition :
 *
 */

@Component
public class PattenMaskCrypto implements Crypto<CryptoWrapper> {
	@Resource(name = "pattenMaskEncryptor")
	private Encryptor<CryptoWrapper> encryptor;
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
	public String decrypt(CryptoWrapper ciphertext) {
		throw new RuntimeException("Mask can't support decrypt operation.");
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
