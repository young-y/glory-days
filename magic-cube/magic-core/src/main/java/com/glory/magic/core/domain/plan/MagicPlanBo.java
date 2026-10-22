/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.domain.plan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glory.foundation.data.jpa.domain.bo.DomainBoWithDynamicFields;
import com.glory.foundation.basic.domain.BooleanEnum;
import com.glory.magic.core.domain.code.MagicCodeBo;
import com.glory.magic.core.domain.code.MagicCodeItemBo;
import com.glory.magic.core.domain.context.MagicContext;
import com.glory.magic.core.domain.node.MagicNode;
import com.glory.magic.core.domain.type.ExceptionStrategy;
import com.glory.magic.core.domain.type.RuleType;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Convert;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author YY
 * @date 2026/9/21
 * @description: TODO
 */
public class MagicPlanBo extends DomainBoWithDynamicFields {
    private Long listId;
    private String planCode;
    private String tenant;
    @Convert(converter = BooleanEnum.BooleanEnumConverter.class)
    private BooleanEnum enabled =  BooleanEnum.True;
    private String name;
    private String description;
    private String keyNameStrategy;
    private String exceptionStrategy;
    private List<MagicPlanFilterBo> filters;
    private List<MagicPlanRuleBo> rules;
    private List<MagicCodeBo> codes;
    {
        ignoredProperties.add("filters");
        ignoredProperties.add("rules");
        ignoredProperties.add("codes");
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyNameStrategy() {
        return keyNameStrategy;
    }

    public void setKeyNameStrategy(String keyNameStrategy) {
        this.keyNameStrategy = keyNameStrategy;
    }

    public String getExceptionStrategy() {
        return StringUtils.hasLength(exceptionStrategy)?exceptionStrategy:ExceptionStrategy.Panic.getName();
    }

    public void setExceptionStrategy(String exceptionStrategy) {
        this.exceptionStrategy = exceptionStrategy;
    }

    public List<MagicPlanFilterBo> getFilters() {
        return filters;
    }

    public void setFilters(List<MagicPlanFilterBo> filters) {
        this.filters = filters;
    }

    public List<MagicPlanRuleBo> getRules() {
        return rules;
    }

    public void setRules(List<MagicPlanRuleBo> rules) {
        this.rules = rules;
    }

    public List<MagicCodeBo> getCodes() {
        return codes;
    }

    public void setCodes(List<MagicCodeBo> codes) {
        this.codes = codes;
    }

    @JsonIgnore
    public void addRule(@Nonnull MagicPlanRuleBo rule){
        if (null == this.rules){
            this.rules = new ArrayList<>();
        }
        this.rules.add(rule);
    }
    @JsonIgnore
    public void addFilter(@Nonnull MagicPlanFilterBo filter){
        if (null == this.filters){
            this.filters = new ArrayList<>();
        }
        this.filters.add(filter);
    }

    @JsonIgnore
    public void addCode(@Nonnull MagicCodeBo code){
        if (null == this.codes){
            this.codes = new ArrayList<>();
        }
        this.codes.add(code);
    }
    @Override
    public Long getPrimaryId() {
        return listId;
    }

    @JsonIgnore
    public boolean exclude(@Nonnull MagicNode node){
        if (!CollectionUtils.isEmpty( filters)){
            return filters.stream().anyMatch(f->f.match(node.getPath()));
        }
        return false;
    }

    @JsonIgnore
    public List<MagicPlanRuleBo> findTypeRules(@Nonnull MagicNode node, @Nonnull RuleType ruleType){
        Assert.notEmpty(rules,String.format("The rules of %s is empty!",getPlanCode()));
        return rules
                .stream()
                .filter(r->r.getEnabled().isTrue()&&r.isType(ruleType) &&r.matchInput(node.getPath()))
                .sorted(Comparator.comparing(MagicPlanRuleBo::getPriority))
                .toList();
    }

    @JsonIgnore
    public MagicCodeItemBo findCodeItem(@Nonnull String codeName, @Nonnull String srcValue,String srcValue1){
        if (!CollectionUtils.isEmpty(codes)){
            MagicCodeBo codeBo = codes.stream()
                    .filter(c->c.getEnabled().isTrue() && c.getName().equals(codeName))
                    .findFirst().orElse(null);
            if (codeBo != null){
                return codeBo.findItem(srcValue,srcValue1);
            }
        }
        return null;

    }

    @JsonIgnore
    public List<MagicPlanRuleBo> findRulesByType(@Nonnull RuleType ruleType){
        Assert.notEmpty(rules,String.format("The rules of %s is empty!",getPlanCode()));
        return rules.stream().filter(r->r.getEnabled().isTrue()&&r.isType(ruleType)).toList();
    }

    @JsonIgnore
    public void processDynamicRules(@Nonnull MagicNode root, @Nonnull BiConsumer<MagicNode,MagicPlanRuleBo> dynamicConsumer){
        findRulesByType(RuleType.DYNAMIC).forEach(rule->{
            root.findNodes(rule.getInput()).forEach(node->{
                dynamicConsumer.accept(node,rule);
            });
        });
    }

    @JsonIgnore
    public void processGlobalRules(@Nonnull MagicContext context, @Nonnull Consumer<MagicContext> consumer) {
        findRulesByType(RuleType.GLOBAL).forEach(rule->{
            MagicContext ruleContext = context.copyRule(rule);
            consumer.accept(ruleContext);
        });
    }
}
