/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.service.resources.impl;

import com.glory.magic.core.domain.code.MagicCodeBo;
import com.glory.magic.core.domain.code.MagicCodeItemBo;
import com.glory.magic.core.domain.plan.MagicPlanBo;
import com.glory.magic.core.helper.MagicExplorer;
import com.glory.magic.core.service.resources.MagicResourceService;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author YY
 * @date 2026/9/22
 * @description: TODO
 */
public class JsonMagicResourceServiceImpl implements MagicResourceService {
    private MagicExplorer explorer;
    @Override
    public MagicPlanBo findPlan(@Nonnull String planCode) {
        return explorer.findPlan(planCode);
    }

    @Override
    public MagicCodeBo findCode(@Nonnull String codeName) {
        return explorer.findCode(codeName);
    }

    @Override
    public MagicCodeItemBo findCodeItem(@Nonnull String codeName, @Nonnull String itemValue, String itemValue1) {
        return explorer.findCodeItem(codeName, itemValue, itemValue1);
    }

    @Resource
    public void setExplorer(MagicExplorer explorer) {
        this.explorer = explorer;
    }
}
