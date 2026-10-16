/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.crypto.util;

import com.glory.foundation.basic.crypto.CryptoType;
import com.glory.foundation.basic.crypto.domain.CryptoWrapper;
import com.glory.foundation.basic.crypto.rsa.RSAKeyProvider;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author YY
 * @date 2026/9/24
 * @description: TODO
 */
public class CryptoUtil {

    public static String encrypt(CryptoWrapper wrapper) {
        CryptoType type = wrapper.getType();
        String value = wrapper.getText();
        Assert.notNull(type,"Algorithm can't be null.");
        Assert.notNull(value,"Value can't be null.");
        String prefix ="";
        if (wrapper.isWithPrefix()){
            prefix = wrapper.getPrefixStr();
        }
        return switch (type) {
            case RSA -> wrapper.formatCipherText(rsaEncryptor().encrypt(wrapper));
            case AES -> wrapper.formatCipherText(aesEncryptor().encrypt(wrapper));
            case DES -> wrapper.formatCipherText(desEncryptor().encrypt(wrapper));
            case BASE64 -> wrapper.formatCipherText(base64Encryptor().encrypt( wrapper));
            default -> wrapper.getText();
        };
    }
    public static String decrypt(CryptoWrapper wrapper) {
        CryptoType type = wrapper.getType();
        String value = wrapper.getText();
        Assert.notNull(type,"Algorithm can't be null.");
        Assert.notNull(value,"Value can't be null.");
        if (wrapper.isWithPrefix()){
            value = value.substring(wrapper.getPrefixStr().length());
            wrapper.setText(value);
        }
        return switch (type) {
            case RSA -> rsaDecryptor().decrypt(wrapper);
            case AES -> aesDecryptor().decrypt(wrapper);
            case DES -> desDecryptor().decrypt(wrapper);
            case BASE64 -> base64Decryptor().decrypt(wrapper);
            default -> wrapper.getText();
        };
    }

    private static UtilEncryptor rsaEncryptor(){
        return (wrapper) -> {
            try {
                Cipher cipher = Cipher.getInstance(wrapper.getType().getAlgorithm());
                cipher.init(Cipher.ENCRYPT_MODE,publicKeyProvider(wrapper).provide());
                byte[] bytes = cipher.doFinal(wrapper.getText().getBytes(StandardCharsets.UTF_8));
                return Base64.getEncoder().encodeToString(bytes);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        };
    }

    private static UtilDecryptor rsaDecryptor(){
        return (wrapper) -> {
            try {
                Cipher cipher = Cipher.getInstance(wrapper.getType().getAlgorithm());
                cipher.init(Cipher.DECRYPT_MODE,privateKeyProvider(wrapper).provide());
                byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(wrapper.getText()));
                return new String(bytes,StandardCharsets.UTF_8);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        };
    }

    private static UtilEncryptor aesEncryptor(){
        return (wrapper) -> {
            try {
                String secretKey = wrapper.getSecretKey();
                String iv = wrapper.getIvParameter();
                if (!StringUtils.hasLength(iv)){
                    wrapper.setIvParameter("1234567890ABCDEF");
                }
                if (!StringUtils.hasLength(secretKey)){
                    wrapper.setSecretKey("12345690ABCDEFSKDFJKSDFODSKFKKSD");
                }
                Cipher cipher = getCipher(wrapper,Cipher.ENCRYPT_MODE,wrapper.getType().getTransformation());
                byte[] bytes = cipher.doFinal(wrapper.getText().getBytes(StandardCharsets.UTF_8));
                return Base64.getEncoder().encodeToString(bytes);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        };
    }

    private static UtilDecryptor aesDecryptor(){
        return (wrapper) -> {
            try {
                String secretKey = wrapper.getSecretKey();
                String iv = wrapper.getIvParameter();
                if (!StringUtils.hasLength(iv)){
                    wrapper.setIvParameter("1234567890ABCDEF");
                }
                if (!StringUtils.hasLength(secretKey)){
                    wrapper.setSecretKey("12345690ABCDEFSKDFJKSDFODSKFKKSD");
                }
                Cipher cipher = getCipher(wrapper,Cipher.DECRYPT_MODE,wrapper.getType().getTransformation());
                byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(wrapper.getCipherText()));
                return new String(bytes,StandardCharsets.UTF_8);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        };
    }


    private static UtilEncryptor desEncryptor(){
        return (wrapper) -> {
            try {
                String secretKey = wrapper.getSecretKey();
                String iv = wrapper.getIvParameter();
                if (!StringUtils.hasLength(iv)){
                    wrapper.setIvParameter("ABCD1234");
                }
                if (!StringUtils.hasLength(secretKey)){
                    wrapper.setSecretKey("1234ABCD");
                }
                Cipher cipher = getCipher(wrapper,Cipher.ENCRYPT_MODE,wrapper.getType().getTransformation());
                byte[] bytes = cipher.doFinal(wrapper.getCipherText().getBytes(StandardCharsets.UTF_8));
                return Base64.getEncoder().encodeToString(bytes);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        };
    }

    private static UtilDecryptor desDecryptor(){
        return (wrapper) -> {
            try {
                String secretKey = wrapper.getSecretKey();
                String iv = wrapper.getIvParameter();
                if (!StringUtils.hasLength(iv)){
                    wrapper.setIvParameter("ABCD1234");
                }
                if (!StringUtils.hasLength(secretKey)){
                    wrapper.setSecretKey("1234ABCD");
                }
                Cipher cipher = getCipher(wrapper,Cipher.DECRYPT_MODE,wrapper.getType().getTransformation());
                byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(wrapper.getCipherText()));
                return new String(bytes,StandardCharsets.UTF_8);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        };
    }

    private static UtilEncryptor base64Encryptor(){
        return (wrapper) -> {
            try {
                return Base64.getEncoder().encodeToString(wrapper.getCipherText().getBytes(StandardCharsets.UTF_8));
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        };
    }

    private static UtilDecryptor base64Decryptor(){
        return (wrapper) -> {
            try {
                return new String(Base64.getDecoder().decode(wrapper.getText()),StandardCharsets.UTF_8);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        };
    }
    private static RSAKeyProvider publicKeyProvider(CryptoWrapper wrapper){
        return ()->{
            try {
                String key = getKeyString(wrapper.getSecretKey());
                Assert.hasLength(key,"Public key is empty.");
                return KeyFactory.getInstance(wrapper.getType().getAlgorithm()).generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(key)));
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        };
    }

    private static RSAKeyProvider privateKeyProvider(CryptoWrapper  wrapper){
        return ()->{
            try {
                String key = getKeyString(wrapper.getSecretKey());
                Assert.hasLength(key,"Private key is empty.");
                return KeyFactory.getInstance(wrapper.getType().getAlgorithm()).generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
    private  static String getKeyString(String file) throws IOException {
        Resource resource = new ClassPathResource(file);
        return resource.getContentAsString(StandardCharsets.UTF_8);
    }

    private static Cipher getCipher(CryptoWrapper wrapper,int cryptMode,String transformation) throws Exception {
        String secretKey = wrapper.getSecretKey();
        String iv = wrapper.getIvParameter();
        Cipher cipher = Cipher.getInstance(transformation);
        final SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), wrapper.getType().getAlgorithm());
        final IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
        cipher.init(cryptMode, keySpec, ivParameterSpec);
        return cipher;
    }
}
