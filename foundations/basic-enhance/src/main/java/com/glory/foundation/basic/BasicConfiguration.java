/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.glory.foundation.basic.context.AppContext;
import com.glory.foundation.basic.context.AppContextFilter;
import com.glory.foundation.basic.context.AppContextInitializer;
import com.glory.foundation.basic.desensitize.condition.DesensitizePredicate;
import com.glory.foundation.basic.formater.DateFormater;
import com.glory.foundation.basic.formater.GloryDateFormater;
import com.glory.foundation.basic.formater.GloryObjectFormater;
import com.glory.foundation.basic.formater.ObjectFormater;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @author : YY
 * @date : 2025/11/27
 * @descprition :
 *
 */
@Configuration
@ComponentScan
public class BasicConfiguration {

	public static final String DESENSITIZE_ACTIVITY = "DESENSITIZE_ACTIVITY";

	@Value("${glory.context.tenant:GLORY}")
	private String defaultTenantCode;
	@Value("${glory.context.userId:100}")
	private Long defaultUserId;
	@Bean
	@ConditionalOnMissingBean
	public ObjectFormater objectFormater(){
		return new GloryObjectFormater();
	}

	@Bean
	@ConditionalOnMissingBean
	public DateFormater dateFormater(){
		return new GloryDateFormater();
	}

	@Bean
	@ConditionalOnMissingBean
	public DesensitizePredicate desensitizePredicate(){
		return ()-> AppContext.get(DESENSITIZE_ACTIVITY, true);
	}

	@Bean
	@ConditionalOnMissingBean
	public AppContextInitializer appContextInitializer(){
		return (request)->{
			String userId = ((RequestFacade) request).getHeader("glory-app-user-id");
			if(userId != null){
				AppContext.setUserId(Long.parseLong(userId));
			}else {
				AppContext.setUserId(defaultUserId);
			}
			String tenantCode = ((RequestFacade) request).getHeader("glory-app-tenant-code");
			if(tenantCode != null){
				AppContext.setTenantCode(tenantCode);
			}else {
				AppContext.setTenantCode(defaultTenantCode);
			}
			String traceId = ((RequestFacade) request).getHeader("glory-app-trace-id");
			if(traceId != null){
				AppContext.setTraceId(traceId);
			}else {
				AppContext.setTraceId(UUID.randomUUID().toString());
			}
		};
	}

	@Bean
	@ConditionalOnProperty(name = "glory.context.filter.enabled",havingValue = "true",matchIfMissing = true)
	public AppContextFilter appContextFilter(){
		return new AppContextFilter();
	}

	@Bean
	@ConditionalOnProperty(name = "glory.context.filter.enabled",havingValue = "true",matchIfMissing = true)
	public FilterRegistrationBean<AppContextFilter> sparkAppContextFilterBean(AppContextFilter appContextFilter) {
		FilterRegistrationBean<AppContextFilter> filter = new FilterRegistrationBean<>();
		filter.setName("appContextFilter");
		filter.setFilter(appContextFilter);
		filter.setOrder(100);
		return filter;
	}

	@Bean
	@ConditionalOnMissingBean
	public ObjectMapper objectMapper(ObjectFormater objectFormater){
		return objectFormater.provide();
	}
}
