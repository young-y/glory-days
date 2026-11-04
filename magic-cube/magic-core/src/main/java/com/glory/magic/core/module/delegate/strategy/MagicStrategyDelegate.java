/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.delegate.strategy;

import com.glory.foundation.delegate.delegate.AbstractDelegate;
import com.glory.magic.core.domain.context.MagicContext;
import com.glory.magic.core.domain.context.RuntimeMetadata;
import com.glory.magic.core.domain.node.ResultNode;
import com.glory.magic.core.domain.plan.MagicPlanRuleBo;
import com.glory.magic.core.module.component.strategy.MagicStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author YY
 * @date 2026/7/27
 * @description: TODO
 */
@Component
public class MagicStrategyDelegate extends AbstractDelegate<MagicStrategy> {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    public void execute(MagicContext context){
        ResultNode rn=delegate(context).execute(context);
        Optional.ofNullable(rn)
                .ifPresent(node->stepProcess(new RuntimeMetadata(context,node)));
    }

    public void stepProcess(RuntimeMetadata metadata){
        MagicContext context = metadata.getContext();
        if (context.getRule().hasStepStrategy()){
            MagicPlanRuleBo stepRule = context.getRule().copyNextStrategyRule();
            MagicContext stepContext = context.copyRule(stepRule);
            metadata.setContext(stepContext);
            logger.debug(">> step process rule[{}] is executing.",stepRule.getStrategy());
            delegate(stepContext).doExecute(metadata);
            stepProcess(metadata);
        }
    }
}
