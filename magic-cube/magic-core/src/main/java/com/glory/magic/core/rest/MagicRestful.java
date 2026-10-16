/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.rest;

import com.glory.foundation.basic.domain.GloryResponse;
import com.glory.foundation.basic.formater.FormatHelper;
import com.glory.magic.core.domain.code.MagicCodeBo;
import com.glory.magic.core.domain.magic.MagicRequest;
import com.glory.magic.core.domain.magic.MagicResponse;
import com.glory.magic.core.domain.plan.MagicPlanBo;
import com.glory.magic.core.module.component.engine.MagicEngine;
import com.glory.magic.core.service.MagicService;
import com.glory.magic.core.service.resources.MagicResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author YY
 * @date 2026/9/21
 * @description: TODO
 */
@RestController
@RequestMapping("/core")
public class MagicRestful {

    private final Logger logger = LoggerFactory.getLogger(MagicRestful.class);

    @Autowired
    private MagicResourceService resourceService;
    @Autowired
    private MagicService service;
    @GetMapping("/viewPlan/{planCode}")
    public GloryResponse<MagicPlanBo> viewPlan(@PathVariable("planCode") String planCode) {
        GloryResponse<MagicPlanBo> success = GloryResponse.Success();
        success.addElement(resourceService.findPlan(planCode));
        return success;
    }

    @GetMapping("/viewCode/{codeName}")
    public GloryResponse<MagicCodeBo> viewCode(@PathVariable("codeName") String codeName) {
        GloryResponse<MagicCodeBo> success = GloryResponse.Success();
        success.addElement(resourceService.findCode(codeName));
        return success;
    }

    @PostMapping("/execute")
    public <T,R> GloryResponse<R>  execute(@RequestBody MagicRequest<T> request) {
        return service.execute(request);
    }

    @PostMapping("/execute/async")
    public <T,R> GloryResponse<R>  executeAsync(@RequestBody MagicRequest<T> request) {
        return service.execute(request,( response)->{
            logger.info(">>execute async callback:{}", FormatHelper.toJson(response));
        });
    }

}
