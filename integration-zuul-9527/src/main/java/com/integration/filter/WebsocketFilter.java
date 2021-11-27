package com.integration.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName WebsocketFilter
 * @Description //这个类可以做websocket权限验证，针对socketjs的info请求，截取token做验证即可
 * @Author zhangfeng
 * @Date 2020/8/7 9:35
 * @Version 1.0
 **/
@Component
public class WebsocketFilter implements GlobalFilter, Ordered {

    Logger log = LoggerFactory.getLogger(WebsocketFilter.class);

    @Resource
    private TokenVerification tokenVerification;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(exchange.getRequest().getURI().getPath().equals("/websocket/info")){
            List<String> valueList = exchange.getRequest().getQueryParams().get("token");
            if(valueList != null && valueList.size() > 0){
                String token = valueList.get(0);
                log.info(token);
                if(!tokenVerification.TokenVerification(token)){
                    return Mono.error(new RuntimeException("连接失败"));
                }
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
