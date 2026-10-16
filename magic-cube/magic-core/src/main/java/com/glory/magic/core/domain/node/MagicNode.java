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
import jakarta.annotation.Nonnull;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author YY
 * @date 2026/6/17
 * @description: TODO
 */
public class MagicNode extends PropertyDescImpl {
    private String name;
    private PathKey key;
    private Object value;
    @JsonIgnore
    private MagicNode parent;
    private Map<String, Object> source;
    private final List<MagicNode> children = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PathKey getKey() {
        return key;
    }

    public void setKey(@Nonnull PathKey key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public MagicNode setValue(Object value) {
        this.value = value;
        return this;
    }

    public MagicNode getParent() {
        return parent;
    }

    public void setParent(MagicNode parent) {
        this.parent = parent;
        Optional.ofNullable(this.parent).ifPresent(p -> p.addChild(this));
    }

    public Map<String, Object> getSource() {
        return source;
    }

    public void setSource(Map<String, Object> source) {
        this.source = source;
    }

    public List<MagicNode> getChildren() {
        return children;
    }

    public void setChildren(List<MagicNode> children) {
        Assert.isNull(value,"value not null.");
        Optional.ofNullable(children).ifPresent(this.children::addAll);
    }

    /**
     * get node full path
     * @return node path
     */
    @JsonIgnore
    public String getPath() {
        if (null == parent) return "";
        String parentPath =  parent.getPath();
        if (StringUtils.hasLength(parentPath)) {
            parentPath += PathKey.GAP_SEPARATOR;
        }
        return parentPath+ key.getFullName();
    }

    /**
     * add a child node
     * @param name child node name
     * @return child node
     */
    @JsonIgnore
    public MagicNode addChild(@Nonnull String name){
        return create(this,PathKey.create(name));
    }
    /**
     * add a child node
     * @param name child node name
     * @param index child node index
     * @return child node
     */
    @JsonIgnore
    public MagicNode addChild(@Nonnull String name,Integer index){
        return create(this.parent,PathKey.create(name, index));
    }

    /**
     * add a child node
     * @param child child node
     */
    @JsonIgnore
    public void addChild(@Nonnull MagicNode child){
//        Assert.isNull(value,"value not null.");
        Assert.isTrue(!isExistNode(child),String.format("node[%s] already exists.",child.getName()));
        children.add(child);
    }

    @JsonIgnore
    public void destroy(){
        if (null != parent){
            parent.children.remove(this);
        }
    }

    /**
     * check whether the node matches the pattern
     * @param pattern pattern
     * @return true if matches
     */
    @JsonIgnore
    public boolean match(@Nonnull String pattern){
        String path = getPath();
        return StringUtils.hasLength(path) && Pattern.matches(pattern, path);
    }

    /**
     * check whether the node is root node
     * @return true if root node
     */
    @JsonIgnore
    public boolean isRoot(){
        return null == parent;
    }

    /**
     * check whether the node is leaf node
     * @return true if leaf node
     */
    @JsonIgnore
    public boolean isLeaf(){
        return  children.isEmpty() && !(value instanceof Map<?,?>) && !(value instanceof Collection<?>);
    }

    /**
     * check whether the node exists at current node
     * @param node node
     * @return true if exists
     */
    @JsonIgnore
    public boolean isExistNode(@Nonnull MagicNode node){
        return children.stream().anyMatch(child -> child.key.equalsKey(node.key));
    }

    /**
     * find node by pattern ,suggesting the pattern is a regular expression and from the root node
     * @param patten pattern
     * @return first node
     */
    @JsonIgnore
    public MagicNode findNode(@Nonnull String patten){
        if (match(patten)){
            return this;
        }
        for (MagicNode child : children){
            MagicNode node = child.findNode(patten);
            if (null != node){
                return node;
            }
        }
        return null;
    }

    /**
     * find leaf nodes from root node
     * @return leaf nodes
     */
    @JsonIgnore
    public List<MagicNode> findLeafNodes(){
        List<MagicNode> leafNodes = new ArrayList<>();
        findLeafNodes(leafNodes);
        return leafNodes;
    }

    /**
     * find leaf nodes from root node
     * @param leafNodes all leaf nodes
     */
    @JsonIgnore
    public void findLeafNodes(@Nonnull List<MagicNode> leafNodes){
        if (isLeaf()){
            leafNodes.add(this);
        }else if (!this.children.isEmpty()){
            for (MagicNode child : children){
                child.findLeafNodes(leafNodes);
            }
        }
    }

    @JsonIgnore
    public List<MagicNode> findNodes(@Nonnull String pattern){
        List<MagicNode> nodes = new ArrayList<>();
        findNodes(pattern,nodes);
        return nodes;
    }
    /**
     * find nodes by pattern ,suggesting the pattern is a regular expression and from the root node
     * @param pattern  pattern
     * @param nodes all match pattern nodes
     */
    @JsonIgnore
    public void findNodes(@Nonnull String pattern,@Nonnull List<MagicNode> nodes){
        if (isLeaf() &&match(pattern)){
            nodes.add(this);
        }
        children.forEach(child -> child.findNodes(pattern,nodes));
    }

    /**
     * obtain the root node
     * @return root node
     */
    @JsonIgnore
    public MagicNode getRoot(){
        MagicNode root = this;
        while (null != root.parent){
            root = root.parent;
        }
        return root;
    }

    public static MagicNode create(MagicNode parent,@Nonnull PathKey key){
        MagicNode node = new MagicNode();
        node.setKey(key);
        node.setName(key.getName());
        node.setParent(parent);
        return node;
    }

}
