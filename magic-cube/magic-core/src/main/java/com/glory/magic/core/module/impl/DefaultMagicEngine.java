/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.impl;

import com.glory.magic.core.domain.context.MagicContext;
import com.glory.magic.core.domain.magic.MagicRequest;
import com.glory.magic.core.domain.magic.MagicResponse;
import com.glory.magic.core.domain.node.MagicNode;
import com.glory.magic.core.domain.node.ResultKeyNameStrategy;
import com.glory.magic.core.domain.plan.MagicPlanBo;
import com.glory.magic.core.domain.plan.MagicPlanRuleBo;
import com.glory.magic.core.exception.MagicException;
import com.glory.magic.core.helper.MagicExplorer;
import com.glory.magic.core.helper.MagicHelper;
import com.glory.magic.core.module.component.engine.MagicEngine;
import com.glory.magic.core.module.delegate.exception.ExceptionHandlerDelegate;
import com.glory.magic.core.module.delegate.processor.MagicProcessorDelegate;
import com.glory.magic.core.service.resources.MagicResourceService;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author YY
 * @date 2026/9/15
 * @description: TODO
 */
public class DefaultMagicEngine implements MagicEngine {
    private final Logger logger = LoggerFactory.getLogger(DefaultMagicEngine.class);
    private Executor executor;
    private MagicResourceService resourceService;
    private MagicProcessorDelegate magicProcessorDelegate;
    private ExceptionHandlerDelegate exceptionHandlerDelegate;
    private MagicExplorer magicExplorer;

    @Override
    public <T,R> MagicResponse<R> execute(@Nonnull MagicRequest<T> request) {
        MagicPlanBo plan = request.getPlan();
        if (null == plan){//use request plan if it's plan not null
            String planCode = request.getPlanCode();
            Assert.hasLength(planCode, "PlanCode is empty.");
            plan = resourceService.findPlan(planCode);
            Assert.notNull(plan, String.format("Plan [%s] is not found.", planCode));
        }
        MagicContext context = new MagicContext();
        context.setPlan(plan);
        context.initRequest(request);
        MagicResponse<R> response = new MagicResponse<>();
        response.setTenant(request.getTenant());
        response.setPlanCode(plan.getPlanCode());
        //normal rule
        Consumer<MagicNode> consumer = node -> {
            logger.trace(">> execute node: {}", node.getPath());
            doProcess(context.copyNode(node));
        };
        MagicNode rootNode = MagicHelper.traverse(request.getData(), consumer);
        //dynamic rule
        BiConsumer<MagicNode, MagicPlanRuleBo> dynamicConsumer = (node, rule) -> {
            doProcess(context.copyDynamicRule(rule,node));
        };
        plan.processDynamicRules(rootNode, dynamicConsumer);
        response.setResultMapData(context.getResult().transToMap(getKeyNameStrategy(request,plan)));//result to map
        return response;
    }

    private ResultKeyNameStrategy<String> getKeyNameStrategy(MagicRequest<?> request, MagicPlanBo planBo){
        String keyNameStrategy = request.getKeyNameStrategy();//request strategy first
        if (null == keyNameStrategy && null != planBo.getKeyNameStrategy()){//planBo strategy second
            keyNameStrategy = planBo.getKeyNameStrategy();
        }
        ResultKeyNameStrategy<String> nameStrategy =null;
        if (null != keyNameStrategy){// default
            nameStrategy=magicExplorer.getKeyNameStrategy(keyNameStrategy);
        }
        return null==nameStrategy? ResultKeyNameStrategy.normalCase:nameStrategy;
    }

    private void doProcess(@Nonnull MagicContext context){
        try {
            magicProcessorDelegate.process(context);
        } catch (Exception e) {
            exceptionHandlerDelegate.handler(new MagicException(e).setStrategy(context.getExceptionStrategy()));
        }
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }

    @Resource(name = "magicExecutor")
    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    @Resource
    public void setResourceService(MagicResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @Resource
    public void setMagicProcessorDelegate(MagicProcessorDelegate magicProcessorDelegate) {
        this.magicProcessorDelegate = magicProcessorDelegate;
    }

    @Resource
    public void setExceptionHandlerDelegate(ExceptionHandlerDelegate exceptionHandlerDelegate) {
        this.exceptionHandlerDelegate = exceptionHandlerDelegate;
    }

    @Resource
    public void setMagicExplorer(MagicExplorer magicExplorer) {
        this.magicExplorer = magicExplorer;
    }
}
