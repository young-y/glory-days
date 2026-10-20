/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.component.exception;

import com.glory.foundation.delegate.domain.DelegateContext;
import com.glory.foundation.delegate.domain.DelegateWrapper;
import com.glory.magic.core.domain.context.MagicContext;
import com.glory.magic.core.exception.MagicException;
import com.glory.magic.core.module.component.MagicAdapter;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * @author YY
 * @date 2026/10/6
 * @description: TODO
 */
public interface ExceptionHandler extends MagicAdapter {

    void handle(@Nonnull MagicException e);

    List<String> supportStrategies();

    @Override
    default boolean accept(DelegateContext context) {
        if (context instanceof MagicContext) {
            return MagicAdapter.super.accept(context)
                    && accept(new DelegateWrapper(supportStrategies(),((MagicContext) context).getExceptionStrategy()));
        }
        return false;
    }
}
