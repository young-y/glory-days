/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.domain.node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import org.springframework.util.Assert;

/**
 * @author YY
 * @date 2026/8/3
 * @description: TODO
 */
public class PathKey {
    public static final String INDEX_SEPARATOR = "-";
    public static final String GAP_SEPARATOR = "#";
    public static final String PATTERN_SEPARATOR = "*";
    private String name;
    private Integer index;

    public PathKey(@Nonnull String name, @Nonnull Integer index) {
        setName(name);
        setIndex(index);
    }

    public PathKey(String name) {
        setName(name);

    }


    public void setName(@Nonnull String name) {
        Assert.doesNotContain(name, GAP_SEPARATOR, "The name of PathKey can not contains GAP_SEPARATOR[#].");
        if (name.contains(INDEX_SEPARATOR)) {
            String[] names = name.split(INDEX_SEPARATOR);
            this.name = names[0];
            if (names[1].equals("*")){
                this.index = -1;
            }else {
                this.index = Integer.valueOf(names[1]);
            }
        } else {
            this.name = name;
        }
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public String getFullName() {
        return (null != index) ? name + INDEX_SEPARATOR + index : name;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    @JsonIgnore
    public boolean equalsKey(PathKey key) {
        if (null == index){
            if (null == key.getIndex()){
                return name.equals(key.getName());
            }
            return false;//key.index is not null
        }else {
            if (null == key.getIndex()){
                return false;
            }
            if (key.getIndex() < 0){//key.index  can not set value and not null
                return name.equals(key.getName());
            }else {
                return name.equals(key.getName()) && index.equals(key.getIndex());
            }
        }
    }

    @JsonIgnore
    public boolean isListKey(){
        return null != index;
    }

    public static PathKey create(String name, Integer index) {
        return new PathKey(name, index);
    }

    public static PathKey create(String name) {
        return new PathKey(name);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        if (null != index) {
            builder.append(INDEX_SEPARATOR).append(index);
        }
        return builder.toString();
    }
}

