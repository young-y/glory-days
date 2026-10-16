/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.delegate.core;

import com.glory.foundation.delegate.domain.DelegateWrapper;
import com.glory.foundation.delegate.domain.DelegateContext;

/**
 * @author YY
 * @date 2026/6/15
 * @description: TODO
 */
public interface DelegateAdapter {

    boolean accept(DelegateContext context);

    default  boolean accept(DelegateWrapper object){
        return object.accept();
    }

}
