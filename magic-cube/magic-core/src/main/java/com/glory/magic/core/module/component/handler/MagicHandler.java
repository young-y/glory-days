/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.component.handler;

import com.glory.magic.core.domain.context.MagicContext;
import com.glory.magic.core.module.component.MagicAdapter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author YY
 * @date 2026/10/21
 * @description: TODO
 */
public interface MagicHandler extends MagicAdapter {
    void handle(MagicContext context);

    @Component
    @Order(Integer.MAX_VALUE)
    class Impl implements MagicHandler{
        @Override
        public void handle(MagicContext context) {

        }
    }
}
