package com.springcloud.study.ordersentinel.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.springcloud.study.ordersentinel.utils.SentinelExceptionUtils;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    /**
     * sentinel对resttemplate的支持，只要在构造RestTemplate对象的时候，使用@SentinelRestTemplate注解即可
     * 使用这种方式时，在sentinel的控制台的资源名有两种方式
     *  1. httpmethod:schema://host:port/path --协议、主机、端口和路径
     *  2. httpmethod:schema://host:port      --协议、主机、端口
     * SentinelRestTemplate
     *  fallback:降级方法
     *  fallbackClass：降级配置类
     */
    @Bean
    @SentinelRestTemplate(fallbackClass = SentinelExceptionUtils.class,fallback = "handleFallback",
            blockHandler = "handleBlock" ,blockHandlerClass = SentinelExceptionUtils.class
    )
    @LoadBalanced//ribbon的负载均衡 服务调用
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
