/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.domain.plan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glory.foundation.basic.type.WithType;
import com.glory.foundation.data.jpa.domain.bo.DomainBoWithDynamicFields;
import com.glory.foundation.basic.domain.BooleanEnum;
import com.glory.magic.core.domain.node.MagicNode;
import com.glory.magic.core.domain.node.PathKey;
import com.glory.magic.core.domain.type.RuleType;
import com.glory.magic.core.helper.MagicHelper;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Convert;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author YY
 * @date 2026/9/21
 * @description: TODO
 */
public class MagicPlanRuleBo extends DomainBoWithDynamicFields {

    private Long listId;// primary key
    private Long planId;//plan id
    private String name;// rule name
    private String alias;// rule alias name,if not null,overwrite name
    private String input;//the node path of get value  from source
    private String output;//the node path of set value to target
    private String formula;//the formula when output value need to calculate
    private String format;//the output value format,eg:yyyy-MM-dd HH:mm:ss
    private int scala;//the output value scala
    @Convert(converter = RuleType.RuleTypeConverter.class)
    private RuleType type = RuleType.NORMAL;//rule type,default is normal
    private String description;//rule description
    private String strategy;//strategy name
    private List<String> stepStrategy;
    private String value;//output default value if strategy need
    private int priority = 10;//rule priority for sort
    private List<String> excludeNodes;//when last digit fuzzy matching ,effective
    private String tenant;// tenant
    @Convert(converter = BooleanEnum.BooleanEnumConverter.class)
    private BooleanEnum enabled = BooleanEnum.True;// rule enabled
    private String cacheEventKey;
//    @WithType
    private Map<String, Object> parameters;

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
        this.input = MagicHelper.replaceRegular(input);
    }

    public String getOutput() {
        return output;
    }

    @JsonIgnore
    public String getReallyOutput(MagicNode node){
        if (!StringUtils.hasLength(output)){
            return node.getPath();
        }
        //root node key name
        if (output.equals("\\w+")|| output.equals("*")){//Root paragraph replacement
            return node.getName();
        }
        //output can't empty,dynamic replace the last section
        if (output.endsWith("#\\w+")|| output.endsWith("#*")){//Final paragraph replacement
            String no = MagicHelper.replaceLastSection(output, node.getName());
            return replaceDynamicOutput(no,node);
        }
        String no = MagicHelper.replaceLastSection(output, alias);
        return replaceDynamicOutput(no,node);
    }

    /**
     * dynamic replace  output if it contains dynamic variable ${name}
     * @param output output
     * @param node MagicNode
     * @return  String
     */
    private String replaceDynamicOutput(String output,MagicNode node){
        return MagicHelper.dynamicRuleOutput(output,this, node.getSource());
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

    public String getCacheEventKey() {
        return cacheEventKey;
    }

    public void setCacheEventKey(String cacheEventKey) {
        this.cacheEventKey = cacheEventKey;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    public Long getPrimaryId() {
        return listId;
    }
    @JsonIgnore
    public boolean isType(@Nonnull RuleType type) {
        return this.type == type;
    }
    @JsonIgnore
    public boolean matchInput(@Nonnull String nodePath){
        Assert.hasLength(input,String.format("The input of Strategy[%s] for Plan[%s] is null!",name,getPlanId()));
        boolean exclude = false;
        if ((input.endsWith("#\\w+")|| input.endsWith("#*"))&&null !=excludeNodes){
            exclude = excludeNodes.stream().anyMatch(nodePath::endsWith);
        }
        return !exclude&&nodePath.matches( input);
    }

    @JsonIgnore
    public MagicPlanRuleBo copy(){
        MagicPlanRuleBo rule = new MagicPlanRuleBo();
        rule.setPlanId(planId);
        rule.setName(name);
        rule.setAlias(alias);
        rule.setValue(value);
        rule.setPriority(priority);
        rule.setType(type);
        rule.setDescription(description);
        rule.setInput(input);
        rule.setOutput(output);
        rule.setFormula(formula);
        rule.setFormat(format);
        rule.setScala(scala);
        rule.setEnabled(enabled);
        rule.setTenant(tenant);
        rule.setStrategy(strategy);
        rule.setListId(listId);
        rule.setExcludeNodes(excludeNodes);
        rule.setStepStrategy(stepStrategy);
        rule.setCacheEventKey(cacheEventKey);
        rule.setDynamicFields(getDynamicFields());
        return rule;
    }

    @JsonIgnore
    public MagicPlanRuleBo copyDynamic(@Nonnull MagicNode node){
        MagicPlanRuleBo rule = copy();
        rule.refreshRule(node.getName());
        return rule;
    }

    @JsonIgnore
    public boolean hasStepStrategy(){
        return null != stepStrategy&&!stepStrategy.isEmpty();
    }

    @JsonIgnore
    public MagicPlanRuleBo copyNextStrategyRule(){
        if (hasStepStrategy()){
            MagicPlanRuleBo rule = copy();
            rule.setStrategy(stepStrategy.getFirst());
            if (!stepStrategy.isEmpty()){
                rule.setStepStrategy(stepStrategy.stream().skip(1).toList());
            }else {
                rule.setStepStrategy(null);
            }
            return rule;
        }
        return null;
    }

    /**
     * refresh rule name and output
     * @param name node name
     */
    private void refreshRule(String name){
        setName( name);
        refreshOutput( name);
    }

    /**
     * refresh output path
     * @param name node name
     */
    private void refreshOutput(String name){
        if (!StringUtils.hasLength( output)){
            output = input;
        }
        output = MagicHelper.replaceLastSection( output,name);
    }

}
