/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author YY
 * @date 2026/9/30
 * @description: TODO
 */
public class GloryResponse <E>{
    private Integer status = ResultStatus.Success.getValue();
    private List<E> elements;
    private List<String> messages;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<E> getElements() {
        return elements;
    }

    public void setElements(List<E> elements) {
        if (null == this.elements) {
            this.elements = new ArrayList<>();
        }
        Optional.of(elements).ifPresent(this.elements::addAll);
    }

    @JsonIgnore
    public GloryResponse<E> addElement(E element){
        if (null == this.elements) {
            this.elements = new ArrayList<>();
        }
        this.elements.add(element);
        return this;
    }

    @JsonIgnore
    public E getElement(){
        if (CollectionUtils.isEmpty(this.elements)){
            return this.elements.getFirst();
        }
        return null;
    }

    @JsonIgnore
    public boolean hasContent() {
        return !CollectionUtils.isEmpty(this.elements);
    }

    @JsonIgnore
    public <T> List<T> map(@Nonnull Function<E, T> converter) {
        if (null == this.elements) {
            return null;
        }
        return this.elements.stream().map(converter).toList();
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        if (null == this.messages) {
            this.messages = new ArrayList<>();
        }
        Optional.ofNullable(messages).ifPresent(this.messages::addAll);
    }

    @JsonIgnore
    public GloryResponse<E> addMessage(@Nonnull String message) {
        if (null == this.messages) {
            this.messages = new ArrayList<>();
        }
        this.messages.add(message);
        return this;
    }

    public static <T> GloryResponse<T> Success(){
        return new GloryResponse<>();
    }
    public static <T> GloryResponse<T> Success(T obj){
        return new GloryResponse<T>().addElement(obj);
    }

    public static <T> GloryResponse<T> Success(List<T> elements){
        GloryResponse<T> response = new GloryResponse<>();
        response.setElements(elements);
        return response;
    }

    public static <T> GloryResponse<T> of(@Nonnull ResultStatus status){
        GloryResponse<T> response = new GloryResponse<>();
        response.setStatus(status.getValue());
        return response;
    }

    public static <T> GloryResponse<T> warn(@Nonnull String warnMsg){
        GloryResponse<T> response = of(ResultStatus.Warn);
        response.addMessage(warnMsg);
        return response;
    }

    public static <T> GloryResponse<T> fail(@Nonnull String failMsg){
        GloryResponse<T> response = of(ResultStatus.Fail);
        response.addMessage(failMsg);
        return response;
    }
}
