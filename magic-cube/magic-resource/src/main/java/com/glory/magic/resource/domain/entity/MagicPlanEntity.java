/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.resource.domain.entity;

import com.glory.foundation.basic.domain.BooleanEnum;
import com.glory.foundation.data.jpa.domain.entity.DomainEntityWithDynamicFields;
import jakarta.persistence.*;

import java.util.List;

/**
 * @author YY
 * @date 2026/9/22
 * @description: TODO
 */
@Entity
@Table(name = "T_MAGIC_PLAN")
public class MagicPlanEntity extends DomainEntityWithDynamicFields {
    @Id
    @Column(name = "LIST_ID", nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listId;
    @Column(name = "PLAN_CODE", nullable = false)
    private String planCode;
    @Column(name = "TENANT")
    private String tenant;
    @Column(name = "ENABLED",nullable = false)
    @Convert(converter = BooleanEnum.BooleanEnumConverter.class)
    private BooleanEnum enabled =  BooleanEnum.True;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "KEY_NAME_STRATEGY")
    private String keyNameStrategy;
    @Column(name = "EXCEPTION_STRATEGY")
    private String exceptionStrategy;
    @OneToMany
    @JoinColumn(name = "PLAN_ID")
    @Transient
    private List<MagicPlanFilterEntity> filters;
    @OneToMany
    @JoinColumn(name = "PLAN_ID")
    @Transient
    private List<MagicPlanRuleEntity> rules;
    @OneToMany
    @JoinColumn(name = "PLAN_ID")
    @Transient
    private List<MagicCodeEntity> codes;
    {
        ignoredProperties.add("filters");
        ignoredProperties.add("rules");
        ignoredProperties.add("codes");
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyNameStrategy() {
        return keyNameStrategy;
    }

    public void setKeyNameStrategy(String keyNameStrategy) {
        this.keyNameStrategy = keyNameStrategy;
    }

    public String getExceptionStrategy() {
        return exceptionStrategy;
    }

    public void setExceptionStrategy(String exceptionStrategy) {
        this.exceptionStrategy = exceptionStrategy;
    }

    public List<MagicPlanFilterEntity> getFilters() {
        return filters;
    }

    public void setFilters(List<MagicPlanFilterEntity> filters) {
        this.filters = filters;
    }

    public List<MagicPlanRuleEntity> getRules() {
        return rules;
    }

    public void setRules(List<MagicPlanRuleEntity> rules) {
        this.rules = rules;
    }

    public List<MagicCodeEntity> getCodes() {
        return codes;
    }

    public void setCodes(List<MagicCodeEntity> codes) {
        this.codes = codes;
    }
}
