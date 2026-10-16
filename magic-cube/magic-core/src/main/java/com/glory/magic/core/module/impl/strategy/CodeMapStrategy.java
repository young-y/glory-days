/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.impl.strategy;

import com.glory.magic.core.domain.code.MagicCodeItemBo;
import com.glory.magic.core.domain.context.MagicContext;
import com.glory.magic.core.domain.context.RuntimeMetadata;
import com.glory.magic.core.domain.node.MagicNode;
import com.glory.magic.core.domain.node.ResultNode;
import com.glory.magic.core.domain.plan.MagicPlanRuleBo;
import com.glory.magic.core.exception.MagicException;
import com.glory.magic.core.module.component.strategy.MagicStrategy;
import com.glory.magic.core.service.resources.MagicResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author YY
 * @date 2026/10/5
 * @description: TODO
 */
@Component
public class CodeMapStrategy implements MagicStrategy {
    @Autowired
    private MagicResourceService resourceService;

    @Override
    public ResultNode doExecute(RuntimeMetadata metadata) {
        ResultNode rn = metadata.getResultNode();
        MagicPlanRuleBo rule = metadata.getRule();
        Object value = rn.getValue();
        String codeName = rule.getFormat();
        Object srcValue1 = getSrcValue1(rule, metadata.getNode());
        MagicCodeItemBo codeItem = findCodeItem(metadata.getContext(), codeName, value.toString(),srcValue1==null?"":srcValue1.toString());
        if (null != codeItem){
            rn.setValue(codeItem.getDestValue());
        }else {
            rn.setValue(null);
            throw new MagicException(String.format("CodeItem[codeName=%s,srcValue=%s] not found.",codeName, value));
        }
        return rn;
    }

    private Object getSrcValue1(MagicPlanRuleBo rule, MagicNode node){
        String srcValue1Key = rule.getString("srcValue1Key");
        if (StringUtils.hasLength(srcValue1Key)){
            return node.getSource().get(srcValue1Key);
        }
        return null;
    }

    private MagicCodeItemBo findCodeItem(MagicContext context, String codeName, String itemValue,  String itemValue1){
        //find code item from plan first
        MagicCodeItemBo codeItem = context.getPlan().findCodeItem(codeName, itemValue, "");
        if (codeItem == null){//find code item from resource second
            codeItem = resourceService.findCodeItem(codeName, itemValue, "");
        }
        return codeItem;
    }

    @Override
    public List<String> supportStrategies() {
        return List.of("codeMap");
    }
}
