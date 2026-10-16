/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.domain.node;

import com.glory.magic.core.domain.plan.MagicPlanBo;
import jakarta.annotation.Nonnull;

import java.util.Map;

/**
 * @author YY
 * @date 2026/7/27
 * @description: TODO
 */
public class ResultSet {
    private final ResultNode resultNode = new ResultNode();
    private MagicPlanBo plan;


    public ResultNode addResultNode(ResultKey key){
        return resultNode.addResultNode(key);
    }

    public ResultNode addResultNode(String path){
        return resultNode.addResultNode(ResultKey.create( path));
    }

    public ResultNode getResultNode(){
        return resultNode;
    }

    public void setPlan(MagicPlanBo plan) {
        this.plan = plan;
    }

    public MagicPlanBo getPlan() {
        return plan;
    }

    public Map<String, Object> transToMap(){
        return resultNode.transToMap();
    }

    public Map<String, Object> transToMap(@Nonnull ResultKeyNameStrategy<String> strategy){
        return resultNode.transToMap(strategy);
    }
}
