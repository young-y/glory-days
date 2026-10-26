/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.resource.service.impl;

import com.glory.foundation.basic.context.AppContext;
import com.glory.foundation.basic.domain.GloryResponse;
import com.glory.foundation.data.model.domain.converter.DomainFactory;
import com.glory.magic.core.domain.code.MagicCodeBo;
import com.glory.magic.core.domain.code.MagicCodeItemBo;
import com.glory.magic.resource.domain.bo.MagicQueryRequest;
import com.glory.magic.resource.domain.bo.MagicQueryResponse;
import com.glory.magic.resource.domain.entity.MagicCodeEntity;
import com.glory.magic.resource.domain.entity.MagicCodeItemEntity;
import com.glory.magic.resource.repository.MagicCodeItemRepository;
import com.glory.magic.resource.repository.MagicCodeRepository;
import com.glory.magic.resource.service.MagicCodeService;
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
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author YY
 * @date 2026/9/23
 * @description: TODO
 */
@Service
public class MagicCodeServiceImpl implements MagicCodeService {
    @Autowired
    private MagicCodeRepository codeRepository;
    @Autowired
    private MagicCodeItemRepository itemRepository;

    @Override
    @Transactional
    @Caching( put = {@CachePut(value = "magicCode", key = "#p0.listId", condition = "#p0.listId !=null")}
    ,evict = {@CacheEvict(value = "magicPlan",key = "#p0.planId",condition = "#p0.planId !=null")})
    public MagicCodeBo saveAndUpdate(@Nonnull MagicCodeBo bo) {
        MagicCodeEntity entity = DomainFactory.convert(bo, MagicCodeEntity.class);
        MagicCodeEntity save = codeRepository.save(entity);
        Optional.ofNullable(entity.getItems()).ifPresent(items -> {
            items.forEach(item -> item.setCodeId(save.getListId()));
            List<MagicCodeItemEntity> itemEntities = itemRepository.saveAll(items);
            save.setItems(itemEntities);
        });
        return DomainFactory.convert(save, MagicCodeBo.class);
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "magicCode",key = "#p0")
            ,@CacheEvict(value = "magicCodeItem",allEntries = true)
            ,@CacheEvict(value = "magicPlan",allEntries = true)
    })
    public void deleteCode(@Nonnull Long listId) {
        itemRepository.findByCodeId(listId).forEach(item -> itemRepository.delete(item));
        codeRepository.deleteById(listId);
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "magicCode",allEntries = true)
            ,@CacheEvict(value = "magicCodeItem",allEntries = true)})
    public void deleteCodeByPlanId(@Nonnull Long planId) {
        codeRepository.findByPlanId(planId).forEach(code -> deleteCode(code.getListId()));
    }

    @Override
    @Cacheable(value = "magicCode", key = "#p0" ,unless = "#result ==null")
    public MagicCodeBo findCode(@Nonnull Long codeId) {
        MagicCodeEntity entity = codeRepository.findById(codeId).orElse(null);
        if (entity != null) {
            entity.setItems(itemRepository.findByCodeId(entity.getListId()));
            return DomainFactory.convert(entity, MagicCodeBo.class);
        }
        return null;
    }

    @Override
    @Cacheable(value = "magicCode", key = "#p0", unless = "#result ==null")
    public MagicCodeBo findCode(@Nonnull String codeName) {
        MagicCodeEntity entity = codeRepository.findByNameAndTenant(codeName, AppContext.getTenantCode());
        if (entity != null) {
            entity.setItems(itemRepository.findByCodeId(entity.getListId()));
            return DomainFactory.convert(entity, MagicCodeBo.class);
        }
        return null;
    }

    @Override
    @Cacheable(value = "magicPlan", key = "'plan:'+#p0", unless = "#result ==null || #result.size() ==0")
    public List<MagicCodeBo> findCodeByPlanId(@Nonnull Long planId) {
        List<MagicCodeEntity> planEntities = codeRepository.findByPlanId(planId);
        if (!CollectionUtils.isEmpty(planEntities)) {
            return planEntities.stream().map(entity -> {
                entity.setItems(itemRepository.findByCodeId(entity.getListId()));
                return DomainFactory.convert(entity, MagicCodeBo.class);
            }).toList();
        }
        return null;
    }

    @Override
    public MagicQueryResponse<MagicCodeBo> codeQuery(@Nonnull MagicQueryRequest request) {
        MagicQueryResponse<MagicCodeBo> response = new MagicQueryResponse<>();
        Specification<MagicCodeEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new LinkedList<>();
            Optional.ofNullable(request.getListId()).ifPresent(s -> predicates.add(cb.equal(root.get("listId"), s)));
            Optional.ofNullable(request.getPlanId()).ifPresent(s -> predicates.add(cb.equal(root.get("planId"), s)));
            Optional.ofNullable(request.getName()).ifPresent(s -> predicates.add(cb.like(root.get("name"), "%"+s+"%")));
            Optional.ofNullable(request.getEnabled()).ifPresent(s -> predicates.add(cb.equal(root.get("enabled"), s)));
            Optional.ofNullable(request.getTenant()).ifPresent(s -> predicates.add(cb.equal(root.get("tenant"), s)));

            if (null != query){
                return query.where(predicates.toArray( Predicate[]::new)).getRestriction();
            }
            return cb.and(predicates.toArray( Predicate[]::new));
        };
        if (request.getPage() != null){
            response.initPage(request.getPage());
            Page<MagicCodeEntity> p = codeRepository.findAll(spec, request.createPageRequest());
            response.getPage().setTotalElements(p.getTotalElements());
            response.getPage().setTotalPages(p.getTotalPages());
            response.getPage().setNext(p.hasNext());
            response.setElements(p.stream().map(entity -> DomainFactory.convert(entity, MagicCodeBo.class)).toList());
        }else {
            List<MagicCodeEntity> e = codeRepository.findAll(spec);
            response.setElements(e.stream().map(entity -> DomainFactory.convert(entity, MagicCodeBo.class)).toList());
        }
        if (request.isIncludeChild()){
            response.getElements().forEach(code -> {
                if (CollectionUtils.isEmpty(code.getItems())){
                   code.setItems(findCodeItemByCodeId(code.getListId()));
                }
            });
        }
        return response;
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "magicCode",key ="#p0.codeId")
             ,@CacheEvict(value = "magicPlan",allEntries = true)}
            , put = {@CachePut(value = "magicCodeItem", key = "#p0.listId", condition = "#p0.listId !=null")})
    public MagicCodeItemBo saveAndUpdate(@Nonnull MagicCodeItemBo bo) {
        MagicCodeItemEntity entity = DomainFactory.convert(bo, MagicCodeItemEntity.class);
        MagicCodeItemEntity save = itemRepository.save(entity);
        return DomainFactory.convert(save, MagicCodeItemBo.class);
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "magicCodeItem",key ="#p0")
            ,@CacheEvict(value = "magicCode",allEntries = true)
            ,@CacheEvict(value = "magicPlan",allEntries = true)} )
    public void deleteCodeItem(@Nonnull Long listId) {
        itemRepository.deleteById(listId);
    }

    @Override
    @Cacheable(value = "magicCodeItem", key = "#p0")
    public MagicCodeItemBo findCodeItem(@Nonnull Long codeItemId) {
        MagicCodeItemEntity entity = itemRepository.findById(codeItemId).orElse(null);
        if (entity != null) {
            return DomainFactory.convert(entity, MagicCodeItemBo.class);
        }
        return null;
    }

    @Override
    @Cacheable(value = "magicCodeItem", key = "#p0+':'+#p1+':'+#p3", unless = "#result ==null")
    public MagicCodeItemBo findCodeItem(@Nonnull String codeName, @Nonnull String itemValue, String itemValue1) {
        MagicCodeBo code = findCode(codeName);
        if (code != null){
            if (StringUtils.hasLength(itemValue1)){
                return code.findItem(itemValue, itemValue1);
            }
            return code.findItem(itemValue);
        }
        return null;
    }

    @Override
    public MagicQueryResponse<MagicCodeItemBo> codeItemQuery(@Nonnull MagicQueryRequest request) {
        MagicQueryResponse<MagicCodeItemBo> response = new MagicQueryResponse<>();
        Specification<MagicCodeItemEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new LinkedList<>();
            Optional.ofNullable(request.getListId()).ifPresent(s -> predicates.add(cb.equal(root.get("codeId"), s)));
            Optional.ofNullable(request.getName()).ifPresent(s -> predicates.add(cb.equal(root.get("name"), s)));
            Optional.ofNullable(request.getSrcValue()).ifPresent(s -> predicates.add(cb.equal(root.get("srcValue"), s)));
            Optional.ofNullable(request.getSrcValue1()).ifPresent(s -> predicates.add(cb.equal(root.get("srcValue1"), s)));
            Optional.ofNullable(request.getTenant()).ifPresent(s -> predicates.add(cb.equal(root.get("tenant"), s)));
            Optional.ofNullable(request.getEnabled()).ifPresent(s -> predicates.add(cb.equal(root.get("enabled"), s)));
            if (null != query){
                return query.where(predicates.toArray( Predicate[]::new)).getRestriction();
            }
            return cb.and(predicates.toArray( Predicate[]::new));
        };
        if (request.getPage() != null){
            response.initPage(request.getPage());
            Page<MagicCodeItemEntity> p = itemRepository.findAll(spec, request.createPageRequest());
            response.getPage().setTotalElements(p.getTotalElements());
            response.getPage().setTotalPages(p.getTotalPages());
            response.getPage().setNext(p.hasNext());
            response.setElements(p.stream().map(entity -> DomainFactory.convert(entity, MagicCodeItemBo.class)).toList());
        }else {
            List<MagicCodeItemEntity> e = itemRepository.findAll(spec);
            response.setElements(e.stream().map(entity -> DomainFactory.convert(entity, MagicCodeItemBo.class)).toList());
        }
        return response;
    }

    @Override
    @Cacheable(value = "magicCodeItem", key = "'code:'+#p0", unless = "#result ==null || #result.size() ==0")
    public List<MagicCodeItemBo> findCodeItemByCodeId(@Nonnull Long codeId) {
        return itemRepository.findByCodeId(codeId).stream().
                map(item -> DomainFactory.convert(item, MagicCodeItemBo.class)).toList();
    }

    @Override
    @Transactional
    @Caching(put = {@CachePut(value = "magicCode", key = "#p0" , unless = "#result ==null")},
            evict = {@CacheEvict(value = "magicPlan",key = "#p1")})
    public MagicCodeBo bindCodeForPlan(@Nonnull Long codeId, @Nonnull Long planId) {
        MagicCodeEntity code = codeRepository.findById(codeId).orElse(null);
        if (code != null){
            code.setPlanId(planId);
            MagicCodeEntity entity = codeRepository.save(code);
            return DomainFactory.convert(entity, MagicCodeBo.class);
        }
        return null;
    }
}
