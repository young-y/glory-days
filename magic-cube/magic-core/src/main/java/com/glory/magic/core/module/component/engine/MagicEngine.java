/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.component.engine;

import com.glory.magic.core.domain.context.MagicContext;
import com.glory.magic.core.domain.magic.MagicRequest;
import com.glory.magic.core.domain.magic.MagicResponse;
import jakarta.annotation.Nonnull;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * @author YY
 * @date 2026/9/15
 * @description: TODO
 */
public interface MagicEngine {

    <T,R>MagicResponse<R> execute(@Nonnull MagicRequest<T> request);

    default <T,R>MagicResponse<R> execute(@Nonnull String planCode,@Nonnull T data){
        return execute(MagicRequest.create(planCode,data));
    }

    default <T,R>void executeAsync(@Nonnull MagicRequest<T> request,MagicCallback<MagicResponse<R>> callback){
        CompletableFuture<MagicResponse<R>> future = CompletableFuture.supplyAsync(() -> execute(request), getExecutor());
        Optional.ofNullable(callback).ifPresent(c -> future.thenAccept(c::callback));
    }

    Executor getExecutor();
}
