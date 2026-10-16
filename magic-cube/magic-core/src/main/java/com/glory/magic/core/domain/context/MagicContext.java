/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.domain.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glory.foundation.basic.context.AppContext;
import com.glory.foundation.delegate.domain.DelegateContext;
import com.glory.magic.core.domain.magic.MagicRequest;
import com.glory.magic.core.domain.magic.MagicResponse;
import com.glory.magic.core.domain.node.MagicNode;
import com.glory.magic.core.domain.node.ResultKey;
import com.glory.magic.core.domain.node.ResultNode;
import com.glory.magic.core.domain.node.ResultSet;
import com.glory.magic.core.domain.plan.*;
import com.glory.magic.core.domain.type.ExceptionStrategy;
import com.glory.magic.core.domain.type.RuleType;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Convert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author YY
 * @date 2026/6/15
 * @description: TODO
 */
public class MagicContext extends DelegateContext  {

    private String planCode;
    private MagicNode node;
    private MagicPlanBo plan;
    private MagicPlanRuleBo rule;
    private ResultSet result = new ResultSet();
    private String tenant;
    private MagicPlanRuleBo defaultRule;
    @Convert(converter = ExceptionStrategy.ExceptionStrategyConverter.class)
    private ExceptionStrategy strategy = ExceptionStrategy.Panic;


    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public MagicNode getNode() {
        return node;
    }

    public void setNode(MagicNode node) {
        this.node = node;
    }

    public MagicPlanBo getPlan() {
        return plan;
    }

    public void setPlan(MagicPlanBo plan) {
        this.plan = plan;
    }

    public MagicPlanRuleBo getRule() {
        return rule;
    }

    public void setRule(MagicPlanRuleBo rule) {
        this.rule = rule;
    }

    public MagicPlanRuleBo getDefaultRule() {
        return defaultRule;
    }

    public void setDefaultRule(MagicPlanRuleBo defaultRule) {
        this.defaultRule = defaultRule;
    }

    public ResultSet getResult() {
        if (result == null){
            result = new ResultSet();
            result.setPlan(plan);
        }
        return result;
    }

    public void setResult(ResultSet result) {
        this.result = result;
    }

    public <T,R> void initRequest(@Nonnull MagicRequest<T> request){
        setPlanCode(request.getPlanCode());
        setProperties(request.getProperties());
        setTenant(request.getTenant());
        setDefaultRule(request.getDefaultRule());
        Optional.ofNullable(request.getStrategy()).ifPresent(this::setStrategy);
    }
    public String getTenant() {
        return null==tenant? AppContext.getTenantCode():tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public ExceptionStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(ExceptionStrategy strategy) {
        this.strategy = strategy;
    }

    @JsonIgnore
    public MagicContext copy(){
        MagicContext context = new MagicContext();
        context.setPlan(this.plan);
        context.setPlanCode(this.planCode);
        context.setNode(this.node);
        context.setRule(this.rule);
        context.setResult(this.result);
        context.setProperties(this.properties);
        context.setTenant(this.tenant);
        context.setDefaultRule(this.defaultRule);
        context.setStrategy(this.strategy);
        return context;
    }
    @JsonIgnore
    public MagicContext copyNode(MagicNode  node){
        MagicContext context = copy();
       context.setNode(node);
        return context;
    }

    @JsonIgnore
    public MagicContext copyRule(MagicPlanRuleBo rule){
        MagicContext context = copy();
        context.setRule(rule);
        return context;
    }
    @JsonIgnore
    public MagicContext copyDynamicRule(MagicPlanRuleBo rule,MagicNode node){
        MagicContext context = copy();
        context.setRule(rule.copyDynamic( node));
        context.setNode(node);
        return context;
    }

    @JsonIgnore
    public List<MagicPlanRuleBo> findTypeRules(@Nonnull RuleType type){
        List<MagicPlanRuleBo> typeRules = plan.findTypeRules(node, type);
        if (CollectionUtils.isEmpty(typeRules) && null != defaultRule){
            return List.of(defaultRule);
        }
        return typeRules;
    }

    @JsonIgnore
    public ResultNode createResultNode(){
        return result.addResultNode(ResultKey.create(rule.getOutput()));
    }

    @Deprecated
    public static MagicContext create(MagicNode node){
        MagicContext context = new MagicContext();
        context.setNode(node);
        return context;
    }

    public static MagicContext create(MagicPlanBo plan){
        MagicContext context = new MagicContext();
        context.setPlan(plan);
        return context;
    }

}
