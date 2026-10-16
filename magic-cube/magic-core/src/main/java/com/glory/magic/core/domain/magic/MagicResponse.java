/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.domain.magic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glory.foundation.basic.domain.GloryResponse;
import com.glory.magic.core.helper.MagicHelper;
import jakarta.annotation.Nonnull;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author YY
 * @date 2026/6/21
 * @description: TODO
 */
public class MagicResponse<R> extends GloryResponse<R> {
    private String planCode;
    private String tenant ;
    private Map<String, Object> resultMapData;

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    @JsonIgnore
    public R getSpecifiedTypeResult(@Nonnull Class<R> resultType){
        if (!CollectionUtils.isEmpty(resultMapData)){
            return MagicHelper.mapToObj(resultMapData, resultType);
        }
        return null;
    }

    public Map<String, Object> getResultMapData() {
        return resultMapData;
    }

    public void setResultMapData(Map<String, Object> resultMapData) {
        this.resultMapData = resultMapData;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getTenant() {
        return tenant;
    }
}
