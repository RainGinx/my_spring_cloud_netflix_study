package com.springcloud.study.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.ZuulFilterResult;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

/**
 * 自定义zuul过滤器
 *  继承抽象父类
 */
@Component
public class LoginFilter extends ZuulFilter {
    /**
     * 定义过滤器类型
     * pre
     * routing
     * post
     * error
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 指定过滤器的执行顺序
     *  返回值越小，执行顺序越高
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 当前过滤器是否生效
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 执行过滤器中的业务逻辑
     *  身份认证
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("执行了过滤器");
        return null;
    }
}
