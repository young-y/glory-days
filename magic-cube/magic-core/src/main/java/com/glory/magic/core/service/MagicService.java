/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.service;

import com.glory.magic.core.domain.magic.MagicRequest;
import com.glory.magic.core.domain.magic.MagicResponse;
import com.glory.magic.core.module.component.engine.MagicCallback;
import jakarta.annotation.Nonnull;

/**
 * @author YY
 * @date 2026/9/21
 * @description: TODO
 */
public interface MagicService {

    default <T,R> MagicResponse<R> execute(@Nonnull MagicRequest<T> request){
        return execute(request,null);
    }

    <T,R> MagicResponse<R> execute(@Nonnull MagicRequest<T> request, MagicCallback<MagicResponse<R>> callback);

    <T,R> void executeAsync(@Nonnull MagicRequest<T> request, MagicCallback<MagicResponse<R>> callback);

}
