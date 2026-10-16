/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.data.jpa;

import com.glory.foundation.basic.context.AppContext;
import com.glory.foundation.data.jpa.domain.audit.AuditAware;
import com.glory.foundation.data.jpa.domain.converter.DomainConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author : YY
 * @date : 2025/11/10
 * @descprition :
 *
 */
@Configuration
@ComponentScan
public class GloryDataConfiguration {

	@Value("${spark.glory.default.user-id:1}")
	private long defaultUserId;

	@Bean
	@ConditionalOnMissingBean
	public AuditAware auditAware() {
		return (a) -> {
			Long userId = AppContext.getUserId();
			if (null == a.getCreateBy() || 0 == a.getCreateBy()) {
                a.setCreateBy(Objects.requireNonNullElseGet(userId, () -> defaultUserId));
			}
			if (null == a.getModifiedBy() ||0 == a.getModifiedBy()) {
                a.setModifiedBy(Objects.requireNonNullElseGet(userId, () -> defaultUserId));
			}
			LocalDateTime now = LocalDateTime.now();
			if (null == a.getCreateTime()) {
				a.setCreateTime(now);
			}
			a.setModifiedTime(now);
		};
	}

	@Bean
	@ConditionalOnMissingBean(DomainConverter.class)
	public DomainConverter<?,?> domainConverter(){
		return o-> o;
	}

}
