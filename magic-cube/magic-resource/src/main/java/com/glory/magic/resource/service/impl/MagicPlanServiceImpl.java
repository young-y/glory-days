/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.resource.service.impl;

import com.glory.foundation.basic.context.AppContext;
import com.glory.foundation.basic.domain.BooleanEnum;
import com.glory.foundation.data.jpa.domain.converter.DomainFactory;
import com.glory.magic.core.domain.plan.MagicPlanBo;
import com.glory.magic.core.domain.plan.MagicPlanFilterBo;
import com.glory.magic.core.domain.plan.MagicPlanRuleBo;
import com.glory.magic.resource.domain.bo.MagicQueryRequest;
import com.glory.magic.resource.domain.bo.MagicQueryResponse;
import com.glory.magic.resource.domain.entity.MagicPlanEntity;
import com.glory.magic.resource.domain.entity.MagicPlanFilterEntity;
import com.glory.magic.resource.domain.entity.MagicPlanRuleEntity;
import com.glory.magic.resource.repository.MagicPlanFilterRepository;
import com.glory.magic.resource.repository.MagicPlanRepository;
import com.glory.magic.resource.repository.MagicPlanRuleRepository;
import com.glory.magic.resource.service.MagicCodeService;
import com.glory.magic.resource.service.MagicPlanService;
import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author YY
 * @date 2026/9/23
 * @description: TODO
 */
@Service
public class MagicPlanServiceImpl implements MagicPlanService {
    @Autowired
    private MagicPlanRepository planRepository;
    @Autowired
    private MagicPlanFilterRepository planFilterRepository;
    @Autowired
    private MagicPlanRuleRepository planRuleRepository;
    @Autowired
    private MagicCodeService codeService;
    @Override
    @Cacheable(value = "magicPlan",key = "#p0",unless = "#result ==null")
    public MagicPlanBo findByPlanCode(@Nonnull String planCode) {
        MagicQueryRequest request = new MagicQueryRequest();
        request.setPlanCode(planCode);
        request.setTenant(AppContext.getTenantCode());
        MagicQueryResponse<MagicPlanBo> re = searchPlans(request);
        if (null != re && re.hasContent()){
            Optional<MagicPlanBo> first = re.getElements().stream().findFirst();
            if (first.isPresent()){
                return first.get();
            }
        }
        return null;
    }

    @Override
    @Cacheable(value = "magicPlan",key = "#p0",unless = "#result ==null")
    public MagicPlanBo findPlan(@Nonnull Long planId) {
        MagicPlanEntity entity = planRepository.findById(planId).orElse(null);
        if (null  !=entity){
            MagicPlanBo planBo = DomainFactory.convert(entity, MagicPlanBo.class);
            List<MagicPlanFilterBo> filters = planFilterRepository.findByPlanIdAndEnabled(planId, BooleanEnum.True)
                    .stream().map(e -> DomainFactory.convert(e, MagicPlanFilterBo.class)).toList();
            planBo.setFilters(filters);
            List<MagicPlanRuleBo> rules = planRuleRepository.findByPlanIdAndEnabled(planId, BooleanEnum.True)
                    .stream().map(e -> DomainFactory.convert(e, MagicPlanRuleBo.class)).toList();
            planBo.setRules(rules);
            planBo.setCodes(codeService.findCodeByPlanId(planId));
            return planBo;
        }
        return null;
    }

