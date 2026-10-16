/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.resource.service.impl;

import com.glory.magic.core.domain.code.MagicCodeBo;
import com.glory.magic.core.domain.code.MagicCodeItemBo;
import com.glory.magic.core.domain.plan.MagicPlanBo;
import com.glory.magic.core.service.resources.MagicResourceService;
import com.glory.magic.resource.service.MagicCodeService;
import com.glory.magic.resource.service.MagicPlanService;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author YY
 * @date 2026/9/22
 * @description: TODO
 */
@Service
public class MagicResourceServiceImpl implements MagicResourceService {
    private MagicPlanService planService;
    private MagicCodeService codeService;
    @Override
    public MagicPlanBo findPlan(@Nonnull String planCode) {
        return planService.findByPlanCode(planCode);
    }

    @Override
    public MagicCodeBo findCode(@Nonnull String codeName) {
        return codeService.findCode(codeName);
    }

    @Override
    public MagicCodeItemBo findCodeItem(@Nonnull String codeName, @Nonnull String itemValue, String itemValue1) {
        return codeService.findCodeItem(codeName, itemValue, itemValue1);
    }

    @Resource
    public void setPlanService(MagicPlanService planService) {
        this.planService = planService;
    }

    @Resource
    public void setCodeService(MagicCodeService codeService) {
        this.codeService = codeService;
    }
}
