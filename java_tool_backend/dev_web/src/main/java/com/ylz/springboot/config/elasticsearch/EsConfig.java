package com.ylz.springboot.config.elasticsearch;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author lhh
 * @Date 2019/9/14 17:32
 */
@Configuration
public class EsConfig {

    @PostConstruct
    public void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
}
