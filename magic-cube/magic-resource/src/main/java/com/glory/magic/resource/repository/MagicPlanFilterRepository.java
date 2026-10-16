/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.resource.repository;

import com.glory.foundation.basic.domain.BooleanEnum;
import com.glory.magic.resource.domain.entity.MagicPlanFilterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author YY
 * @date 2026/9/22
 * @description: TODO
 */
@Repository
public interface MagicPlanFilterRepository extends JpaRepository<MagicPlanFilterEntity, Long> , JpaSpecificationExecutor<MagicPlanFilterEntity> {

    List<MagicPlanFilterEntity> findByPlanIdAndEnabled(Long planId, BooleanEnum enabled);
    List<MagicPlanFilterEntity> findByPlanId(Long planId);
}
