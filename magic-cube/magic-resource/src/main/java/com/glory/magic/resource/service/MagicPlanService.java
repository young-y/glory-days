/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.resource.service;

import com.glory.magic.core.domain.plan.*;
import com.glory.magic.resource.domain.bo.MagicQueryRequest;
import com.glory.magic.resource.domain.bo.MagicQueryResponse;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * @author YY
 * @date 2026/9/21
 * @description: TODO
 */
public interface MagicPlanService {
    MagicPlanBo saveAndUpdate(@Nonnull MagicPlanBo plan);

    MagicPlanBo findByPlanCode(@Nonnull String planCode);

    MagicPlanBo findPlan(@Nonnull Long planId);

    void deletePlan(@Nonnull Long planId);

    MagicQueryResponse<MagicPlanBo> searchPlans(@Nonnull MagicQueryRequest request);

    MagicPlanRuleBo saveAndUpdate(@Nonnull MagicPlanRuleBo rule);

    MagicPlanRuleBo findRule(@Nonnull Long ruleId);

    List<MagicPlanRuleBo> findRules(@Nonnull Long planId);

    void deleteRule(@Nonnull Long ruleId);

    MagicQueryResponse<MagicPlanRuleBo> searchRules(@Nonnull MagicQueryRequest request);

    MagicPlanFilterBo saveAndUpdate(@Nonnull MagicPlanFilterBo filter);

    MagicPlanFilterBo findFilter(@Nonnull Long filterId);

    List<MagicPlanFilterBo> findFilters(@Nonnull Long planId);

    void deleteFilter(@Nonnull Long filterId);

    MagicQueryResponse<MagicPlanFilterBo> searchFilters(@Nonnull MagicQueryRequest  request);
}
