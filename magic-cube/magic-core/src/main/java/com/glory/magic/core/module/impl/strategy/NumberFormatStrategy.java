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
import com.glory.magic.core.domain.plan.MagicPlanRuleBo;
import com.glory.magic.core.module.component.strategy.MagicStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * @author YY
 * @date 2026/9/20
 * @description: TODO
 */
@Component
public class NumberFormatStrategy implements MagicStrategy {

    @Override
    public ResultNode doExecute(RuntimeMetadata metadata) {
        MagicPlanRuleBo rule = metadata.getRule();
        ResultNode rn = metadata.getResultNode();
        Object value = rn.getValue();
        Assert.hasLength(rule.getFormat(), "number format can not be null");
        DecimalFormat decimalFormat = getDecimalFormat(rule);
        if (null == decimalFormat){//can not format, use string format
            rn.setValue(String.format(rule.getFormat(), value));
        }else {
            rn.setValue(decimalFormat.format(value));
        }
        return rn;
    }

    private DecimalFormat getDecimalFormat(MagicPlanRuleBo rule) {
        DecimalFormat decimalFormat = null;
        String formatType = rule.getString("formatType", "0");
        switch (formatType){//0: number, 1: currency, 2: percentage, other number: String.format
            case "0" -> {// number
                decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance();
                decimalFormat.applyLocalizedPattern(rule.getFormat());
            }
            case "1" -> {// currency
                decimalFormat = (DecimalFormat) NumberFormat.getCurrencyInstance();
                decimalFormat.applyLocalizedPattern(rule.getFormat());
            }
            case "2" -> {// percentage
                decimalFormat = (DecimalFormat) NumberFormat.getPercentInstance();
            }
        }
        return decimalFormat;
    }

    @Override
    public List<String> supportStrategies() {
        return List.of("numberFormat");
    }
}
