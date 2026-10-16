/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.delegate.domain;

import jakarta.annotation.Nonnull;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.BiPredicate;

/**
 * @author YY
 * @date 2026/6/15
 * @description: TODO
 */
public class DelegateWrapper {
    private List<String> conditions;
    private String key;

    private BiPredicate<List<String>,String> matchRule =(ls,s)->{
        if (!CollectionUtils.isEmpty(conditions) && StringUtils.hasLength(key)){
            return conditions.contains(key);
        }
        return true;
    };


    public DelegateWrapper(List<String> conditions, String key) {
        this.conditions = conditions;
        this.key = key;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setMatchRule(@Nonnull BiPredicate<List<String>, String> matchRule) {
        this.matchRule = matchRule;
    }

    public boolean accept(){
        return matchRule.test(conditions,key);
    }
}
