/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.impl.strategy;

import com.glory.magic.core.domain.context.MagicContext;
import com.glory.magic.core.domain.context.RuntimeMetadata;
import com.glory.magic.core.domain.node.ResultNode;
import com.glory.magic.core.domain.plan.MagicPlanRuleBo;
import com.glory.magic.core.module.component.strategy.MagicStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

import static com.glory.magic.core.helper.MagicConstant.STRATEGY_CONSUME_CACHE_EVENT;

/**
 * @author YY
 * @date 2026/10/17
 * @description:
 * not create a new result node and trigger publish event consumer
 */
@Component
public class CacheEventConsumeStrategy implements MagicStrategy {

    @Override
    public ResultNode execute(MagicContext context) {
        ResultNode rn = new ResultNode();
        rn.setNode(context.getNode());
        rn.setRule(context.getRule());
        return doExecute(new RuntimeMetadata(context, rn));
    }

    @Override
    public ResultNode doExecute(RuntimeMetadata metadata) {
        MagicPlanRuleBo rule = metadata.getContext().getRule();
        String cacheEventKey = rule.getCacheEventKey();
        Assert.hasLength(cacheEventKey, "cacheEventKey is null");
        metadata.getContext().consumeCacheEvent(metadata.getResultNode());
        return null;
    }

    @Override
    public List<String> supportStrategies() {
        return List.of(STRATEGY_CONSUME_CACHE_EVENT);
    }
}
