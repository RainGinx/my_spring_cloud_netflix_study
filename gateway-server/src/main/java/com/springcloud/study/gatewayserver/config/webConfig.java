package com.springcloud.study.gatewayserver.config;

import com.springcloud.study.gatewayserver.filter.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class webConfig {
    @Bean
    public LoginFilter getLoginFilter(){
        return new LoginFilter();
    }
}
