package com.xueyong.springbootapi.common;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ElasticSearchConfig {
    @PostConstruct
    void init(){
        //排除冲突
        //在项目初始化钱设置一下一个属性。在初始化之前加上
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
}


