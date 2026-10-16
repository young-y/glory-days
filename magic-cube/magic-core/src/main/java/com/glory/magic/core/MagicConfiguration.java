package com.glory.magic.core;

import com.glory.foundation.basic.context.GloryThreadContextHolder;
import com.glory.magic.core.module.component.loader.MagicResourceLoader;
import com.glory.magic.core.module.component.processor.MagicProcessor;
import com.glory.magic.core.module.component.engine.MagicEngine;
import com.glory.magic.core.module.impl.DefaultMagicEngine;
import com.glory.magic.core.module.impl.DefaultMagicProcessor;
import com.glory.magic.core.service.resources.MagicResourceService;
import com.glory.magic.core.service.resources.impl.JsonMagicResourceServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.Executor;

@Configuration
@ComponentScan
public class MagicConfiguration {
    @Value("${glory.magic.executor.max-pool-size:50}")
    private int maxPoolSize;
    @Value("${glory.magic.executor.core-pool-size:20}")
    private int corePoolSize;
    @Value("${glory.magic.executor.queue-capacity:20}")
    private int queueCapacity;

    @Bean("magicExecutor")
    @ConditionalOnMissingBean(name = "magicExecutor")
    public Executor sparkExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("spark-");
        executor.setTaskDecorator((r)->{
            Map<String, Object> contextMap = GloryThreadContextHolder.getContextMap();
            return ()->{
                try{
                    GloryThreadContextHolder.setMap(contextMap);
                    r.run();
                }finally {
                    GloryThreadContextHolder.clear();
                }
            };
        });
        return executor;
    }

    @Bean
    @ConditionalOnMissingBean(MagicEngine.class)
    public MagicEngine magicEngine(){
        return new DefaultMagicEngine();
    }

    @Bean
    @ConditionalOnMissingBean(MagicProcessor.class)
    public MagicProcessor magicProcessor(){
        return new DefaultMagicProcessor();
    }
    @Bean
    @ConditionalOnMissingBean(MagicResourceLoader.class)
    public MagicResourceLoader magicResourceLoader(){
        return ()->{};
    }

    @Bean
    @ConditionalOnMissingBean
    public MagicResourceService magicResourceService(){
        return new JsonMagicResourceServiceImpl();
    }
}