    @Override
    @Transactional
    @CachePut(value = "magicPlan",key = "#p0.listId", condition = "#p0.listId !=null")
    public MagicPlanBo saveAndUpdate(@Nonnull MagicPlanBo plan) {
        MagicPlanEntity entity = DomainFactory.convert(plan, MagicPlanEntity.class);
        MagicPlanEntity saveEntity = planRepository.save(entity);
        Optional.ofNullable(saveEntity.getFilters()).ifPresent(fs -> {
            fs.forEach(filter -> filter.setPlanId(saveEntity.getListId()));
            List<MagicPlanFilterEntity> saveFilters = planFilterRepository.saveAll(fs);
            saveEntity.setFilters(saveFilters);
        });
        Optional.ofNullable(saveEntity.getRules()).ifPresent(rs -> {
            rs.forEach(rule -> rule.setPlanId(saveEntity.getListId()));
            List<MagicPlanRuleEntity> saveRules = planRuleRepository.saveAll(rs);
            saveEntity.setRules(saveRules);
        });
        Optional.ofNullable(plan.getCodes()).ifPresent(cs -> {
            cs.forEach(code ->{
                code.setPlanId(saveEntity.getListId());
                codeService.saveAndUpdate(code);
            });
        });
        return DomainFactory.convert(saveEntity, MagicPlanBo.class);
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "magicPlan",key = "#p0.listId")
            ,@CacheEvict(value = "magicRule",allEntries = true)
            ,@CacheEvict(value = "magicFilter",allEntries = true)})
    public void deletePlan(@Nonnull Long planId) {
        planRuleRepository.findByPlanId(planId).forEach(rule -> planRuleRepository.delete(rule));
        planFilterRepository.findByPlanId(planId).forEach(filter -> planFilterRepository.delete(filter));
        codeService.deleteCodeByPlanId(planId);
        planRepository.deleteById(planId);
    }

    @Override
    public MagicQueryResponse<MagicPlanBo> searchPlans(@Nonnull MagicQueryRequest request) {
        MagicQueryResponse<MagicPlanBo> response = new MagicQueryResponse<>();
        Specification<MagicPlanEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new LinkedList<>();
            Optional.ofNullable(request.getPlanId()).ifPresent(s -> predicates.add(cb.equal(root.get("planId"), s)));
            Optional.ofNullable(request.getPlanCode()).ifPresent(s -> predicates.add(cb.equal(root.get("planCode"), s)));
            Optional.ofNullable(request.getName()).ifPresent(s -> predicates.add(cb.like(root.get("name"), "%" + s + "%")));
            Optional.ofNullable(request.getEnabled()).ifPresent(s -> predicates.add(cb.equal(root.get("enabled"), s)));
            Optional.ofNullable(request.getTenant()).ifPresent(tenant -> predicates.add(cb.equal(root.get("tenant"), tenant)));
            if (null != query){
                return query.where(predicates.toArray( Predicate[]::new)).getRestriction();
            }
            return cb.and(predicates.toArray( Predicate[]::new));
        };
        if (null != request.getPage()){
            response.initPage(request.getPage());
            Page<MagicPlanEntity> planEntities = planRepository.findAll(spec, request.getPage().createPageRequest());
            response.getPage().setTotalElements(planEntities.getTotalElements());
            response.getPage().setTotalPages(planEntities.getTotalPages());
            response.getPage().setNext(planEntities.hasNext());
            response.setElements(planEntities.stream().map(e -> DomainFactory.convert(e, MagicPlanBo.class)).toList());
        }else {
            List<MagicPlanEntity> planEntities = planRepository.findAll(spec);
            response.setElements(planEntities.stream().map(e -> DomainFactory.convert(e, MagicPlanBo.class)).toList());
        }
        if (request.isIncludeChild()){ //include child,rules and filters
            response.getElements().forEach(plan -> {
                if (CollectionUtils.isEmpty(plan.getRules())){
                    List<MagicPlanRuleBo> rules = findRules(plan.getListId());
                    plan.setRules(rules);
                }
                if (CollectionUtils.isEmpty(plan.getFilters())){
                    List<MagicPlanFilterBo> filters = findFilters(plan.getListId());
                    plan.setFilters(filters);
                }
            });
        }
        return response;
    }

    @Override
    @Cacheable(value = "magicRule",key = "#p0",unless = "#result ==null")
    public MagicPlanRuleBo findRule(@Nonnull Long ruleId) {
        MagicPlanRuleEntity entity = planRuleRepository.findById(ruleId).orElse(null);
        if (null  !=entity){
            return DomainFactory.convert(entity, MagicPlanRuleBo.class);
        }
        return null;
    }

    @Override
    @Cacheable(value = "magicRule",key = "'plan:'+#p0",unless = "#result ==null || #result.size() ==0")
    public List<MagicPlanRuleBo> findRules(@Nonnull Long planId) {
        List<MagicPlanRuleBo> list = new ArrayList<>();
        planRuleRepository.findByPlanId(planId).forEach(e->{
            MagicPlanRuleBo ruleBo = DomainFactory.convert(e, MagicPlanRuleBo.class);
            list.add(ruleBo);
        });
        return list;
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "magicPlan",key ="#p0.planId")
            ,@CacheEvict(value = "magicRule",key ="'plan:'+#p0.planId")},
            put = @CachePut(value = "magicRule",key = "#p0.listId", condition = "#p0.listId !=null"))
    public MagicPlanRuleBo saveAndUpdate(@Nonnull MagicPlanRuleBo rule) {
        MagicPlanRuleEntity entity = DomainFactory.convert(rule, MagicPlanRuleEntity.class);
        MagicPlanRuleEntity saveEntity = planRuleRepository.save(entity);
        return DomainFactory.convert(saveEntity, MagicPlanRuleBo.class);
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "magicRule",allEntries = true)
            ,@CacheEvict(value = "magicPlan",allEntries = true)})
    public void deleteRule(@Nonnull Long ruleId) {
        planRuleRepository.deleteById(ruleId);
    }

    @Override
    public MagicQueryResponse<MagicPlanRuleBo> searchRules(@Nonnull MagicQueryRequest request) {
        MagicQueryResponse<MagicPlanRuleBo> response = new MagicQueryResponse<>();
        Specification<MagicPlanRuleEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new LinkedList<>();
            Optional.ofNullable(request.getPlanId()).ifPresent(s -> predicates.add(cb.equal(root.get("planId"), s)));
            Optional.ofNullable(request.getStrategy()).ifPresent(s -> predicates.add(cb.equal(root.get("strategy"), s)));
            Optional.ofNullable(request.getRuleType()).ifPresent(s -> predicates.add(cb.equal(root.get("ruleType"), s.getValue())));
            Optional.ofNullable(request.getEnabled()).ifPresent(s -> predicates.add(cb.equal(root.get("enabled"), s)));
            Optional.ofNullable(request.getTenant()).ifPresent(tenant -> predicates.add(cb.equal(root.get("tenant"), tenant)));
            if (null != query){
                return query.where(predicates.toArray( Predicate[]::new)).getRestriction();
            }
            return cb.and(predicates.toArray( Predicate[]::new));
        };
        if (null != request.getPage()){//page query
            response.initPage(request.getPage());
            Page<MagicPlanRuleEntity> ruleEntities = planRuleRepository.findAll(spec, request.getPage().createPageRequest());
            response.getPage().setTotalElements(ruleEntities.getTotalElements());
            response.getPage().setTotalPages(ruleEntities.getTotalPages());
            response.getPage().setNext(ruleEntities.hasNext());
            response.setElements(ruleEntities.stream().map(e -> DomainFactory.convert(e, MagicPlanRuleBo.class)).toList());
        }else {
            List<MagicPlanRuleEntity> ruleEntities = planRuleRepository.findAll(spec);
            response.setElements(ruleEntities.stream().map(e -> DomainFactory.convert(e, MagicPlanRuleBo.class)).toList());
        }
        return response;
    }

    @Override
    @Cacheable(value = "magicFilter",key = "#p0",unless = "#result ==null")
    public MagicPlanFilterBo findFilter(@Nonnull Long filterId) {
        MagicPlanFilterEntity entity = planFilterRepository.findById(filterId).orElse(null);
        if (null  !=entity){
            return DomainFactory.convert(entity, MagicPlanFilterBo.class);
        }
        return null;
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "magicPlan",key = "#p0.planId")
    ,@CacheEvict(value = "magicFilter",key ="'plan:'+#p0.planId")}
    ,put = @CachePut(value = "magicFilter",key = "#p0.listId", condition = "#p0.listId !=null"))
    public MagicPlanFilterBo saveAndUpdate(@Nonnull MagicPlanFilterBo filter) {
        MagicPlanFilterEntity entity = DomainFactory.convert(filter, MagicPlanFilterEntity.class);
        MagicPlanFilterEntity saveEntity = planFilterRepository.save(entity);
        return DomainFactory.convert(saveEntity, MagicPlanFilterBo.class);
    }


    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "magicFilter",allEntries = true)
            ,@CacheEvict(value = "magicPlan",allEntries = true)})
    public void deleteFilter(@Nonnull Long filterId) {
        planFilterRepository.deleteById(filterId);
    }

    @Override
    public MagicQueryResponse<MagicPlanFilterBo> searchFilters(@Nonnull MagicQueryRequest request) {
        MagicQueryResponse<MagicPlanFilterBo> response = new MagicQueryResponse<>();
        Specification<MagicPlanFilterEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new LinkedList<>();
            Optional.ofNullable(request.getPlanId()).ifPresent(s -> predicates.add(cb.equal(root.get("planId"), s)));
            Optional.ofNullable(request.getName()).ifPresent(s -> predicates.add(cb.like(root.get("name"), "%" + s + "%")));
            Optional.ofNullable(request.getEnabled()).ifPresent(s -> predicates.add(cb.equal(root.get("enabled"), s)));
            Optional.ofNullable(request.getTenant()).ifPresent(tenant -> predicates.add(cb.equal(root.get("tenant"), tenant)));
            if (null != query){
                return query.where(predicates.toArray( Predicate[]::new)).getRestriction();
            }
            return cb.and(predicates.toArray( Predicate[]::new));
        };
        if (null != request.getPage()){
            response.initPage(request.getPage());
            Page<MagicPlanFilterEntity> planEntities = planFilterRepository.findAll(spec, request.getPage().createPageRequest());
            response.getPage().setTotalElements(planEntities.getTotalElements());
            response.getPage().setTotalPages(planEntities.getTotalPages());
            response.getPage().setNext(planEntities.hasNext());
            response.setElements(planEntities.stream().map(e -> DomainFactory.convert(e, MagicPlanFilterBo.class)).toList());
        }else {
            List<MagicPlanFilterEntity> planEntities = planFilterRepository.findAll(spec);
            response.setElements(planEntities.stream().map(e -> DomainFactory.convert(e, MagicPlanFilterBo.class)).toList());
        }
        return response;
    }

    @Override
    @Cacheable(value = "magicFilter",key = "'plan:'+#p0" ,unless = "#result ==null || #result.size() ==0")
    public List<MagicPlanFilterBo> findFilters(@Nonnull Long planId) {
        List<MagicPlanFilterBo> list = new ArrayList<>();
        planFilterRepository.findByPlanId(planId).forEach(e->{
            MagicPlanFilterBo filterBo = DomainFactory.convert(e, MagicPlanFilterBo.class);
            list.add(filterBo);
        });
        return list;
    }

}
