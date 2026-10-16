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

/**
 * @author YY
 * @date 2026/9/2
 * @description: TODO
 */
public class ResultKey {
    private PathKey key;
    private ResultKey next;

    public ResultKey(@Nonnull String path){
        if (path.contains(PathKey.GAP_SEPARATOR)){
            String name = path.substring(0, path.indexOf(PathKey.GAP_SEPARATOR));
            key = PathKey.create(name);
            next = ResultKey.create(path.substring(path.indexOf(PathKey.GAP_SEPARATOR)+1));
        }else {
            key = PathKey.create(path);
        }
    }

    public PathKey getKey() {
        return key;
    }

    public void setKey(PathKey key) {
        this.key = key;
    }

    public ResultKey getNext() {
        return next;
    }

    @JsonIgnore
    public boolean hasNext(){
        return null != next;
    }

    public void setNext(ResultKey next) {
        this.next = next;
    }


    public static ResultKey create(String path){
        return new ResultKey(path);
    }
}
