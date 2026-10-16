/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.domain.magic;

import com.glory.foundation.basic.domain.GloryRequest;
import com.glory.foundation.basic.domain.PropertyDescImpl;
import com.glory.magic.core.domain.node.ResultKeyNameStrategy;
import com.glory.magic.core.domain.plan.MagicPlanBo;
import com.glory.magic.core.domain.plan.MagicPlanRuleBo;
import com.glory.magic.core.domain.type.ExceptionStrategy;

import java.lang.reflect.ParameterizedType;

/**
 * @author YY
 * @date 2026/6/20
 * @description: TODO
 */
public class MagicRequest <T> extends GloryRequest<T> {
    private String planCode;
    private MagicPlanBo plan;
    private String tenant;
    private boolean withDefaultRule= false;
    private ExceptionStrategy strategy;
    private String keyNameStrategy;

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public MagicPlanBo getPlan() {
        return plan;
    }

    public void setPlan(MagicPlanBo plan) {
        this.plan = plan;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getTenant() {
        return tenant;
    }

    public boolean isWithDefaultRule() {
        return withDefaultRule;
    }

    public void setWithDefaultRule(boolean withDefaultRule) {
        this.withDefaultRule = withDefaultRule;
    }

    public MagicPlanRuleBo getDefaultRule() {
        if (withDefaultRule){
            MagicPlanRuleBo ruleBo = new MagicPlanRuleBo();
            ruleBo.setStrategy("valueCopy");
            return ruleBo;
        }
        return null;
    }

    public ExceptionStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(ExceptionStrategy strategy) {
        this.strategy = strategy;
    }

    public String getKeyNameStrategy() {
        return keyNameStrategy;
    }

    public void setKeyNameStrategy(String keyNameStrategy) {
        this.keyNameStrategy = keyNameStrategy;
    }

    public static <T> MagicRequest<T> create(String planCode, T data){
        MagicRequest<T> request = new MagicRequest<>();
        request.setPlanCode(planCode);
        request.setData(data);
        return request;
    }

}
