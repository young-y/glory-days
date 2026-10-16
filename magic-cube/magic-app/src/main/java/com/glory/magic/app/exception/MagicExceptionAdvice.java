/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.app.exception;

import com.glory.foundation.basic.domain.GloryResponse;
import com.glory.magic.core.exception.MagicException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author YY
 * @date 2026/10/7
 * @description: TODO
 */
@RestControllerAdvice
public class MagicExceptionAdvice {
    private final Logger logger = LoggerFactory.getLogger(MagicExceptionAdvice.class);

    @ExceptionHandler(MagicException.class)
    public GloryResponse<String> handleMagicException(MagicException e) {
        logger.error(">>MagicException:", e);
        return GloryResponse.fail(e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public GloryResponse<String> handleException(Exception e) {
        logger.error(">>Exception:", e);
        return GloryResponse.fail(e.getMessage());
    }
}
