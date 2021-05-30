package com.gxun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MycConfig implements WebMvcConfigurer {

    @Bean
    public LoginHandlerInterceter loginHandlerInterceter(){
        return new LoginHandlerInterceter();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个
      /*  registry.addInterceptor(loginHandlerInterceter())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/","/commodity/queryCommodityListIndex");*/


    }
   /* @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(1800)
                .allowedOrigins("*");
    }*/
}
