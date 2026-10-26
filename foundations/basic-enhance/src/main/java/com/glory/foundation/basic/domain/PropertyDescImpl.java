/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author YY
 * @date 2026/7/13
 * @description: TODO
 */
public class PropertyDescImpl implements PropertyDesc {
//    @WithType
    protected Map<String, Object> properties = new HashMap<>(8);

    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        Optional.ofNullable(properties).ifPresent(this.properties::putAll);
    }

    @JsonAnySetter
    public void setProperty(String key, Object value) {
        if (!properties.containsKey( key)){
            properties.put(key, value);
        }
    }

    @JsonIgnore
    @Override
    public Object getValue(String key) {
        return this.properties.get(key);
    }
}
