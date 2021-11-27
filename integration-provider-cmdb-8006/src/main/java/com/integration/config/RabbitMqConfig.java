package com.integration.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RabbitMqConfig
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2021/1/14 15:54
 * @Version 1.0
 **/
@Configuration
public class RabbitMqConfig {



    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //json序列化时，若想手动ACK，则必须配置
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

}
