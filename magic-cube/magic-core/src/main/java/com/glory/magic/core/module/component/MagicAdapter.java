/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.component;

import com.glory.foundation.delegate.core.ConditionDelegateAdapter;
import com.glory.foundation.delegate.domain.DelegateContext;
import com.glory.magic.core.domain.context.MagicContext;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author YY
 * @date 2026/7/8
 * @description: TODO
 */
public interface MagicAdapter extends ConditionDelegateAdapter {

    default List<String> supportPlans(){
        return List.of();
    }

    @Override
    default boolean accept(DelegateContext context) {
        boolean bol = true;
        if (context instanceof MagicContext mc && !CollectionUtils.isEmpty(supportPlans())){
            bol = supportPlans().stream().anyMatch(p->p.equals(mc.getPlan().getPlanCode()));
        }
        return bol&&ConditionDelegateAdapter.super.accept(context);
    }
}
