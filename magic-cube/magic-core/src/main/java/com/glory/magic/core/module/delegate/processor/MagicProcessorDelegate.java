/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.delegate.processor;

import com.glory.foundation.delegate.delegate.AbstractDelegate;
import com.glory.magic.core.domain.context.MagicContext;
import com.glory.magic.core.module.component.processor.MagicProcessor;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

/**
 * @author YY
 * @date 2026/9/16
 * @description: TODO
 */
@Component
public class MagicProcessorDelegate extends AbstractDelegate<MagicProcessor> {

    public void process(@Nonnull MagicContext context){
        delegate(context).process(context);
    }
}
