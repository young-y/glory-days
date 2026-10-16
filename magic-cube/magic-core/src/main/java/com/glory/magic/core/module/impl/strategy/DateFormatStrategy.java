/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.impl.strategy;

import com.glory.foundation.basic.formater.FormatHelper;
import com.glory.magic.core.domain.context.RuntimeMetadata;
import com.glory.magic.core.domain.node.ResultNode;
import com.glory.magic.core.domain.plan.MagicPlanRuleBo;
import com.glory.magic.core.exception.MagicException;
import com.glory.magic.core.module.component.strategy.MagicStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 * @author YY
 * @date 2026/9/20
 * @description: TODO
 */
@Component
public class DateFormatStrategy implements MagicStrategy {
    @Value("${spring.jackson.date-format:yyyy-MM-dd'T'HH:mm:ss}")
    private String springDateFormat;

    @Override
    public ResultNode doExecute(RuntimeMetadata metadata) {
        ResultNode rn = metadata.getResultNode();
        MagicPlanRuleBo rule = metadata.getRule();
        Object value = rn.getValue();
        try{
            switch (value) {
                case String s -> {
                    String dateFormat = rule.getString("dateFormat",springDateFormat);//if config in rule, use it.
                    Date date = FormatHelper.getFormat(dateFormat).parse(value.toString());
                    setDateFormat(rule, rn, date);
                }
                case Date date -> setDateFormat(rule, rn, date);
                case LocalDate date ->
                        rn.setValue(FormatHelper.getFormatter(rule.getFormat()).format(date));
                case LocalDateTime date ->
                        rn.setValue(FormatHelper.getFormatter(rule.getFormat()).format(date));
                case LocalTime date ->
                        rn.setValue(FormatHelper.getFormatter(rule.getFormat()).format(date));
                default -> rn.setValue(value);
            }
        } catch (Exception e) {
            throw new MagicException(e);
        }
        return rn;
    }

    private void setDateFormat(MagicPlanRuleBo rule, ResultNode rn, Date date){
        rn.setValue(FormatHelper.getFormat(rule.getFormat()).format(date));
    }

    @Override
    public List<String> supportStrategies() {
        return List.of("dateFormat");
    }
}
