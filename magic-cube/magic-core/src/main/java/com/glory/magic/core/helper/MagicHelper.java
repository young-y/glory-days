/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.helper;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.glory.foundation.basic.formater.DateFormater;
import com.glory.foundation.basic.formater.ObjectFormater;
import com.glory.magic.core.domain.node.MagicNode;
import com.glory.magic.core.domain.plan.MagicPlanRuleBo;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author YY
 * @date 2026/6/19
 * @description: TODO
 */
@Component
public class MagicHelper {
    private static ObjectFormater objectFormater;

    private static DateFormater dateFormater;


    @Autowired
    public void setDateFormater(DateFormater dateFormater) {
        MagicHelper.dateFormater = dateFormater;
    }

    @Autowired
    public void setObjectFormater(ObjectFormater objectFormater) {
        MagicHelper.objectFormater = objectFormater;
    }

    public static DateFormater getDateFormater() {
        return dateFormater;
    }

    public static ObjectFormater getObjectFormater() {
        return objectFormater;
    }
    @SuppressWarnings("unchecked")
    public static Map<String, Object> objToMap(@Nonnull Object obj) {
        Assert.isTrue(!(obj instanceof Collection<?>), "Object is a collection.");
        if (obj instanceof Map<?, ?>){
            return (Map<String, Object>) obj;
        }
        return objectFormater.fromJsonAsMap(objectFormater.toJson(obj), String.class, Object.class);
    }

    public static  <T> T mapToObj(@Nonnull Map<String, Object> map,@Nonnull Class<T> clazz) {
        return objectFormater.fromAsJavaType(objectFormater.toJson(map),  TypeFactory.defaultInstance().constructType(clazz));
    }

    public static String toJson(Object obj){
        return objectFormater.toJson(obj);
    }

    public static MagicNode traverse(@Nonnull Object obj, @Nonnull Consumer<MagicNode> runner){
        Map<String,Object> map = objToMap(obj);
        MagicNode root = new MagicNode().setValue(map);
        traverseMap(root,map,runner);
        return root;
    }

    private static void traverseMap(MagicNode node,Map<String,Object> map,Consumer<MagicNode> runner){
        map.forEach((key, value) -> {
            MagicNode child = node.addChild(key);
            child.setSource(map);
            traverse(child,value,runner);
        });
    }

    @SuppressWarnings("unchecked")
    private static void traverse(@Nonnull MagicNode node, Object obj, Consumer<MagicNode> runner){
        if (obj instanceof Map<?,?>){
            Map<String,Object> map = (Map<String, Object>) obj;
            node.setSource(map);
            traverseMap(node,map,runner);

        }else if (obj instanceof Collection<?> coll){
            List<?> list = coll.stream().toList();
            traverseList(node,list,runner);
        }else {
            node.setValue(obj);
            runner.accept(node);
        }
    }
    private static void traverseList(@Nonnull MagicNode node, @Nonnull List<?> list,Consumer<MagicNode> runner){
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            if (obj instanceof Map<?,?> || obj instanceof Collection<?>){
                MagicNode child = node.addChild(node.getName(),i);
                traverse(child,obj,runner);
            }else {//if the element is not map or collection type,it is a leaf node
                node.setValue(list);
                runner.accept(node);
                return;
            }
        }
        node.destroy();
    }
    public static MagicNode traverse(@Nonnull Object  obj){
        Map<String,Object> map = objToMap(obj);
        MagicNode root =  new MagicNode().setValue( map);
        traverseMap(root,map,(node)->{});
        return root;
    }

    /**
     *
     * @param input contain '-*' or end with '#*' or equals '*'
     * @return regular string
     */
    public static String replaceRegular(String input){
        if (StringUtils.hasLength(input)){
            if (input.contains("-*")){
                input = input.replace("-*", "-\\d+");
            }
            if (input.endsWith("#*")){
                input = input.replace("#*", "#\\w+");
            }
            if (input.equals("*")){
                input = "\\w+";
            }
        }
        return input;
    }
    static Pattern pattern = Pattern.compile("(?<=\\$\\{).*?(?=})");
    public static String dynamicRuleOutput(String output, MagicPlanRuleBo ruleBo, Map<String,Object> map){
        Matcher matcher = pattern.matcher(output);
        if (matcher.find()){
            String var = matcher.group();
            String attrKey = ruleBo.getString(var+"Key");
            if (null != attrKey){//obtain condition key of node
                Object o = map.get(attrKey);
                if (null !=o){//replace new name
                    String newKey = ruleBo.getString(var +"_"+ o);
                    if (StringUtils.hasLength(newKey)){
                        return output.replace("${"+var+"}",newKey);
                    }
                }
            }
        }
        return output;
    }
}
