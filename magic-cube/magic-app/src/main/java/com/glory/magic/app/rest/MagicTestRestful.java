/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.app.rest;

import com.glory.foundation.basic.domain.GloryResponse;
import com.glory.foundation.basic.formater.FormatHelper;
import com.glory.magic.app.domain.FamilyRequest;
import com.glory.magic.app.domain.family.Family;
import com.glory.magic.core.domain.magic.MagicRequest;
import com.glory.magic.core.domain.magic.MagicResponse;
import com.glory.magic.core.domain.node.MagicNode;
import com.glory.magic.core.domain.node.ResultKeyNameStrategy;
import com.glory.magic.core.helper.MagicHelper;
import com.glory.magic.core.service.MagicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author YY
 * @date 2026/9/30
 * @description: TODO
 */
@RestController
@RequestMapping("/test")
public class MagicTestRestful {
    private final Logger logger = LoggerFactory.getLogger(MagicTestRestful.class);

    @Autowired
    private MagicService service;
    @PostMapping("/family")
    public Family testFamily(@RequestBody Family family){
        logger.info(">> family:{}", family);
        return family;
    }

    @PostMapping("/traverse")
    public <T>GloryResponse<MagicNode> executeTraverse(@RequestBody MagicRequest<T> request) {
        MagicNode rootNode = MagicHelper.traverse(request.getData(), node -> {
            logger.info(">> node:{} and value :{}", node.getPath(), node.getValue());
        });
        GloryResponse<MagicNode> success = GloryResponse.Success();
        success.addElement(rootNode);
        return success;
    }

    @PostMapping("/execute")
    public <T,R> GloryResponse<R> execute(@RequestBody MagicRequest<T> request) {
//        request.setKeyNameStrategy(ResultKeyNameStrategy.upperCase);
        return service.execute(request);
    }

    @PostMapping("/execute/async")
    public <T,R> void executeAsync(@RequestBody MagicRequest<T> request) {
        service.executeAsync(request,( response)->{
            logger.info(">> async response:{}", FormatHelper.toJson(response));
        });
    }

    @Deprecated
    @PostMapping("/test/execute")
    public  <T,R> MagicResponse<R> testExecute(@RequestBody MagicRequest<T>  request) {
        return service.execute(request);
    }
}
