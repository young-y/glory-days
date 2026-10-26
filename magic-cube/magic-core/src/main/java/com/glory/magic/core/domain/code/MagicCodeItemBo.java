/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.domain.code;

import com.glory.foundation.basic.domain.BooleanEnum;
import com.glory.foundation.data.model.domain.bo.DomainBoWithDynamicFields;
import jakarta.persistence.Convert;

/**
 * @author YY
 * @date 2026/9/21
 * @description: TODO
 */
public class MagicCodeItemBo extends DomainBoWithDynamicFields {
    private Long listId;
    private Long codeId;//the code id,codeId,srcValue,srcValue1  can confirm the code item is unique
    private String tenant;
    @Convert(converter = BooleanEnum.BooleanEnumConverter.class)
    private BooleanEnum enabled = BooleanEnum.True;
    private String name;//the second code condition,
    private String srcValue;//the first code condition value ,not nullable
    private String srcValue1="";//the second code condition value ,default '',Unique effective
    private String destValue;
    private String description;

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public Long getCodeId() {
        return codeId;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
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

    public String getDestValue() {
        return destValue;
    }

    public void setDestValue(String destValue) {
        this.destValue = destValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Long getPrimaryId() {
        return listId;
    }
}
