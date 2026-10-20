/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.exception;

import com.glory.foundation.basic.domain.ResultStatus;
import com.glory.magic.core.domain.context.MagicContext;
import com.glory.magic.core.domain.type.ExceptionStrategy;
import jakarta.persistence.Convert;

/**
 * @author YY
 * @date 2026/9/19
 * @description: TODO
 */
public class MagicException extends RuntimeException{
    private int code = ResultStatus.Fail.getValue();
    private boolean interrupted = false;// handler can interrupt
    private String strategy = ExceptionStrategy.Panic.getName();

    public MagicException(String message) {
        super(message);
    }
    public MagicException(Throwable cause){
        super(cause);
    }
    public MagicException(String message,Throwable cause){
        super(message, cause);
    }

    public MagicException setCode(int code) {
        this.code = code;
        return this;
    }
    public int getCode() {
        return code;
    }
    public boolean isInterrupted() {
        return interrupted;
    }

    public MagicException setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
        return this;
    }

    public String getStrategy() {
        return strategy;
    }

    public MagicException setStrategy(String strategy) {
        this.strategy = strategy;
        return  this;
    }

    public MagicContext delegate() {
        MagicContext context = new MagicContext();
        context.setExceptionStrategy(strategy);
        return context;
    }
}
