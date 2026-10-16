/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.app.rest;

import com.glory.foundation.basic.crypto.CryptoContext;
import com.glory.foundation.basic.crypto.CryptoHelper;
import com.glory.foundation.basic.crypto.CryptoType;
import com.glory.foundation.basic.crypto.annotation.Secret;
import com.glory.foundation.basic.crypto.domain.CryptoResult;
import com.glory.foundation.basic.crypto.domain.CryptoWrapper;
import com.glory.foundation.basic.crypto.rsa.RSAKeyGenerator;
import com.glory.foundation.basic.crypto.rsa.RSAKeyProvider;
import com.glory.foundation.basic.crypto.util.CryptoUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Base64;

/**
 * @author YY
 * @date 2026/9/25
 * @description: TODO
 */
@RestController
@RequestMapping("/foundation/crypto")
public class GloryCryptoRestful {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource(name = "publicKeyGenerator")
    private RSAKeyGenerator privateKeyGenerator;
    @Resource(name = "privateKeyGenerator")
    private RSAKeyGenerator publicKeyGenerator;
    @Resource(name = "privateKeyProvider")
    private RSAKeyProvider privateKeyProvider;
    @Resource(name = "publicKeyProvider")
    private RSAKeyProvider publicKeyProvider;

    @GetMapping("/generateRasKey/{slatKey}")
    public CryptoResult generateKey(@PathVariable("slatKey") String slatKey){
        String privateKey = privateKeyGenerator.generate(slatKey);
        String pubicKey = publicKeyGenerator.generate(slatKey);
        CryptoResult re = CryptoResult.of(CryptoType.RSA);
        re.setText(slatKey);
        re.setPrivateKey(privateKey);
        re.setPublicKey(pubicKey);
        return re;
    }

    @GetMapping("/getRasKey")
    public CryptoResult getRSAKey(){
        Key publicKey = publicKeyProvider.provide();
        Key privateKey = privateKeyProvider.provide();
        CryptoResult re = CryptoResult.of(CryptoType.RSA);
        re.setPublicKey(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        re.setPrivateKey(Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        return re;
    }

    @GetMapping("/para/{text}")
    public CryptoResult testPara(@Secret @PathVariable("text") String text){
        logger.info(">> para text ={}",text);
        return CryptoResult.of(CryptoType.AES,text);
    }

}
