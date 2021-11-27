package com.integration.feign;

import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

@Component
public class RobotServiceFallbackFactory implements FallbackFactory<RobotService> {
    @Override
    public RobotService create(Throwable cause) {
        return new RobotService() {
            @Override
            public void receiveMessage(String content) {
                System.out.println("维秘消息推送异常");
            }

            @Override
            public Object updatePersonnelOnGroupState(String personnelId, String groupId) {
                return null;
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
    }
}
