/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.crypto.rest;

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
@RequestMapping("crypto")
public class CryptoRestful {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource(name = "publicKeyGenerator")
    private RSAKeyGenerator privateKeyGenerator;
    @Resource(name = "privateKeyGenerator")
    private RSAKeyGenerator publicKeyGenerator;
    @Resource(name = "privateKeyProvider")
    private RSAKeyProvider privateKeyProvider;
    @Resource(name = "publicKeyProvider")
    private RSAKeyProvider publicKeyProvider;

    @PostMapping("/encrypt")
    public CryptoResult encrypt(@RequestBody CryptoWrapper wrapper){
        String encrypt = CryptoHelper.encrypt(wrapper);
        return CryptoResult.of(wrapper.getType(),encrypt);
    }
    @PostMapping("/encrypt/util")
    public CryptoResult encryptUtil(@RequestBody CryptoWrapper wrapper){
        String encrypt = CryptoUtil.encrypt(wrapper);
        return CryptoResult.of(wrapper.getType(),encrypt);
    }

    @PostMapping("/decrypt")
    public CryptoResult decrypt(@RequestBody CryptoWrapper wrapper){
        String decrypt = CryptoHelper.decrypt(wrapper);
        return CryptoResult.of(wrapper.getType(),decrypt);
    }

    @PostMapping("/decrypt/util")
    public CryptoResult decryptUtil(@RequestBody CryptoWrapper wrapper){
        String decrypt = CryptoUtil.decrypt(wrapper);
        return CryptoResult.of(wrapper.getType(),decrypt);
    }

    @PostMapping("/encrypt/mask")
    public CryptoResult encrypt(@RequestBody CryptoContext context){
        String encrypt = CryptoHelper.encrypt(context);
        return CryptoResult.of(context.getType(),encrypt);
    }
}
