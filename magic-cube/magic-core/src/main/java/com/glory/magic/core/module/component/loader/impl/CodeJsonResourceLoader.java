/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.module.component.loader.impl;

import com.glory.magic.core.domain.code.MagicCodeBo;
import com.glory.magic.core.helper.MagicExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author YY
 * @date 2026/9/21
 * @description: TODO
 */
@Component
public class CodeJsonResourceLoader extends JsonResourceLoader<MagicCodeBo>{
    @Value("${glory.magic.code.resource.path:/magic/code/*.json}")
    private String resourcePath;
    @Autowired
    private MagicExplorer magicExplorer;
    @Override
    protected void handleResource(MagicCodeBo resource) {
        magicExplorer.registerCode(resource);
    }

    @Override
    protected String getResourcePath() {
        return resourcePath;
    }
}
