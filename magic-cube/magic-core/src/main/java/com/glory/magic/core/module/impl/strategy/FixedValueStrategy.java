/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.impl.strategy;

import com.glory.magic.core.domain.context.RuntimeMetadata;
import com.glory.magic.core.domain.node.ResultNode;
import com.glory.magic.core.module.component.strategy.MagicStrategy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author YY
 * @date 2026/9/20
 * @description: TODO
 */
@Component
@Order
public class FixedValueStrategy implements MagicStrategy {

    @Override
    public ResultNode doExecute(RuntimeMetadata metadata) {
        ResultNode rn = metadata.getResultNode();
        rn.setValue(metadata.getRule().getValue());
        return rn;
    }

    @Override
    public List<String> supportStrategies() {
        return List.of("fixedValue");
    }
}
