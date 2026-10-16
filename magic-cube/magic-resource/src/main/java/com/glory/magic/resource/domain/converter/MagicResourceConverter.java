/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.resource.domain.converter;

import com.glory.foundation.basic.context.AppContext;
import com.glory.foundation.data.jpa.domain.converter.DomainConverter;
import com.glory.foundation.data.jpa.domain.converter.DomainFactory;
import com.glory.magic.core.domain.code.MagicCodeBo;
import com.glory.magic.core.domain.code.MagicCodeItemBo;
import com.glory.magic.core.domain.plan.MagicPlanFilterBo;
import com.glory.magic.core.domain.plan.MagicPlanBo;
import com.glory.magic.core.domain.plan.MagicPlanRuleBo;
import com.glory.magic.resource.domain.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

/**
 * @author YY
 * @date 2026/9/22
 * @description: TODO
 */
@Configuration
public class MagicResourceConverter {
    @Bean
    public DomainConverter<MagicCodeEntity, MagicCodeBo> codeEntityToBo(){
        return new DomainConverter<MagicCodeEntity, MagicCodeBo>() {
            @Override
            public MagicCodeBo convert(MagicCodeEntity entity) {
                MagicCodeBo bo = new MagicCodeBo();
                BeanUtils.copyProperties(entity,bo);
                Optional.ofNullable(entity.getItems()).ifPresent(items -> {
                    List<MagicCodeItemBo> list = items.stream().map(itemEntity -> DomainFactory.convert(itemEntity, MagicCodeItemBo.class)).toList();
                    bo.setItems(list);
                });
                return bo;
            }
        };
    }
    @Bean
    public DomainConverter<MagicCodeBo, MagicCodeEntity> codeBoToEntity(){
        return new DomainConverter<MagicCodeBo, MagicCodeEntity>() {
            @Override
            public MagicCodeEntity convert(MagicCodeBo bo) {
                MagicCodeEntity entity = new MagicCodeEntity();
                BeanUtils.copyProperties(bo,entity,bo.getIgnorePropertiesInSync().toArray(String[]::new));
                Optional.ofNullable(bo.getItems()).ifPresent(items -> {
                    List<MagicCodeItemEntity> list = items.stream().map(itemBo -> DomainFactory.convert(itemBo, MagicCodeItemEntity.class)).toList();
                    entity.setItems(list);
                });
                if (bo.getTenant() == null){
                    entity.setTenant(AppContext.getTenantCode());
                }
                return entity;
            }
        };
    }
    @Bean
    public DomainConverter<MagicCodeItemEntity, MagicCodeItemBo> codeItemEntityToBo(){
        return new DomainConverter<MagicCodeItemEntity, MagicCodeItemBo>() {
            @Override
            public MagicCodeItemBo convert(MagicCodeItemEntity entity) {
                MagicCodeItemBo bo = new MagicCodeItemBo();
                BeanUtils.copyProperties(entity,bo);
                return bo;
            }
        };
    }
    @Bean
    public DomainConverter<MagicCodeItemBo, MagicCodeItemEntity> codeItemBoToEntity(){
        return new DomainConverter<MagicCodeItemBo, MagicCodeItemEntity>() {
            @Override
            public MagicCodeItemEntity convert(MagicCodeItemBo bo) {
                MagicCodeItemEntity entity = new MagicCodeItemEntity();
                BeanUtils.copyProperties(bo,entity,bo.getIgnorePropertiesInSync().toArray(String[]::new));
                if (bo.getTenant() == null){
                    entity.setTenant(AppContext.getTenantCode());
                }
                return entity;
            }
        };
    }

