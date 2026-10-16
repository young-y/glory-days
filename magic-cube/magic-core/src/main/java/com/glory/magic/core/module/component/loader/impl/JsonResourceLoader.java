/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.component.loader.impl;

import com.glory.foundation.basic.formater.FormatHelper;
import com.glory.magic.core.module.component.loader.MagicResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YY
 * @date 2026/9/21
 * @description: TODO
 */
public abstract class JsonResourceLoader<T> implements MagicResourceLoader {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void load() {
        loadResource().forEach(this::handleResource);
    }

    protected abstract   void handleResource(T resource);

    @SuppressWarnings("unchecked")
    protected Class<T> getActualType() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }
    protected abstract String getResourcePath();

    protected  List<T> loadResource()  {
        String resourcePath = "classpath:"+getResourcePath() ;
        List<T> list = new ArrayList<>();
        try {
            Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader()).getResources(resourcePath);
            for (Resource r : resources) {
                T t = FormatHelper.fromResource(r,getActualType());
                list.add(t);
            }
        } catch (IOException e) {
            logger.debug(">>load resource{} exception", resourcePath, e);
            logger.info(">>load resource{} fail", resourcePath);
        }
        return list;
    }

}
