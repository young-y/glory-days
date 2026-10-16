/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.desensitize.config;

import com.glory.foundation.basic.crypto.CryptoType;
import com.glory.foundation.basic.crypto.util.CryptoUtil;
import com.glory.foundation.basic.crypto.domain.CryptoWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author YY
 * @date 2026/9/24
 * @description: TODO
 */
public class ConfigCryptoEnvironmentPostProcessor implements EnvironmentPostProcessor,Ordered {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public static final int ORDER = Ordered.HIGHEST_PRECEDENCE+11;
    public static final String CRYPTO_PREFIX = "CRY:";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Properties properties = new Properties();
        CryptoWrapper wrapper = getWrapper();
        environment.getPropertySources().forEach(ps -> {
            if (ps instanceof EnumerablePropertySource<?> ep){
                decryptPropertySource(ep,environment,wrapper,properties);
            }
        });
        if (!properties.isEmpty()){
            environment.getPropertySources().addFirst(new PropertiesPropertySource("decryptProperties",properties));
        }
    }

    private void decryptPropertySource(EnumerablePropertySource<?> ep,ConfigurableEnvironment  environment,CryptoWrapper wrapper, Properties properties) {
        Arrays.stream(ep.getPropertyNames()).forEach(pn->{
            Object property = ep.getProperty(pn);
            if (property instanceof String pString){
                String prefix = System.getenv("APP_CRYPTO_PREFIX");
                if (prefix == null ){
                    prefix = CRYPTO_PREFIX;
                }
                if (pString.startsWith(prefix)){
                    try {
                        String value = pString.substring(prefix.length());
                        wrapper.setText(value);
                        String decrypt = CryptoUtil.decrypt(wrapper);
                        properties.put(pn,decrypt);
                    }catch (Exception e){
                        logger.error("decrypt property error:{}",pString,e);
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private CryptoWrapper getWrapper() {
        String cryptoType = System.getenv("APP_CRYPTO_TYPE");//TODO document
        CryptoType type = CryptoType.fromPersistableValue(cryptoType);
        if (type == null){
            type = CryptoType.AES;
        }
        String secretKey = System.getenv("APP_CRYPTO_SECRET_KEY");//TODO document
        logger.trace(">> crypto type:{},secretKey:{}",type,secretKey);
        return switch (type) {
            case BASE64 -> CryptoWrapper.ofBase64();
            case DES -> CryptoWrapper.ofDES().setSecretKey(secretKey);
            case AES -> CryptoWrapper.ofAES().setSecretKey(secretKey);
            default -> throw new IllegalArgumentException("not support crypto type：" + cryptoType);
        };
    }

    @Override
    public int getOrder() {
        return ORDER;
    }
}
