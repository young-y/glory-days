/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.resource.domain.entity;

import com.glory.foundation.data.model.converter.TypedListStringConverter;
import com.glory.foundation.data.model.domain.entity.DomainEntityWithDynamicFields;
import com.glory.foundation.basic.domain.BooleanEnum;
import jakarta.persistence.*;

import java.util.List;

/**
 * @author YY
 * @date 2026/9/22
 * @description: TODO
 */
@Entity
@Table(name = "T_MAGIC_PLAN_FILTER")
public class MagicPlanFilterEntity extends DomainEntityWithDynamicFields {
    @Id
    @Column(name = "LIST_ID", nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listId;
    @Column(name = "PLAN_ID",nullable = false)
    private Long planId;
    @Column(name = "TENANT")
    private String tenant;
    @Column(name = "NAME",nullable = false)
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "ENABLED",nullable = false)
    @Convert(converter = BooleanEnum.BooleanEnumConverter.class)
    private BooleanEnum enabled = BooleanEnum.True;
    @Column(name = "PATTERNS")
    @Convert(converter = TypedListStringConverter.class)
    private List<String> patterns;

    public List<String> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<String> patterns) {
        this.patterns = patterns;
    }

    public BooleanEnum getEnabled() {
        return enabled;
    }

    public void setEnabled(BooleanEnum enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }
}
