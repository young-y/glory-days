/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.domain.node;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author YY
 * @date 2026/10/11
 * @description: TODO
 */
@Configuration
public class ResultKeyNameStrategyConfig {

    @Bean
    public ResultKeyNameStrategy<String> upperStrategy(){
        return ResultKeyNameStrategy.upperCase;
    }

    @Bean
    public ResultKeyNameStrategy<String> lowerStrategy(){
        return ResultKeyNameStrategy.lowerCase;
    }
    @Bean
    public ResultKeyNameStrategy<String> firstStrategy(){
        return ResultKeyNameStrategy.firstCase;
    }

    @Bean
    public ResultKeyNameStrategy<String> normalStrategy(){
        return ResultKeyNameStrategy.normalCase;
    }
}
