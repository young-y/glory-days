/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.service.impl;

import com.glory.magic.core.domain.magic.MagicRequest;
import com.glory.magic.core.domain.magic.MagicResponse;
import com.glory.magic.core.module.component.engine.MagicCallback;
import com.glory.magic.core.module.component.engine.MagicEngine;
import com.glory.magic.core.service.MagicService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author YY
 * @date 2026/10/6
 * @description: TODO
 */
@Service
public class MagicServiceImpl implements MagicService {

    @Autowired
    private MagicEngine engine;
    @Override
    public <T, R> MagicResponse<R> execute(@Nonnull MagicRequest<T> request, MagicCallback<MagicResponse<R>> callback) {
        MagicResponse<R> response = engine.execute(request);
        Optional.ofNullable( callback).ifPresent(c->c.callback(response));
        return response;
    }

    @Override
    public <T, R> void executeAsync(@Nonnull MagicRequest<T> request, MagicCallback<MagicResponse<R>> callback) {
        engine.executeAsync(request,callback);
    }

}
