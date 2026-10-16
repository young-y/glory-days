/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.resource.rest;

import com.glory.foundation.basic.domain.GloryResponse;
import com.glory.foundation.basic.formater.FormatHelper;
import com.glory.magic.core.domain.plan.MagicPlanBo;
import com.glory.magic.core.domain.plan.MagicPlanFilterBo;
import com.glory.magic.core.domain.plan.MagicPlanRuleBo;
import com.glory.magic.resource.domain.bo.MagicQueryRequest;
import com.glory.magic.resource.domain.bo.MagicQueryResponse;
import com.glory.magic.resource.service.MagicPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * @author YY
 * @date 2026/9/21
 * @description: TODO
 */
@RestController
@RequestMapping("/plan")
public class MagicPlanRestful {

    @Autowired
    private MagicPlanService planService;
    @GetMapping("/find/{planCode}")
    public GloryResponse<MagicPlanBo> findByPlanCode(@PathVariable("planCode") String planCode){
        return GloryResponse.Success(planService.findByPlanCode(planCode));
    }

    @GetMapping("/{planId}")
    public GloryResponse<MagicPlanBo> findPlan(@PathVariable("planId") Long planId){
        return GloryResponse.Success(planService.findPlan(planId));
    }

    @PostMapping("/save")
    public GloryResponse<MagicPlanBo> saveAndUpdate(@RequestBody MagicPlanBo plan){
        return GloryResponse.Success(planService.saveAndUpdate(plan));
    }

    @PostMapping("/delete/{planId}")
    public GloryResponse<?> deletePlan(@PathVariable("planId") Long planId){
        planService.deletePlan(planId);
        return GloryResponse.Success();
    }

    /**
     * search plans
     * @param request {@link MagicQueryRequest}
     * @return {@link MagicQueryResponse}
     * @description:
     * planId:planId
     * planCode:planCode
     * name: plan name,like
     * enabled: 0 disabled or 1 enabled
     * tenant: tenantCode
     */
    @PostMapping("/search")
    public MagicQueryResponse<MagicPlanBo> searchPlans(@RequestBody MagicQueryRequest request){
        return planService.searchPlans(request);
    }

    @GetMapping("/rule/{ruleId}")
    public GloryResponse<MagicPlanRuleBo> findRule(@PathVariable("ruleId") Long ruleId){
        return GloryResponse.Success(planService.findRule(ruleId));
    }

    @GetMapping("/rule/find/{planId}")
    public GloryResponse<MagicPlanRuleBo> findRules(@PathVariable("planId") Long planId){
        return GloryResponse.Success(planService.findRules(planId));
    }


    @PostMapping("/rule/save")
    public GloryResponse<MagicPlanRuleBo> saveAndUpdate(@RequestBody MagicPlanRuleBo rule){
        return GloryResponse.Success(planService.saveAndUpdate(rule));
    }

    @PostMapping("/rule/delete/{ruleId}")
    public GloryResponse<?> deleteRule(@PathVariable("ruleId") Long ruleId){
        planService.deleteRule(ruleId);
        return GloryResponse.Success();
    }

    /**
     * search rules
     * @param request {@link MagicQueryRequest}
     * @return {@link MagicQueryResponse}
     * @description:
     * planId:planId
     * planCode:planCode
     * strategy: rule  strategy
     * ruleType: {@link com.glory.magic.core.domain.type.RuleType} 0:normal or 1:dynamic
     * enabled: 0 disabled or 1 enabled
     * tenant: tenantCode
     */
    @PostMapping("/rule/search")
    public MagicQueryResponse<MagicPlanRuleBo> searchRules(@RequestBody MagicQueryRequest request){
        return planService.searchRules(request);
    }

    @GetMapping("/filter/{filterId}")
    public GloryResponse<MagicPlanFilterBo> findFilter(@PathVariable("filterId") Long filterId){
        return GloryResponse.Success(planService.findFilter(filterId));
    }

    @GetMapping("/filter/find/{planId}")
    public GloryResponse<MagicPlanFilterBo> findFilters(@PathVariable("planId") Long planId){
        return GloryResponse.Success(planService.findFilters(planId));
    }

    @PostMapping("/filter/save")
    public GloryResponse<MagicPlanFilterBo> saveAndUpdate(@RequestBody MagicPlanFilterBo filter){
        return GloryResponse.Success(planService.saveAndUpdate(filter));
    }

    @PostMapping("/filter/delete/{filterId}")
    public GloryResponse<?> deleteFilter(@PathVariable("filterId") Long filterId){
        planService.deleteFilter(filterId);
        return GloryResponse.Success();
    }

    /**
     * search filters
     * @param request {@link MagicQueryRequest}
     * @return {@link MagicQueryResponse}
     * @description:
     * planId:planId
     * planCode:planCode
     * name: filter name,like
     * enabled: 0 disabled or 1 enabled
     * tenant: tenantCode
     */
    @PostMapping("/filter/search")
    public MagicQueryResponse<MagicPlanFilterBo> searchFilters(@RequestBody MagicQueryRequest  request){
        return planService.searchFilters(request);
    }

    @PostMapping("/upload")
    public GloryResponse<MagicPlanBo> uploadMultiPlans(@RequestParam("files") MultipartFile[] files){
        GloryResponse<MagicPlanBo> response = GloryResponse.Success();
        Optional.ofNullable(files).ifPresent(fs->{
            for (MultipartFile file : fs) {
                MagicPlanBo magicPlanBo = FormatHelper.fromResource(file.getResource(), MagicPlanBo.class);
                response.addElement(planService.saveAndUpdate(magicPlanBo));
            }
        });
        return response;
    }
}
