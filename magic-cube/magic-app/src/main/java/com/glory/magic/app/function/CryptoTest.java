/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.app.function;

import com.glory.foundation.basic.crypto.CryptoType;
import com.glory.foundation.basic.crypto.util.CryptoUtil;
import com.glory.foundation.basic.crypto.domain.CryptoWrapper;

/**
 * @author YY
 * @date 2026/9/24
 * @description: TODO
 */
public class CryptoTest {

    public static void main(String[] args) {
        testAes();
        testMask("sort@163.com");

    }

    private static void testAes(){
        String str = "123456";
        CryptoWrapper wrapper = new CryptoWrapper();
        wrapper.setType(CryptoType.AES);
        wrapper.setText(str);
        String encrypt = CryptoUtil.encrypt(wrapper);
        wrapper.setText(encrypt);
        String decrypt = CryptoUtil.decrypt(wrapper);
        System.out.printf("[%s] >> str=[%s] encrypt =[%s],decrypt =[%s]%n",wrapper.getType().getAlgorithm(),str,encrypt,decrypt);
    }

    private static void testMask(String str){
        CryptoWrapper context =  CryptoWrapper.ofMask();
        context.setText(str);
        context.setPattern("^(.{4}).*(.{4})$");
        context.setReplacement("$1****$2");
        context.setMinLength(10);
        context.setMinLengthStr("****");
        String encrypt = CryptoUtil.encrypt(context);
        System.out.println("mask ->"+encrypt);
    }

    private static void testRSA(String str){
        CryptoWrapper encrypt = getRSAWrapper(true);
        encrypt.setText(str);
        String enStr = CryptoUtil.encrypt(encrypt);
        CryptoWrapper decrypt = getRSAWrapper(false);
        decrypt.setText(enStr);
        String deStr = CryptoUtil.decrypt(decrypt);
        System.out.printf("RSA >> str=[%s] encrypt =[%s],decrypt =[%s]%n",str,enStr,deStr);
    }

    private static void testBase(String str){
        CryptoWrapper wrapper = getBaseWrapper();
        wrapper.setText(str);
        test(wrapper,str);
    }
    private static void testDES(String str){
        CryptoWrapper wrapper = getDESWrapper();
        wrapper.setText(str);
        test(wrapper,str);
    }

    private static void testAes(String str){
        CryptoWrapper wrapper = getAESWrapper();
        wrapper.setText(str);
        test(wrapper,str);
    }

    private static void test(CryptoWrapper wrapper,String str){
        String encrypt = CryptoUtil.encrypt(wrapper);
        wrapper.setText(encrypt);
        String decrypt = CryptoUtil.decrypt(wrapper);
        System.out.printf("[%s] >> str=[%s] encrypt =[%s],decrypt =[%s]%n",wrapper.getType().getAlgorithm(),str,encrypt,decrypt);
    }

    private static CryptoWrapper getAESWrapper(){
        CryptoWrapper wrapper = new CryptoWrapper();
        wrapper.setType(CryptoType.AES);
        return wrapper;
    }

    private static CryptoWrapper getDESWrapper(){
        CryptoWrapper wrapper = new CryptoWrapper();
        wrapper.setType(CryptoType.DES);
//        wrapper.setSecretKey("1234ABCD");
        return wrapper;
    }

    private static CryptoWrapper getRSAWrapper(boolean encrypt){
        CryptoWrapper wrapper = new CryptoWrapper();
        wrapper.setType(CryptoType.RSA);
        if (encrypt){
            wrapper.setSecretKey("/crypto/rsa/rsa.public");
        }else {
            wrapper.setSecretKey("/crypto/rsa/rsa.private");
        }
        return wrapper;
    }

    private static CryptoWrapper getBaseWrapper(){
        CryptoWrapper wrapper = new CryptoWrapper();
        wrapper.setType(CryptoType.BASE64);
        return  wrapper;
    }
}
