package com.springcloud.study.ordersentinel.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;

/**
 * @Description：Ribbon 全局的负载均衡策略配置类.
 */
//@Configuration
public class RibbonGlobalLoadBalancingConfiguration {

    /**
     * 随机规则
     */
    @Bean
    public IRule ribbonRule() {
        return new RandomRule();
    }
}
