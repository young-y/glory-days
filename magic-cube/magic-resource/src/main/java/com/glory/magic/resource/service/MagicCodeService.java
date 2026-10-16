/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.resource.service;

import com.glory.foundation.basic.domain.GloryResponse;
import com.glory.magic.core.domain.code.MagicCodeBo;
import com.glory.magic.core.domain.code.MagicCodeItemBo;
import com.glory.magic.resource.domain.bo.MagicQueryRequest;
import com.glory.magic.resource.domain.bo.MagicQueryResponse;
import jakarta.annotation.Nonnull;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author YY
 * @date 2026/9/21
 * @description: TODO
 */
public interface MagicCodeService {

    MagicCodeBo saveAndUpdate(@Nonnull MagicCodeBo bo);

    void deleteCode(@Nonnull Long listId);
    void deleteCodeByPlanId(@Nonnull Long planId);

    MagicCodeBo findCode(@Nonnull Long codeId);

    MagicCodeBo findCode(@Nonnull String codeName);

    List<MagicCodeBo> findCodeByPlanId(@Nonnull Long planId);

    MagicQueryResponse<MagicCodeBo> codeQuery(@Nonnull MagicQueryRequest request);

    MagicCodeItemBo saveAndUpdate(@Nonnull MagicCodeItemBo bo);

    void deleteCodeItem(@Nonnull Long listId);

    MagicCodeItemBo findCodeItem(@Nonnull Long codeItemId);

    @Cacheable(value = "magicCodeItem", key = "#p0+':'+#p1+")
    default MagicCodeItemBo findCodeItem(@Nonnull String codeName, @Nonnull String itemValue){
        return findCodeItem(codeName, itemValue, null);
    }
    MagicCodeItemBo findCodeItem(@Nonnull String codeName, @Nonnull String itemValue, String itemValue1);

    MagicQueryResponse<MagicCodeItemBo> codeItemQuery(@Nonnull MagicQueryRequest request);
    List<MagicCodeItemBo> findCodeItemByCodeId(@Nonnull Long codeId);

    MagicCodeBo bindCodeForPlan(@Nonnull Long codeId, @Nonnull Long planId);
}
