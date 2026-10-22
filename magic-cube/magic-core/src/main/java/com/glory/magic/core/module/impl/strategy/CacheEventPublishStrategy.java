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
import com.glory.magic.core.domain.node.MagicNode;
import com.glory.magic.core.domain.node.ResultNode;
import com.glory.magic.core.domain.plan.MagicPlanRuleBo;
import com.glory.magic.core.module.component.strategy.MagicStrategy;
import com.glory.magic.core.module.delegate.strategy.MagicStrategyDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Consumer;

import static com.glory.magic.core.helper.MagicConstant.STRATEGY_PUBLISH_CACHE_EVENT;

/**
 * @author YY
 * @date 2026/10/17
 * @description:
 * delay execute strategy when consume cache event happened
 */
@Component
public class CacheEventPublishStrategy implements MagicStrategy {
    @Autowired
    private MagicStrategyDelegate magicStrategyDelegate;
    @Override
    public ResultNode execute(MagicContext context) {
        Consumer<ResultNode> consumer = resultNode -> {
            MagicPlanRuleBo rule = context.getRule();
            MagicNode node = context.getNode();
            ResultNode rn;
            if (resultNode.isTmpResultNode()){
                rn = context.getResult().addResultNode(rule.getReallyOutput(node));
            }else {
                String name = StringUtils.hasLength(rule.getAlias())?rule.getAlias():node.getName();
                rn = context.getResult().addResultNode(resultNode.getNewOutput(name));
            }
            rn.setRule(rule);
            rn.setNode(node);
            Object value = node.getValue();
            rn.setValue(value);
            if (value != null){
                magicStrategyDelegate.stepProcess(new RuntimeMetadata(context,rn));
            }
        };
        context.publishCacheEvent(consumer);
        return null;
    }

    @Override
    public List<String> supportStrategies() {
        return List.of(STRATEGY_PUBLISH_CACHE_EVENT);
    }
}
