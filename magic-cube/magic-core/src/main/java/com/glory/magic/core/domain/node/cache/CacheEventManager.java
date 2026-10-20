/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.domain.node.cache;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glory.magic.core.domain.context.MagicContext;
import com.glory.magic.core.domain.node.ResultNode;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author YY
 * @date 2026/10/17
 * @description: TODO
 */
public class CacheEventManager {
    private final Map<String, List<PublishCacheEvent>> cacheEventMap = new HashMap<>();
    private final List<ConsumeCacheEvent> consumeCacheEventSet = new ArrayList<>();

    public void publishCacheEvent(@Nonnull MagicContext context, Consumer<ResultNode> transmit) {
        publishCacheEvent(new PublishCacheEvent(context, transmit));
    }

    public void publishCacheEvent(@Nonnull PublishCacheEvent publishCacheEvent) {
        List<PublishCacheEvent> publishCacheEvents = cacheEventMap.computeIfAbsent(publishCacheEvent.getCacheEventKey(), k -> new ArrayList<>());
        publishCacheEvents.add(publishCacheEvent);
        triggerPublishCacheEvent(publishCacheEvent);
    }

    public void consumeCacheEvent(@Nonnull ResultNode resultNode) {
        consumeCacheEvent(new ConsumeCacheEvent(resultNode));
    }

    public void consumeCacheEvent(@Nonnull ConsumeCacheEvent consumeCacheEvent) {
        consumeCacheEventSet.add(consumeCacheEvent);
        triggerConsumeCacheEvent(consumeCacheEvent);
    }

    public void triggerConsumeCacheEvent(ConsumeCacheEvent consumeCacheEvent) {
        List<PublishCacheEvent> publishCacheEvents = cacheEventMap.get(consumeCacheEvent.getCacheEventKey());
        if (publishCacheEvents != null) {
            for (PublishCacheEvent publishCacheEvent : publishCacheEvents) {
                publishCacheEvent.transmit().accept(consumeCacheEvent.resultNode);
            }
        }
    }

    public void triggerPublishCacheEvent(@Nonnull PublishCacheEvent publishCacheEvent) {
        consumeCacheEventSet.stream().filter(publishCacheEvent::matchEvent).forEach(cacheEvent -> {
            publishCacheEvent.transmit().accept(cacheEvent.resultNode);
        });
    }

    @JsonIgnore
    public void clear(String cacheEventKey) {
        cacheEventMap.remove(cacheEventKey);
    }

    @JsonIgnore
    public void clear() {
        cacheEventMap.clear();
        consumeCacheEventSet.clear();
    }

    public record ConsumeCacheEvent(ResultNode resultNode) {
        public String getCacheEventKey() {
            return resultNode.getRule().getCacheEventKey();
        }

    }

    /**
     * @author YY
     * @date 2026/10/16
     * @description: TODO
     */
    public record PublishCacheEvent(MagicContext context, Consumer<ResultNode> transmit) {
        public PublishCacheEvent(@Nonnull MagicContext context, Consumer<ResultNode> transmit) {
            this.transmit = transmit;
            this.context = context;
        }

        public String getCacheEventKey() {
            return context.getRule().getCacheEventKey();
        }

        @Override
        public Consumer<ResultNode> transmit() {
            return null == transmit ? (event) -> {
            } : transmit;
        }

        @JsonIgnore
        public boolean matchEvent(@Nonnull ConsumeCacheEvent eventKey) {
            return getCacheEventKey().equals(eventKey.getCacheEventKey());
        }
    }

}
