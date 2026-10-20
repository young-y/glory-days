/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.domain.node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glory.foundation.basic.domain.PropertyDescImpl;
import com.glory.magic.core.domain.plan.MagicPlanRuleBo;
import com.glory.magic.core.helper.MagicHelper;
import jakarta.annotation.Nonnull;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author YY
 * @date 2026/7/13
 * @description: TODO
 */
public class ResultNode extends PropertyDescImpl {
    private String name;
    private Object value;
    private PathKey key;
    private MagicPlanRuleBo rule;
    private MagicNode node;
    private ResultNode parent;
    private final List<ResultNode> children = new ArrayList<>();

    public ResultNode() {
    }
    public ResultNode(ResultNode parent, @Nonnull PathKey key) {
        this.key = key;
        if (key.getIndex() != null && key.getIndex() < 0){
            key.setIndex(0);
        }
        this.name = key.getName();
        if (null != parent){
            this.parent = parent;
            this.parent.addChild(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public PathKey getKey() {
        return key;
    }

    public void setKey(PathKey key) {
        this.key = key;
    }

    public MagicPlanRuleBo getRule() {
        return rule;
    }

    public void setRule(MagicPlanRuleBo rule) {
        this.rule = rule;
    }

    public MagicNode getNode() {
        return node;
    }

    public void setNode(MagicNode node) {
        this.node = node;
    }

    public ResultNode getParent() {
        return parent;
    }

    public List<ResultNode> getChildren() {
        return children;
    }

    public void setChildren(List<ResultNode> children) {
        Optional.ofNullable(children).ifPresent(this.children::addAll);
    }

    @JsonIgnore
    public void addChild(String name, Object value) {
        ResultNode child = new ResultNode(this, new PathKey(name));
        child.setValue(value);
    }

    @JsonIgnore
    public void addChild(@Nonnull ResultNode child){
        Assert.isTrue(!isExistNode( child),String.format("node[%s] already exists.",child.getName()));
        this.children.add(child);
    }

    /**
     * determine whether the node exists or not
     * @param node {@link ResultNode}
     * @return true:存在
     */
    private boolean isExistNode(@Nonnull ResultNode node) {
        return children.stream().anyMatch(n -> n.key.equalsKey(node.getKey()));
    }

    @JsonIgnore
    public String getPath() {
        if (isRoot()){
            return "";
        }
        String parentPath =  parent.getPath();
        if (StringUtils.hasLength(parentPath)) {
            parentPath += PathKey.GAP_SEPARATOR;
        }
        return parentPath+ key.getFullName();
    }

    @JsonIgnore
    public String getNewOutput(String name){
        return MagicHelper.replaceLastSection(rule.getOutput(),name);
    }

    @JsonIgnore
    public Map<String, Object> transToMap() {
        return transToMap(ResultKeyNameStrategy.normalCase);
    }
    /**
     *  result trans to  map
     * @return {@link Map}
     */
    @SuppressWarnings("unchecked")
    @JsonIgnore
    public Map<String, Object> transToMap(@Nonnull ResultKeyNameStrategy<String> strategy) {
        Map<String, Object> map = new HashMap<>();
        children.forEach(node -> {
            String name = strategy.apply(node.getName());
            if (node.getKey().isListKey()){
                List<Object> list = (List<Object>) map.getOrDefault(name, new ArrayList<>());
                if (!map.containsKey(name)){
                    map.put(name, list);
                }
                list.add(node.transValue(strategy));
            }else {
                map.put(name, node.transValue(strategy));
            }
        });
        return map;
    }

    private Object transValue(ResultKeyNameStrategy<String> strategy){
        if (children.isEmpty()){
            return value;
        }else {
            return transToMap(strategy);
        }
    }

    @JsonIgnore
    public ResultNode addResultNode(ResultKey key){
        ResultNode node = null;
        if (isRoot()){//root node
            node  = findExistNode(key);
            if (null ==node){
                node = new ResultNode(this, key.getKey());
            }
            return node.addResultNode( key);
        }
        if (!key.hasNext()) {//it's lastest when next is null, return parent node
            return this;
        }
        node = findExistNode(key.getNext());
        if (null ==  node){
            node = new ResultNode(this, key.getNext().getKey());
        }
        return node.addResultNode(key.getNext());
    }

    /**
     * find exist node by key
     * @param key {@link ResultKey}
     * @return {@link ResultNode}
     */
    private ResultNode findExistNode(ResultKey key) {
        ResultNode node;
        if (key.getKey().isListKey()){
            node= findLastestNode(key);
            if (null != node && node.needNewElement(key.getNext())){//需要判断是否存在，如果存在需要插入新节点，Index+1
                key.getKey().setIndex(node.getKey().getIndex()+1);
                node = null;
            }
        }else {
            node = findChildNode(key);
        }
        return node;
    }

    @JsonIgnore
    public boolean isRoot() {
        return null == parent && null==key;
    }

    @JsonIgnore
    public boolean isTmpResultNode() {
        return null == key;
    }
    /**
     * does the list  need new element
     * @param key {@link ResultKey}
     * @return true:need new element
     */
    private boolean needNewElement(ResultKey key){
        if (key.getKey().isListKey()){
            return false;
        }
        boolean flag = false;
        for (ResultNode node:children){
            if (node.key.equalsKey(key.getKey())){
                if (null != key.getNext()){
                    return node.needNewElement(key.getNext());
                }else {//the same name node has existed and is last node,so need new element
                    flag= true;
                }
            }
        }
        return flag;
    }

    /**
     * 获取最新的list节点
     * @param key {@link ResultKey}
     * @return {@link ResultNode}
     */
    private ResultNode findLastestNode(ResultKey key){
        ResultNode resultNode = null;
        for (ResultNode node:children){
            if (node.key.isListKey()&&node.key.equalsKey(key.getKey())){
                if (null==resultNode){
                    resultNode = node;
                }else if (node.key.getIndex() > resultNode.key.getIndex()){
                    resultNode = node;
                }
            }
        }
        return resultNode;
    }

    /**
     * find node that it's none list node, and it's name is equal to key,return null if not exist
     * @param key {@link ResultKey}
     * @return {@link ResultNode}
     */
    private ResultNode findChildNode(ResultKey key){
        ResultNode resultNode = null;
        for (ResultNode node:children){
            if (!node.key.isListKey()&&node.key.equalsKey(key.getKey())){
                resultNode = node;
                break;
            }
        }
        return resultNode;
    }

}
