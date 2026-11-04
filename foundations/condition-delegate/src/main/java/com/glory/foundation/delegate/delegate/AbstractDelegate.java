/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.delegate.delegate;

import com.glory.foundation.delegate.core.DelegateAdapter;
import com.glory.foundation.delegate.domain.DelegateContext;
import jakarta.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.comparator.Comparators;

import java.lang.reflect.ParameterizedType;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author YY
 * @date 2026/6/15
 * @description: TODO
 */
public class AbstractDelegate<S extends DelegateAdapter> implements GloryDelegate<S>, ApplicationRunner, ApplicationContextAware {
    protected List<S> targets;

    protected ApplicationContext applicationContext;

    @SuppressWarnings({"unchecked"})
    protected Class<S> getActualType() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<S>) type.getActualTypeArguments()[0];
    }

    protected BiFunction<List<S>,DelegateContext,List<S>> getSelector(){
        return (ts,context)-> ts.stream().filter(t->t.accept(context)).toList();
    }

    protected Supplier<S> getDefaultSelector() {
        return ()->targets.getLast();
    }

    @Override
    public S delegate(DelegateContext context) {
        List<S> delegates = delegates(context);
        return CollectionUtils.isEmpty(delegates)?getDefaultSelector().get():delegates.getFirst();
    }

    @Override
    public List<S> delegates(DelegateContext context) {
        Assert.notEmpty(targets,"target delegate services is empty!");
        return getSelector().apply(targets,context);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        targets = applicationContext.getBeansOfType(getActualType())
                .values()
                .stream()
                .sorted(Comparator.comparing(o->{
                    Order od = o.getClass().getAnnotation(Order.class);
                    return null != od ? od.value() : 100;
                }).thenComparing(o->o.getClass().getName(), Comparator.reverseOrder()))
                .toList();
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
