/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.context;

import jakarta.servlet.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @author YY
 * @date 2026/9/28
 * @description: TODO
 */
public class AppContextFilter implements Filter {
    private AppContextInitializer initializer;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        initializer.initialize( request);
        chain.doFilter(request, response);
    }

    @Autowired
    public void setInitializer(AppContextInitializer initializer) {
        this.initializer = initializer;
    }
}
