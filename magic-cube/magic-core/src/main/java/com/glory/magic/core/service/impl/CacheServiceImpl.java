/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.service.impl;

import com.glory.magic.core.service.CacheService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author YY
 * @date 2026/10/8
 * @description: TODO
 */
@Service
public class CacheServiceImpl implements CacheService {
    @Autowired
    private CacheManager cacheManager;
    @Override
    public void evictSpecificKey(String cacheName, Object key) {
        Optional.ofNullable(cacheManager.getCache(cacheName))
                .ifPresent(c->c.evict(key));
    }

    @Override
    public void clearCacheRegion(@Nonnull String cacheName) {
        Optional.ofNullable(cacheManager.getCache(cacheName)).ifPresent(Cache::clear);
    }

    @Override
    public void putIntoCache(String cacheName, Object key, Object value) {
        Optional.ofNullable(cacheManager.getCache(cacheName))
                .ifPresent(cache->cache.put(key,value));
    }
}
