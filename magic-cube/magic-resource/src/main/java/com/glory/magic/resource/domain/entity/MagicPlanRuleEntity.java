/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.resource.domain.entity;

import com.glory.foundation.data.jpa.converter.TypedListStringConverter;
import com.glory.foundation.data.jpa.domain.entity.DomainEntityWithDynamicFields;
import com.glory.foundation.basic.domain.BooleanEnum;
import com.glory.magic.core.domain.type.RuleType;
import jakarta.persistence.*;

import java.util.List;

/**
 * @author YY
 * @date 2026/9/22
 * @description: TODO
 */
@Entity
@Table(name = "T_MAGIC_PLAN_RULE")
public class MagicPlanRuleEntity extends DomainEntityWithDynamicFields {
    @Id
    @Column(name = "LIST_ID", nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listId;// primary key
    @Column(name = "PLAN_ID", nullable = false)
    private Long planId;//plan id
    @Column(name = "NAME", nullable = false)
    private String name;// rule name
    @Column(name = "ALIAS")
    private String alias;// rule alias name,if not null,overwrite name
    @Column(name = "INPUT", nullable = false)
    private String input;//the node path of get value  from source
    @Column(name = "OUTPUT", nullable = false)
    private String output;//the node path of set value to target
    @Column(name = "FORMULA")
    private String formula;//the formula when output value need to calculate
    @Column(name = "FORMAT")
    private String format;//the output value format,eg:yyyy-MM-dd HH:mm:ss
    @Column(name = "SCALA")
    private int scala =0;//the output value scala
    @Column(name = "TYPE", nullable = false)
    @Convert(converter = RuleType.RuleTypeConverter.class)
    private RuleType type = RuleType.NORMAL;//rule type,default is normal
    @Column(name = "DESCRIPTION")
    private String description;//rule description
    @Column(name = "STRATEGY", nullable = false)
    private String strategy;//strategy name
    @Column(name = "VALUE")
    private String value;//output default value if strategy need
    @Column(name = "PRIORITY", nullable = false)
    private int priority = 10;//rule priority for sort
    @Convert(converter = TypedListStringConverter.class)
    @Column(name = "EXCLUDE_NODES")//when last digit fuzzy matching ,effective
    private List<String> excludeNodes;
    @Column(name = "STEP_STRATEGY")//the same magicNode need to execute others strategy
    @Convert(converter = TypedListStringConverter.class)
    private List<String> stepStrategy;
    @Column(name = "CACHE_EVENT_KEY")
    private String cacheEventKey;
    @Column(name = "TENANT")
    private String tenant;// tenant
    @Column(name = "ENABLED", nullable = false)
    @Convert(converter = BooleanEnum.BooleanEnumConverter.class)
    private BooleanEnum enabled = BooleanEnum.True;// rule enabled

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getScala() {
        return scala;
    }

    public void setScala(int scala) {
        this.scala = scala;
    }

    public RuleType getType() {
        return type;
    }

    public void setType(RuleType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<String> getExcludeNodes() {
        return excludeNodes;
    }

    public void setExcludeNodes(List<String> excludeNodes) {
        this.excludeNodes = excludeNodes;
    }

    public List<String> getStepStrategy() {
        return stepStrategy;
    }

    public void setStepStrategy(List<String> stepStrategy) {
        this.stepStrategy = stepStrategy;
    }

    public String getCacheEventKey() {
        return cacheEventKey;
    }

    public void setCacheEventKey(String cacheEventKey) {
        this.cacheEventKey = cacheEventKey;
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
}
