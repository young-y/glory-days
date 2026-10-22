/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.impl.handler;

import com.glory.magic.core.domain.context.MagicContext;
import com.glory.magic.core.module.component.handler.MagicHandler;
import com.glory.magic.core.module.delegate.processor.MagicProcessorDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author YY
 * @date 2026/10/21
 * @description:
 *  process global rules
 */
@Component
@Order(5)
public class GlobalRuleStrategyHandler implements MagicHandler {
    @Autowired
    private MagicProcessorDelegate magicProcessorDelegate;
    @Override
    public void handle(MagicContext context) {
        context.getPlan().processGlobalRules(context,c->magicProcessorDelegate.process(c));
    }
}
