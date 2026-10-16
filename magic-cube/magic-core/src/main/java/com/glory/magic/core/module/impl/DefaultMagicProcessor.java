/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.impl;

import com.glory.magic.core.domain.context.MagicContext;
import com.glory.magic.core.domain.type.RuleType;
import com.glory.magic.core.module.component.processor.MagicProcessor;
import com.glory.magic.core.module.delegate.filter.MagicFilterDelegate;
import com.glory.magic.core.module.delegate.strategy.MagicStrategyDelegate;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author YY
 * @date 2026/9/17
 * @description: TODO
 */
public class DefaultMagicProcessor implements MagicProcessor {
    private final Logger logger = LoggerFactory.getLogger(DefaultMagicProcessor.class);
    private MagicStrategyDelegate magicStrategyDelegate;
    private MagicFilterDelegate magicFilterDelegate;
    @Override
    public void process(@Nonnull MagicContext context) {
        if (magicFilterDelegate.doFilter(context)){
            if (context.getRule() == null){//find rule by node,normal rule
                context.findTypeRules(RuleType.NORMAL).forEach(rule -> {
                    MagicContext copyRule = context.copyRule(rule);
                    //TODO test
                    logger.info(">> process rule[{}] input[{}] is executing",rule.getStrategy(),rule.getInput());
                    magicStrategyDelegate.execute(copyRule);
                });
            }else {//dynamic rule
                magicStrategyDelegate.execute(context);
            }
        }else {
            logger.debug(">> process:[{}] is not executed,because Filter return false",context.getNode().getPath());
        }
    }

    @Resource
    public void setMagicStrategyDelegate(MagicStrategyDelegate magicStrategyDelegate) {
        this.magicStrategyDelegate = magicStrategyDelegate;
    }

    @Resource
    public void setMagicFilterDelegate(MagicFilterDelegate magicFilterDelegate) {
        this.magicFilterDelegate = magicFilterDelegate;
    }
}
