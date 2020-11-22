package com.springcloud.study.gatewayserver.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//@Configuration
public class KeyResolverConfiguration {

    /**
     * 1 基于路径
     * 2 基于ip
     * 3 基于参数
     *
     */
    @Bean
    public KeyResolver pathKeyResolver(){
        //自定义KeyResolver
        return new KeyResolver() {
            /**
             * ServerWebExchange: 上下文参数
             * @param exchange
             * @return
             */
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                //配置路径规则
                return Mono.just(exchange.getRequest().getPath().toString());
            }
        };
    }
    /**
     * 基于请求ip地址的限流
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(
                exchange.getRequest().getHeaders().getFirst("X-Forwarded-For")
        );
    }
    /**
     * 基于参数的限流
     * 请求中包含参数user是会触发令牌桶
     */
    @Bean
    @Primary
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(
                exchange.getRequest().getQueryParams().getFirst("user")
        );
    }
}
