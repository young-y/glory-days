/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.domain.plan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glory.foundation.data.model.converter.TypedListStringConverter;
import com.glory.foundation.data.model.domain.bo.DomainBoWithDynamicFields;
import com.glory.foundation.basic.domain.BooleanEnum;
import com.glory.magic.core.helper.MagicHelper;
import jakarta.persistence.Convert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author YY
 * @date 2026/9/21
 * @description:
 * exclude some node in normal rules processing,priority is higher than normal rules
 */
public class MagicPlanFilterBo extends DomainBoWithDynamicFields {
    private Long listId;
    private Long planId;
    private String tenant;
    private String name;
    private String description;
    @Convert(converter = BooleanEnum.BooleanEnumConverter.class)
    private BooleanEnum enabled = BooleanEnum.True;
    @Convert(converter = TypedListStringConverter.class)
    private List<String> patterns;//exclude node path regular patterns

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

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
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

    public BooleanEnum getEnabled() {
        return enabled;
    }

    public void setEnabled(BooleanEnum enabled) {
        this.enabled = enabled;
    }

    public List<String> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<String> patterns) {
        Optional.ofNullable(patterns).ifPresent(ps -> {
            this.patterns=ps.stream().map(MagicHelper::replaceRegular).toList();
        });
    }

    @Override
    public Long getPrimaryId() {
        return listId;
    }

    @JsonIgnore
    public boolean match(String pattern) {
        if (CollectionUtils.isEmpty(patterns)) {
            return false;
        }
        return patterns.stream().anyMatch(p -> Pattern.matches(p, pattern));
    }
}
