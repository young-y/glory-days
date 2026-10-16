/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.crypto;


import com.glory.foundation.basic.crypto.domain.CryptoWrapper;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author : YY
 * @date : 2025/12/11
 * @descprition :
 *
 */
@Component
@SuppressWarnings({"unchecked", "rawtypes"})
public class CryptoHelper {

	private  static CryptoType defaultType;

	private final static Map<CryptoType, Crypto> cryptoMap = new HashMap<>();

	public static Crypto getCrypto(CryptoType type) {
		Assert.notNull(type,"Algorithm can't be null.");
		return cryptoMap.get(type);
	}

	/**
	 * only support Mask crypto
	 * @param context
	 * @return
	 */
	public static String encrypt(CryptoContext context){
		Assert.isTrue(CryptoType.MASK == context.getType(),"Algorithm must be Mask.");
		return getCrypto(context.getType()).encrypt(context);
	}

	public static String encrypt(@Nonnull String text) {
		return encrypt(defaultType,text,false);
	}
	public static String encrypt(@Nonnull String text, boolean withPrefix) {
		return encrypt(defaultType,text,withPrefix);
	}
	public static String encrypt(@Nonnull CryptoType type,@Nonnull String text) {
		return encrypt(type, text, false);
	}

	public static String encrypt(@Nonnull CryptoWrapper wrapper) {
		Assert.notNull(wrapper.getType(),"Wrapper type can't be null.");
		Crypto crypto = getCrypto(wrapper.getType());
		Assert.notNull(crypto, wrapper.getType() + " can't found Crypto.");
		return wrapper.formatCipherText(crypto.encrypt(wrapper.getText()));
	}

	public static String encrypt( @Nonnull CryptoType algorithm,@Nonnull String text, boolean withPrefix) {
		CryptoWrapper wrapper = CryptoWrapper.of(algorithm).setText( text).setWithPrefix(withPrefix);
		return encrypt( wrapper);
	}

	public static String decrypt(@Nonnull String ciphertext) {
		return decrypt(defaultType,ciphertext,false);
	}
	public static String decrypt(@Nonnull String ciphertext, boolean withPrefix) {
		return decrypt(defaultType,ciphertext,withPrefix);
	}
	public static String decrypt(@Nonnull CryptoType algorithm,@Nonnull String ciphertext) {
		return decrypt(algorithm,ciphertext,  false);
	}

	public static String decrypt(@Nonnull CryptoType algorithm,@Nonnull String ciphertext,  boolean withPrefix) {
		CryptoWrapper wrapper = CryptoWrapper.of(algorithm).setText( ciphertext).setWithPrefix(withPrefix);
		return decrypt(wrapper);
	}

	public static String decrypt(@Nonnull CryptoWrapper wrapper) {
		Assert.notNull(wrapper.getType(),"Wrapper type can't be null.");
		Assert.isTrue(wrapper.getType().isReversible(),"Algorithm must be reversible.");
		Assert.hasLength(wrapper.getText(),"Wrapper text can't be null.");
		Crypto crypto = getCrypto(wrapper.getType());
		Assert.notNull(crypto, wrapper.getType() + " can't found Crypto.");
		return crypto.decrypt(wrapper.getCipherText());
	}

	private static String getPrefix(String algorithm, boolean withPrefix) {
		StringBuilder builder = new StringBuilder();
		if (withPrefix) {
			builder.append("{").append(algorithm).append("}");
		}
		return builder.toString();
	}

	public static String encryptAES(@Nonnull String text) {
		return encrypt(CryptoType.AES,text);
	}

	public static String decryptAES(@Nonnull String ciphertext) {
		return decrypt(CryptoType.AES, ciphertext);
	}

	public static String encryptBase64(@Nonnull String text) {
		return encrypt(CryptoType.BASE64, text);
	}

	public static String decryptBase64(@Nonnull String ciphertext) {
		return decrypt(CryptoType.BASE64, ciphertext);
	}

	@Autowired
	public void setCryptos(List<Crypto> cryptos) {
		Optional.ofNullable(cryptos).ifPresent(cs -> {
			cs.forEach(crypto -> {
				cryptoMap.put(crypto.algorithm(), crypto);
			});
		});
	}

	@Value("${glory.secret.algorithm.default:AES}")
	public void setDefaultType(String algorithm) {
		CryptoHelper.defaultType = CryptoType.fromPersistableValue(algorithm);
	}
}
