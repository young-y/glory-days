/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.impl.exception;

import com.glory.magic.core.domain.type.ExceptionStrategy;
import com.glory.magic.core.exception.MagicException;
import com.glory.magic.core.module.component.exception.ExceptionHandler;
import jakarta.annotation.Nonnull;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author YY
 * @date 2026/10/6
 * @description: TODO
 */
@Component
@Order(Integer.MAX_VALUE)
public class ConditionExceptionHandler implements ExceptionHandler {
    @Override
    public void handle(@Nonnull MagicException e) {
        if (isPanicException(e)){
            e.setInterrupted( true);
            throw e;
        }
        doHandle( e);

    }

    protected void doHandle(MagicException e) {

    }

    protected boolean isPanicException(MagicException e) {
        return false;
    }

    @Override
    public List<String> supportStrategies() {
        return List.of(ExceptionStrategy.Condition.getName());
    }
}
