/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.service.resources;

import com.glory.magic.core.domain.code.MagicCodeBo;
import com.glory.magic.core.domain.code.MagicCodeItemBo;
import com.glory.magic.core.domain.plan.MagicPlanBo;
import jakarta.annotation.Nonnull;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author YY
 * @date 2026/9/22
 * @description: TODO
 */
public interface MagicResourceService {

   MagicPlanBo findPlan(@Nonnull String planCode);

    MagicCodeBo findCode(@Nonnull String codeName);

    default MagicCodeItemBo findCodeItem(@Nonnull String codeName, @Nonnull String itemValue){
        return findCodeItem(codeName, itemValue, null);
    }

    MagicCodeItemBo findCodeItem(@Nonnull String codeName, @Nonnull String itemValue, String itemValue1);
}
