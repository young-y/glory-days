/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.resource.domain.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glory.foundation.basic.context.AppContext;
import com.glory.foundation.basic.domain.BooleanEnum;
import com.glory.foundation.data.model.domain.page.GloryPageRequest;
import com.glory.magic.core.domain.type.RuleType;
import jakarta.persistence.Convert;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

/**
 * @author YY
 * @date 2026/9/21
 * @description: TODO
 */
public class MagicQueryRequest extends GloryPageRequest {
    private Long listId;
    private Long planId;//rule
    private String planCode;
    @Convert(converter = RuleType.RuleTypeConverter.class)
    private RuleType ruleType;
    @Convert(converter = BooleanEnum.BooleanEnumConverter.class)
    private BooleanEnum enabled;
    private String name;
    private String strategy;//only rule query
    private String tenant;//specified tenant query only,default current tenant
    private String srcValue;
    private String srcValue1;
    private boolean includeChild = true;

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public BooleanEnum getEnabled() {
        return enabled;
    }

    public void setEnabled(BooleanEnum enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getTenant() {
        return null==tenant?AppContext.getTenantCode():tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public boolean isIncludeChild() {
        return includeChild;
    }

    public void setIncludeChild(boolean includeChild) {
        this.includeChild = includeChild;
    }

    public String getSrcValue() {
        return srcValue;
    }

    public void setSrcValue(String srcValue) {
        this.srcValue = srcValue;
    }

    public String getSrcValue1() {
        return srcValue1;
    }

    public void setSrcValue1(String srcValue1) {
        this.srcValue1 = srcValue1;
    }

    @JsonIgnore
	public PageRequest createPageRequest() {
		if (CollectionUtils.isEmpty(getPage().getSortProperties())) {
			return PageRequest.of(getPage().getNumber(), getPage().getSize());
		} else {
			return PageRequest.of(getPage().getNumber(), getPage().getSize(), getPage().isDesc() ? Sort.Direction.DESC : Sort.Direction.ASC, getPage().getSortProperties().toArray(String[]::new));
		}
	}
}
