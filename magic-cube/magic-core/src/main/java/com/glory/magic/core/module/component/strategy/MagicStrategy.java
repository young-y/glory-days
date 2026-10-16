/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.component.strategy;

import com.glory.foundation.delegate.domain.DelegateContext;
import com.glory.foundation.delegate.domain.DelegateWrapper;
import com.glory.magic.core.domain.context.MagicContext;
import com.glory.magic.core.domain.context.RuntimeMetadata;
import com.glory.magic.core.domain.node.MagicNode;
import com.glory.magic.core.domain.node.ResultNode;
import com.glory.magic.core.domain.plan.MagicPlanRuleBo;
import com.glory.magic.core.module.component.MagicAdapter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author YY
 * @date 2026/7/27
 * @description: TODO
 */
public interface MagicStrategy extends MagicAdapter {

    default ResultNode execute(MagicContext context){
        MagicPlanRuleBo rule = context.getRule();
        MagicNode node = context.getNode();
        ResultNode rn = context.getResult().addResultNode(rule.getReallyOutput(node));
        rn.setRule(rule);
        rn.setNode(node);
        Object value = node.getValue();
        rn.setValue(value);
        if (value == null){
            return rn;
        }
        return doExecute(new RuntimeMetadata(context, rn));
    }

    default ResultNode doExecute(RuntimeMetadata metadata) {
        return metadata.getResultNode();
    }

    List<String> supportStrategies();

    @Override
    default boolean accept(DelegateContext context) {
        if (context instanceof MagicContext) {
            return MagicAdapter.super.accept(context)
                    && accept(new DelegateWrapper(supportStrategies(),((MagicContext) context).getRule().getStrategy()));
        }
        return false;
    }

    @Component
    @Order(Integer.MAX_VALUE)
    class Impl implements MagicStrategy {

        @Override
        public ResultNode execute(MagicContext context) {
            return null;
        }

        @Override
        public List<String> supportStrategies() {
            return List.of();
        }
    }

}
