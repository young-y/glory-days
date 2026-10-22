/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.impl.strategy;

import com.glory.magic.core.domain.context.MagicContext;
import com.glory.magic.core.domain.context.RuntimeMetadata;
import com.glory.magic.core.domain.node.MagicNode;
import com.glory.magic.core.domain.node.ResultNode;
import com.glory.magic.core.domain.plan.MagicPlanRuleBo;
import com.glory.magic.core.module.component.strategy.MagicStrategy;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author YY
 * @date 2026/10/21
 * @description:
 * use Groovy script to calculate the value,the strategy name is groovyFormula
 * <p>
 *     rule formula: it is Groovy script
 *     rule parameters regulation:
 *     1. if key start with $C_,it means get parameter value from cache,parameter name is substring after $C_.
 *     Attention:the cache node must be in the same node or parent nodes, and the value format is cacheEventKey;nodePath,
 *     if it can't provide input,get the first publishCacheEvent
 *     2. if key start with $N_,it means get parameter value from same node source,parameter name is substring after $N_
 *     3. else key is the node name,value is the node value
 * </p>
 */
@Component
public class GroovyScriptFormulaStrategy implements MagicStrategy {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public static final String CACHE_KEY = "$C_";//get parameter value from cache
    public static final String CACHE_ALL_KEY = "$CA_";//get parameter value from cache
    public static final String MAGIC_NODE_KEY = "$N_";//get parameter value from magicNode source
    public static final String ROOT_NODE_KEY = "$R_";//get parameter value from magicNode source

    @Override
    public ResultNode doExecute(RuntimeMetadata metadata) {
        MagicPlanRuleBo rule = metadata.getRule();
        String formula = rule.getFormula();
        Assert.hasLength(formula, "formula is null");
        Map<String, Object> parameters = formatParameters(metadata,rule.getParameters());
        Binding binding = new Binding(parameters);
        GroovyShell shell = new GroovyShell(binding);
        Script parse = shell.parse(formula);
        Object run = parse.run();
        logger.trace("formula[{}] ,parameter[{}] , result:{}", formula,parameters,run);
        BigDecimal value = run instanceof BigDecimal ? (BigDecimal) run : new BigDecimal(run.toString());
        BigDecimal newValue = value.setScale(rule.getScala(), RoundingMode.HALF_DOWN);
        metadata.getResultNode().setValue(newValue);
        return metadata.getResultNode();
    }

    private Map<String, Object> formatParameters(RuntimeMetadata metadata,Map<String, Object> parameters) {
        Map<String, Object> newParameters = new HashMap<>();
        MagicContext context = metadata.getContext();
        Optional.ofNullable( parameters).ifPresent(map->
            map.forEach((key, value)->{
                if (Objects.nonNull( value)){
                    if (key.startsWith(CACHE_KEY)){//the cache node must be in the same node or parent nodes
                        String pk = key.substring(CACHE_KEY.length());
                        String str = value.toString();
                        if (str.contains(";")){//split cacheEventKey and node input path
                            String[] keys = str.split(";");//format :cacheEventKey;nodePath
                            MagicContext cache = context.getResult().getPublishCacheEventContext(keys[0], keys[1]);
                            putParameters(pk,cache.getNode().getValue(),newParameters);
                        }else {//if it can't provide input,get the first publishCacheEvent
                            MagicContext cache = context.getResult().getPublishCacheEventContext(str,null);
                            putParameters(pk,cache.getNode().getValue(),newParameters);
                        }
                    }else  if (key.startsWith(CACHE_ALL_KEY)){//usually used on global rule type
                        String pk = key.substring(CACHE_ALL_KEY.length());
                        Double sumCacheValues = getSumCacheValues(context, value.toString());
                        putParameters(pk,sumCacheValues,newParameters);
                    }else if (key.startsWith(MAGIC_NODE_KEY)){//get parameter value from the same node source
                        String pk = key.substring(MAGIC_NODE_KEY.length());
                        String str = value.toString();
                        Object nv = context.getNode().getSource().get(str);
                        putParameters(pk,nv,newParameters);
                    }else if(key.startsWith(ROOT_NODE_KEY)){
                        String pk = key.substring(CACHE_KEY.length());
                        String str = value.toString();
                        MagicNode root = context.getNode().getRoot();
                        MagicNode node = root.findNode(str);
                        Assert.notNull(node, String.format("can't find parameter[%s] node path[%s]",key, str));
                        Assert.notNull(node.getValue(), String.format("parameter[%s] node value[%s] is null",key, str));
                        putParameters(pk,node.getValue(),newParameters);
                    }else {
                        putParameters(key,value,newParameters);
                    }
                }
            }));
        if (!context.getNode().isRoot()){
            putParameters(context.getNode().getName(),context.getNode().getValue(),newParameters);
        }
        return newParameters;
    }

    private void putParameters(String key,Object value,Map<String, Object> parameters) {
        Assert.notNull(value, "please check your formula parameter["+key+"] value is null");
        parameters.put(key,value);
    }

    private Double getSumCacheValues(MagicContext context,String cacheEventKey){
        List<MagicContext> contextList = context.getResult().getPublishCacheEventContexts(cacheEventKey);
        return contextList.stream().mapToDouble(c->{
            Object value = c.getNode().getValue();
            return value instanceof BigDecimal ? ((BigDecimal) value).doubleValue() : Double.parseDouble(value.toString());
        }).sum();
    }

    @Override
    public List<String> supportStrategies() {
        return List.of("groovyFormula");
    }
}
