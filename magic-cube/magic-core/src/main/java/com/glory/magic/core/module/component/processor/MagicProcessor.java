/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.component.processor;

import com.glory.magic.core.domain.context.MagicContext;
import com.glory.magic.core.module.component.MagicAdapter;
import jakarta.annotation.Nonnull;

/**
 * @author YY
 * @date 2026/9/16
 * @description: TODO
 */
public interface MagicProcessor extends MagicAdapter {

    void process(@Nonnull MagicContext context);
}
