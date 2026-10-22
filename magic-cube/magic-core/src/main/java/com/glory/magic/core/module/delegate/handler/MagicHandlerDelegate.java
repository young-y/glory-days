/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.delegate.handler;

import com.glory.foundation.delegate.delegate.AbstractDelegate;
import com.glory.magic.core.domain.context.MagicContext;
import com.glory.magic.core.module.component.handler.MagicHandler;
import org.springframework.stereotype.Component;

/**
 * @author YY
 * @date 2026/10/21
 * @description:
 * After process dynamic rules, handle the result or the global rules
 */
@Component
public class MagicHandlerDelegate extends AbstractDelegate<MagicHandler> {

    public void handle(MagicContext context){
        delegates(context).forEach(h->h.handle(context));
    }
}
