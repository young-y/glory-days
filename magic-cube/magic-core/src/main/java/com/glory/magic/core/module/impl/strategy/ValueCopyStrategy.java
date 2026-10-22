/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.impl.strategy;

import com.glory.magic.core.module.component.strategy.MagicStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.glory.magic.core.helper.MagicConstant.STRATEGY_VALUE_COPY;

/**
 * @author YY
 * @date 2026/9/20
 * @description: TODO
 */
@Component
//@Order
public class ValueCopyStrategy implements MagicStrategy {

    @Override
    public List<String> supportStrategies() {
        return List.of(STRATEGY_VALUE_COPY);
    }
}
