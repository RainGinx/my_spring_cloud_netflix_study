package com.springcloud.study.gatewayserver.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.SetStatusGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.setResponseStatus;

@Component
public class LoginFilterPost extends AbstractGatewayFilterFactory<SetStatusGatewayFilterFactory.Config> implements GlobalFilter, Ordered  {

    /**
     * 执行过滤器的业务逻辑
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("执行了全局过滤器");
        //获取token
        String queryToken = exchange.getRequest().getQueryParams().getFirst("access-token");
        String headerToken = exchange.getRequest().getHeaders().getFirst("access-token");
        //验证token
        //如果token为空  无登录
        if (Objects.isNull(queryToken) && Objects.isNull(headerToken)){
            System.out.println("无token，没有登录");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();//请求结束
        }

        //如果token不为空，验证token的时效、合法性等
        //优先从header获取token
        String token = headerToken !=null? headerToken:queryToken;
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders();
            System.out.println("在返回响应前执行的代码");
        }));//继续向下执行
    }

    /**
     * 执行过滤器执行顺序，值越小执行顺序越早
     * @return
     */
    @Override
    public int getOrder() {
        return 10;
    }


    public GatewayFilter apply(SetStatusGatewayFilterFactory.Config config) {
        final HttpStatus status = ServerWebExchangeUtils.parse(config.getStatus());
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                System.out.println("在返回响应前执行的代码");
                // check not really needed, since it is guarded in setStatusCode,
                // but it's a good example
                if (!exchange.getResponse().isCommitted()) {
                    setResponseStatus(exchange, status);
                }
            }));
        };
    }
}
