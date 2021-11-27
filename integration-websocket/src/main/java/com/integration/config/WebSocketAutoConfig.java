package com.integration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * @ClassName WebSocketAutoConfig
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2020/7/30 14:00
 * @Version 1.0
 **/
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketAutoConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private MyHandshakeInterceptor myHandshakeInterceptor;
    @Autowired
    private MyDefaultHandshakeHandler myDefaultHandshakeHandler;


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //开启/bullet端点
        registry.addEndpoint("/websocket/**")
                //允许跨域访问
                .setAllowedOrigins("*")
                .setHandshakeHandler(myDefaultHandshakeHandler)
                .addInterceptors(myHandshakeInterceptor);
                //使用sockJS
                //.withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //订阅Broker名称
        registry.setUserDestinationPrefix("/toSingle");
        //开放订阅 /toAll 推送所有，toSingle推送指定人
        registry.enableSimpleBroker("/toAll","/toSingle");
    }

    /**
     * 消息传输参数配置
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        //设置消息字节数大小
        registry.setMessageSizeLimit(8192)
                //设置消息缓存大小
                .setSendBufferSizeLimit(8192)
                //设置消息发送时间限制毫秒
                .setSendTimeLimit(10000);
    }

}
