/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.glory.foundation.delegate.domain;

import org.springframework.util.StringUtils;
import com.glory.foundation.basic.domain.PropertyDescImpl;


/**
 * @author YY
 * @date 2026/6/15
 * @description: TODO
 */
public class DelegateContext extends PropertyDescImpl {

    private String tenant;
    private String condition;

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        if (StringUtils.hasLength(tenant) && !StringUtils.hasLength(this.tenant)){
            this.tenant = tenant;
        }
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

}
