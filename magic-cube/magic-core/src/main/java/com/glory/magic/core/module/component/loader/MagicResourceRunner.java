/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.component.loader;

import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author YY
 * @date 2026/9/21
 * @description: TODO
 */
@Component
public class MagicResourceRunner implements ApplicationRunner {
    @Resource
    private List<MagicResourceLoader> loaders;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional.ofNullable(loaders).ifPresent(loaders->loaders.forEach(MagicResourceLoader::load));
    }
}
