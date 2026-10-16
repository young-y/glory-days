/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.helper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glory.magic.core.domain.code.MagicCodeBo;
import com.glory.magic.core.domain.code.MagicCodeItemBo;
import com.glory.magic.core.domain.node.ResultKeyNameStrategy;
import com.glory.magic.core.domain.plan.MagicPlanBo;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YY
 * @date 2026/9/22
 * @description: TODO
 */
@Component
public class MagicExplorer {
    private final Map<String, MagicPlanBo> planBoMap = new HashMap<>();
    private final Map<String, MagicCodeBo> codeBoMap = new HashMap<>();
    @SuppressWarnings("rawtypes")
    @Autowired
    private Map<String, ResultKeyNameStrategy> keyNameStrategyMap;

    public void registerCodes(@Nonnull List<MagicCodeBo> codeBos) {
        codeBos.forEach(this::registerCode);
    }
    public void registerCode(@Nonnull MagicCodeBo codeBo) {
        validateCode(codeBo);
        codeBoMap.put(codeBo.getName(), codeBo);
    }

    private void validateCode(MagicCodeBo codeBo) {
        Assert.hasLength(codeBo.getName(), " New codeName is empty");
        Assert.notEmpty(codeBo.getItems(),String.format("Code[%s] items is empty", codeBo.getName()));
    }

    public void registerPlans(@Nonnull List<MagicPlanBo> planBos) {
        planBos.forEach(this::registerPlan);
    }
    public void registerPlan(@Nonnull MagicPlanBo planBo) {
        validatePlan(planBo);
        planBoMap.put(planBo.getPlanCode(), planBo);
    }

    private void validatePlan(MagicPlanBo planBo) {
        String key = planBo.getPlanCode();
        Assert.hasLength(key, " New planCode is empty");
        Assert.isTrue(!planBoMap.containsKey(key), String.format("Plan[%s] existed", key));
        Assert.notEmpty(planBo.getRules(),String.format("Plan[%s] rules is empty", key));
    }

    public MagicPlanBo findPlan(@Nonnull String planCode) {
        return planBoMap.get(planCode);
    }

    public MagicCodeBo findCode(@Nonnull String codeName) {
        return codeBoMap.get(codeName);
    }

    @JsonIgnore
    public MagicCodeItemBo findCodeItem(@Nonnull String codeName, @Nonnull String itemValue) {
        MagicCodeBo codeBo = findCode(codeName);
        if (null == codeBo) {
            return null;
        }
        return codeBo.findItem(itemValue);
    }

    @JsonIgnore
    public MagicCodeItemBo findCodeItem(@Nonnull String codeName, @Nonnull String itemValue, @Nonnull String itemValue1) {
        MagicCodeBo codeBo = findCode(codeName);
        if (null == codeBo) {
            return null;
        }
        return codeBo.findItem(itemValue, itemValue1);
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    public ResultKeyNameStrategy<String> getKeyNameStrategy(@Nonnull String nameStrategy) {
        return keyNameStrategyMap.get(nameStrategy);
    }
}
