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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author YY
 * @date 2026/10/6
 * @description: TODO
 */
@Component
public class PanicExceptionHandler implements ExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(PanicExceptionHandler.class);
    @Override
    public void handle(@Nonnull MagicException e) {
        e.setInterrupted( true);
        logger.warn(">>Panic exception: {}",e.getMessage());
        throw e;
    }

    @Override
    public List<ExceptionStrategy> supportStrategies() {
        return List.of(ExceptionStrategy.Panic);
    }
}
