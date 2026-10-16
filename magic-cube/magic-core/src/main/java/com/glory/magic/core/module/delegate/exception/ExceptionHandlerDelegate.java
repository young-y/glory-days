/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.delegate.exception;

import com.glory.foundation.delegate.delegate.AbstractDelegate;
import com.glory.magic.core.exception.MagicException;
import com.glory.magic.core.module.component.exception.ExceptionHandler;
import org.springframework.stereotype.Component;

/**
 * @author YY
 * @date 2026/10/6
 * @description: TODO
 */
@Component
public class ExceptionHandlerDelegate extends AbstractDelegate<ExceptionHandler> {

    public void handler(MagicException e) {
        delegates(e.delegate()).forEach(h->{
            if (e.isInterrupted()){
                return;
            }
            h.handle(e);
        });
    }
}
