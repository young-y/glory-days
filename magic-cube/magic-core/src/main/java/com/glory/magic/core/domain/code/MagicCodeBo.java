/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.domain.code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glory.foundation.data.model.domain.bo.DomainBoWithDynamicFields;
import com.glory.foundation.basic.domain.BooleanEnum;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Convert;

import java.util.List;

/**
 * @author YY
 * @date 2026/9/21
 * @description: TODO
 */
public class MagicCodeBo extends DomainBoWithDynamicFields {
    private Long listId;
    private Long planId;
    private String name;
    private String description;
    @Convert(converter = BooleanEnum.BooleanEnumConverter.class)
    private BooleanEnum enabled = BooleanEnum.True;
    private String tenant;
    private List<MagicCodeItemBo> items;
    {
        ignoredProperties.add("items");
    }
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

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public List<MagicCodeItemBo> getItems() {
        return items;
    }

    public void setItems(List<MagicCodeItemBo> items) {
        this.items = items;
    }

    @Override
    public Long getPrimaryId() {
        return listId;
    }

    /**
     * @param srcValue
     * @return
     */
    @JsonIgnore
    public MagicCodeItemBo findItem(@Nonnull String srcValue) {
        if (items != null){
            return items.stream().filter(item -> item.getSrcValue().equals(srcValue)).findFirst().orElse(null);
        }
        return null;
    }
    @JsonIgnore
    public MagicCodeItemBo findItem(@Nonnull String srcValue, @Nonnull String srcValue1) {
        if (items != null){
            return items.stream().filter(item
                    ->item.getEnabled().isTrue()
                    && item.getSrcValue().equals(srcValue)
                    && item.getSrcValue1().equals(srcValue1)).findFirst().orElse(null);
        }
        return null;
    }
}
