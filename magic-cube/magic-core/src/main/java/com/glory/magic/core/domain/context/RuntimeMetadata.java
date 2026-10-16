/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.domain.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glory.magic.core.domain.node.MagicNode;
import com.glory.magic.core.domain.node.ResultNode;
import com.glory.magic.core.domain.plan.MagicPlanRuleBo;

/**
 * @author YY
 * @date 2026/10/11
 * @description: TODO
 */
public class RuntimeMetadata {
    private ResultNode resultNode;
    private MagicContext context;

    public RuntimeMetadata(MagicContext context, ResultNode resultNode) {
        this.context = context;
        this.resultNode = resultNode;
    }

    public void setResultNode(ResultNode resultNode) {
        this.resultNode = resultNode;
    }

    public ResultNode getResultNode() {
        return resultNode;
    }

    public MagicContext getContext() {
        return context;
    }

    public void setContext(MagicContext context) {
        this.context = context;
    }

    @JsonIgnore
    public MagicPlanRuleBo getRule() {
        return context.getRule();
    }

    @JsonIgnore
    public MagicNode getNode() {
        return context.getNode();
    }
}