    @Bean
    public DomainConverter<MagicPlanRuleEntity, MagicPlanRuleBo> ruleEntityToBo(){
        return new DomainConverter<MagicPlanRuleEntity, MagicPlanRuleBo>() {
            @Override
            public MagicPlanRuleBo convert(MagicPlanRuleEntity entity) {
                MagicPlanRuleBo bo = new MagicPlanRuleBo();
                BeanUtils.copyProperties(entity,bo);
                return bo;
            }
        };
    }
    @Bean
    public DomainConverter<MagicPlanRuleBo, MagicPlanRuleEntity> ruleBoToEntity(){
        return new DomainConverter<MagicPlanRuleBo, MagicPlanRuleEntity>() {
            @Override
            public MagicPlanRuleEntity convert(MagicPlanRuleBo bo) {
                MagicPlanRuleEntity entity = new MagicPlanRuleEntity();
                BeanUtils.copyProperties(bo,entity);
                if (bo.getTenant() == null){
                    entity.setTenant(AppContext.getTenantCode());
                }
                return entity;
            }
        };
    }
    @Bean
    public DomainConverter<MagicPlanEntity, MagicPlanBo> planEntityToBo(){
        return new DomainConverter<MagicPlanEntity, MagicPlanBo>() {
            @Override
            public MagicPlanBo convert(MagicPlanEntity entity) {
                MagicPlanBo bo = new MagicPlanBo();
                BeanUtils.copyProperties(entity,bo);
                Optional.ofNullable(entity.getFilters()).ifPresent(filters -> {
                    List<MagicPlanFilterBo> list = filters.stream().map(filterEntity -> DomainFactory.convert(filterEntity, MagicPlanFilterBo.class)).toList();
                    bo.setFilters(list);
                });
                Optional.ofNullable(entity.getRules()).ifPresent(rules -> {
                    List<MagicPlanRuleBo> list = rules.stream().map(ruleEntity -> DomainFactory.convert(ruleEntity, MagicPlanRuleBo.class)).toList();
                    bo.setRules(list);
                });
                Optional.ofNullable(entity.getCodes()).ifPresent(codes -> {
                    List<MagicCodeBo> list = codes.stream().map(codeEntity -> DomainFactory.convert(codeEntity, MagicCodeBo.class)).toList();
                    bo.setCodes(list);
                });
                return bo;
            }
        };
    }
    @Bean
    public DomainConverter<MagicPlanBo, MagicPlanEntity> planBoToEntity(){
        return new DomainConverter<MagicPlanBo, MagicPlanEntity>() {
            @Override
            public MagicPlanEntity convert(MagicPlanBo bo) {
                MagicPlanEntity entity = new MagicPlanEntity();
                BeanUtils.copyProperties(bo,entity,bo.getIgnorePropertiesInSync().toArray(String[]::new));
                Optional.ofNullable(bo.getFilters()).ifPresent(filters -> {
                    List<MagicPlanFilterEntity> list = filters.stream().map(filterBo -> DomainFactory.convert(filterBo, MagicPlanFilterEntity.class)).toList();
                    entity.setFilters(list);
                });
                Optional.ofNullable(bo.getRules()).ifPresent(rules -> {
                    List<MagicPlanRuleEntity> list = rules.stream().map(ruleBo -> DomainFactory.convert(ruleBo, MagicPlanRuleEntity.class)).toList();
                    entity.setRules(list);
                });
                Optional.ofNullable(bo.getCodes()).ifPresent(codes -> {
                    List<MagicCodeEntity> list = codes.stream().map(codeBo -> DomainFactory.convert(codeBo, MagicCodeEntity.class)).toList();
                    entity.setCodes(list);
                });
                if (bo.getTenant() == null){
                    entity.setTenant(AppContext.getTenantCode());
                }
                return entity;
            }
        };
    }
    @Bean
    public DomainConverter<MagicPlanFilterEntity, MagicPlanFilterBo> filterEntityToBo(){
        return new DomainConverter<MagicPlanFilterEntity, MagicPlanFilterBo>() {
            @Override
            public MagicPlanFilterBo convert(MagicPlanFilterEntity entity) {
                MagicPlanFilterBo bo = new MagicPlanFilterBo();
                BeanUtils.copyProperties(entity,bo);
                return bo;
            }
        };
    }
    @Bean
    public DomainConverter<MagicPlanFilterBo, MagicPlanFilterEntity> filterBoToEntity(){
        return new DomainConverter<MagicPlanFilterBo, MagicPlanFilterEntity>() {
            @Override
            public MagicPlanFilterEntity convert(MagicPlanFilterBo bo) {
                MagicPlanFilterEntity entity = new MagicPlanFilterEntity();
                BeanUtils.copyProperties(bo,entity,bo.getIgnorePropertiesInSync().toArray(String[]::new));
                if (bo.getTenant() == null){
                    entity.setTenant(AppContext.getTenantCode());
                }
                return entity;
            }
        };
    }
}
