/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.desensitize.rule;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author : YY
 * @date : 2025/12/24
 * @descprition :
 *
 */
@Component
@ConfigurationProperties(prefix = "glory.desensitize")
public class DesensitizeRuleConfig {
	private List<DesensitizeRule> rules;
	private List<DesensitizeRule> sortedRules;

	public List<DesensitizeRule> getRules() {
		return rules;
	}

	public void setRules(List<DesensitizeRule> rules) {
		enroll(rules);
	}

	@JsonIgnore
	public boolean include(@Nonnull String name){
		return CollectionUtils.isEmpty(rules)||rules.stream().allMatch(r->r.include(name));
	}

	@JsonIgnore
	public boolean match(@Nonnull String name){
		return !CollectionUtils.isEmpty(rules) && getSortedRules().stream().anyMatch(r->r.match(name));
	}

	@JsonIgnore
	public DesensitizeRule find(@Nonnull String name){
		if (!CollectionUtils.isEmpty(rules)){
			return getSortedRules().stream().filter(r->r.match(name)).findFirst().orElse(null);
		}
		return null;
	}

	/**
	 * sorte by Priority
	 * @return List
	 */
	private List<DesensitizeRule> getSortedRules(){
		if (null== sortedRules && null != rules){
			sortedRules = rules.stream().filter(DesensitizeRule::effective).sorted(Comparator.comparingInt(DesensitizeRule::getPriority)).toList();
		}
		return sortedRules;
	}

	@JsonIgnore
	public void enroll(@Nonnull DesensitizeRule rule){
		if (null == rules){
			rules = new ArrayList<>();
		}
		rules.add(rule);
		sortedRules = null;
	}

	@JsonIgnore
	public void enroll(@Nonnull List<DesensitizeRule> rules){
		if (null == this.rules){
			this.rules = new ArrayList<>();
		}
		this.rules.addAll(rules);
		sortedRules = null;
	}
}
