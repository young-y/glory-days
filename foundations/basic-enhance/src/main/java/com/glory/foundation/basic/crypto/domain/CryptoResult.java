/*
 * Copyright (c) 2025-2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.crypto.domain;


import com.glory.foundation.basic.crypto.CryptoType;

/**
 * @author : YY
 * @date : 2025/12/15
 * @descprition :
 *
 */

public class CryptoResult {
	private CryptoType type;
	private String text;
	private String privateKey;
	private String publicKey;

	public CryptoType getType() {
		return type;
	}

	public void setType(CryptoType type) {
		this.type = type;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public static CryptoResult of(CryptoType type){
		CryptoResult result = new CryptoResult();
		result.setType( type);
		return result;
	}

	public static CryptoResult of(CryptoType type,String text){
		CryptoResult result = new CryptoResult();
		result.type = type;
		result.text = text;
		return result;
	}

	public static CryptoResult of(String publicKey,String privateKey){
		CryptoResult result = new CryptoResult();
		result.privateKey= privateKey;
		result.publicKey = publicKey;
		return result;
	}
}
