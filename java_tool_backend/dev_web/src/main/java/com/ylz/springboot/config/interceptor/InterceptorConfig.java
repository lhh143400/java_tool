package com.ylz.springboot.config.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * InterceptorConfig
 * 拦截器配置类
 *
 * @author: Chris
 * @time: 2018.08.20
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    /**
     * 注册拦截器，可注册多个
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有请求
        registry.addInterceptor(new RequestInterceptor())
                .addPathPatterns("/*");
        super.addInterceptors(registry);
    }
}
