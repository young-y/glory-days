/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.desensitize;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.glory.foundation.basic.crypto.CryptoContext;
import com.glory.foundation.basic.crypto.CryptoHelper;
import com.glory.foundation.basic.crypto.CryptoType;
import com.glory.foundation.basic.crypto.annotation.Secret;
import com.glory.foundation.basic.desensitize.condition.DesensitizePredicate;
import com.glory.foundation.basic.desensitize.jackson.DesensitizeAnnotationIntrospector;
import com.glory.foundation.basic.desensitize.jackson.DesensitizePropertyFilter;
import com.glory.foundation.basic.desensitize.rule.DesensitizeRule;
import com.glory.foundation.basic.desensitize.rule.DesensitizeRuleConfig;
import com.glory.foundation.basic.formater.ObjectFormater;
import jakarta.annotation.Nonnull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author : YY
 * @date : 2025/12/22
 * @descprition :
 *
 */
@Component
public class DesensitizeHelper {
	private static DesensitizeRuleConfig ruleConfig;
	private static ObjectMapper desensitizeMapper;
	private static DesensitizePredicate desensitizePredicate;

	public static Object desensitizeToJson(Object obj){
		if (Objects.nonNull(desensitizeMapper)&&Objects.nonNull(obj)){
			try {
				return desensitizeMapper.writeValueAsString(obj);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return obj;
	}
	public static String desensitize(CryptoContext context){
		if (activity()){
			CryptoType algorithm = context.getType();
			if (CryptoType.MASK == algorithm){
				return CryptoHelper.encrypt(context);
			}
			return CryptoHelper.encrypt(context.wrapper());
		}
		return context.getValue();
	}

	/**
	 * determine whether the property is output,when not activity mode, output all
	 * @param name property name
	 * @return boolean
	 */
	public static boolean include(String name){
		return !activity() || null == ruleConfig ||ruleConfig.include( name);
	}

	/**
	 * determine whether the property need  desensitize
	 * @param annotated annotated
	 * @return boolean
	 */
	public static boolean match(Annotated annotated){
		if (activity()){
			Desensitize desensitize = annotated.getAnnotation(Desensitize.class);
			if (null != desensitize){//as long as it is marked Desensitize annotation, need to desensitize
				return true;
			}
			AnnotatedElement element = annotated.getAnnotated();
			try{
				if (element instanceof Method){
					PropertyDescriptor property = BeanUtils.findPropertyForMethod((Method) element);
					if (null !=property){//match property name through the Rule to determine whether to desensitize
						return match(property.getName());
					}
				}
			}catch (Exception ignored){
			}
		}
		return false;
	}

	public static boolean match(String name){
		return activity() && null != ruleConfig &&ruleConfig.match(name);
	}

	public static CryptoContext createContext(Annotated annotated){
		Desensitize desensitize = annotated.getAnnotation(Desensitize.class);
		if (null != desensitize){
			return CryptoContext.create(desensitize);
		}
		AnnotatedElement element = annotated.getAnnotated();
		try{
			if (element instanceof Method){
				PropertyDescriptor property = BeanUtils.findPropertyForMethod((Method) element);
				if (null !=property){
					DesensitizeRule rule = findRule(property.getName());
					if (null != rule){
						return rule.generate();
					}
				}
			}
		}catch (Exception ignored){
		}
		return null;
	}

	public static DesensitizeRule findRule(@Nonnull String name){
		if (null != ruleConfig){
			return ruleConfig.find(name);
		}
		return null;
	}

	public static void enroll(@Nonnull DesensitizeRule rule){
		Optional.ofNullable(ruleConfig).ifPresent(rc->{
			rc.enroll(rule);
		});
	}

	public static void enroll(@Nonnull List<DesensitizeRule> rules){
		Optional.ofNullable(ruleConfig).ifPresent(rs->{
			ruleConfig.enroll(rules);
		});
	}

	private static boolean activity(){
		return null == desensitizePredicate || desensitizePredicate.test();
	}

	@Autowired
	public void setFormater(ObjectFormater formater) {
		desensitizeMapper = formater.provideNew();
//		desensitizeMapper = provide.copy();
		initDesensitize(desensitizeMapper);
	}
	@Autowired
	public void setRuleConfig(DesensitizeRuleConfig ruleConfig) {
		DesensitizeHelper.ruleConfig = ruleConfig;
	}

	@Autowired
	public  void setDesensitizePredicate(DesensitizePredicate desensitizePredicate) {
		DesensitizeHelper.desensitizePredicate = desensitizePredicate;
	}

	private static void initDesensitize(ObjectMapper mapper){
		SimpleBeanPropertyFilter filter = new DesensitizePropertyFilter();
		mapper.setAnnotationIntrospector(new DesensitizeAnnotationIntrospector());
		Map<Class<?>,Class<?>> mixins = new HashMap<>(8);
		mixins.put(Object.class,DesensitizeFilterView.class);
		mapper.setMixIns(mixins);
		mapper.setFilterProvider(new SimpleFilterProvider()
				.addFilter("DESENSITIZE_FILTER_NAME",filter));
	}

	@JsonFilter("DESENSITIZE_FILTER_NAME")
	interface DesensitizeFilterView{}
}
