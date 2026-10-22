/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.impl.strategy;

import com.glory.foundation.basic.crypto.CryptoContext;
import com.glory.foundation.basic.crypto.CryptoHelper;
import com.glory.foundation.basic.crypto.CryptoType;
import com.glory.magic.core.domain.context.RuntimeMetadata;
import com.glory.magic.core.domain.node.ResultKeyNameStrategy;
import com.glory.magic.core.domain.node.ResultNode;
import com.glory.magic.core.domain.plan.MagicPlanRuleBo;
import com.glory.magic.core.module.component.strategy.MagicStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.glory.magic.core.helper.MagicConstant.*;

/**
 * @author YY
 * @date 2026/9/20
 * @description: TODO
 */
@Component
public class StringFormatStrategy implements MagicStrategy {

    @Override
    public ResultNode doExecute(RuntimeMetadata metadata) {
        MagicPlanRuleBo rule = metadata.getRule();
        ResultNode rn = metadata.getResultNode();
        Object value = rn.getValue();
        String format = rule.getFormat();
        String valueStr = value.toString();
        if (StringUtils.hasLength( format)){
            switch ( format){
                case "upper"->
                        rn.setValue(ResultKeyNameStrategy.upperCase.apply(valueStr));
                case "lower"->
                        rn.setValue(ResultKeyNameStrategy.lowerCase.apply(valueStr));
                case "first"->
                        rn.setValue(ResultKeyNameStrategy.firstCase.apply(valueStr));
                case "mask"->
                        rn.setValue(encryptMask(rule, value.toString()));
                case "encrypt"-> {
                    String algorithm = rule.getString(RULE_FORMAT_ALGORITHM);// AES,DES, RSA,BASE64,default:AES
                    CryptoType type = CryptoType.fromPersistableValue(algorithm);
                    if (null == type){
                        type = CryptoType.AES;
                    }
                    rn.setValue(CryptoHelper.encrypt(type,value.toString()));
                }
                default -> rn.setValue(value);
            }
        }else {
            rn.setValue(value);
        }
        return rn;
    }

    private String encryptMask(MagicPlanRuleBo rule, String value) {
        CryptoContext context = new CryptoContext();
        context.setType(CryptoType.MASK);
        if (rule.containsKey(RULE_FORMAT_PATTERN)){
            context.setPattern(rule.getString(RULE_FORMAT_PATTERN));
        }
        if (rule.containsKey(RULE_FORMAT_REPLACEMENT)){
            context.setReplacement(rule.getString(RULE_FORMAT_REPLACEMENT));
        }
        if (rule.containsKey(RULE_FORMAT_MINLENGTH)){
            context.setMinLength(Integer.parseInt(rule.getString(RULE_FORMAT_MINLENGTH)));
        }
        if (rule.containsKey(RULE_FORMAT_MINLENGTH_STR)){
            context.setMinLengthStr(rule.getString(RULE_FORMAT_MINLENGTH_STR));
        }
        context.setValue(value);
        return CryptoHelper.encrypt(context);
    }

    @Override
    public List<String> supportStrategies() {
        return List.of(STRATEGY_STRING_FORMAT);
    }
}
