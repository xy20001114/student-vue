package com.xueyong.springbootapi.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class MyConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**/")
                .addResourceLocations("file:D:\\pic\\");
    }
    /**
     * 跨域：协议不同、主机不同、端口不同、路径不同时候，ajax请求是需要配置的。
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //允许所有来源
                .allowedOrigins("*")
                //允许所有方法：Get、Post、Put、Delete
                .allowedMethods("*")
                //允许所有的头信息
                .allowedHeaders("*")
                //是否需要跨域验证
                .allowCredentials(true)
                //认证过期时间：单位秒
                .maxAge(3600);
    }
}

