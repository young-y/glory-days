/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.component.filter.impl;

import com.glory.magic.core.domain.context.MagicContext;
import com.glory.magic.core.module.component.filter.MagicFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author YY
 * @date 2026/7/21
 * @description: TODO
 */
@Component
@Order(Integer.MAX_VALUE)
public class DefaultMagicFilter implements MagicFilter {
    @Override
    public boolean doFilter(MagicContext context) {
        return !context.getPlan().exclude(context.getNode());
    }
}
