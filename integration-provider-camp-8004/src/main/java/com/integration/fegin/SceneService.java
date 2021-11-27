package com.integration.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ProjectName: integration
 * @Package: com.integration.fegin
 * @ClassName: SceneService
 * @Author: ztl
 * @Date: 2021-04-19
 * @Version: 1.0
 * @description:场景可视化
 */
@FeignClient(value ="scene", fallbackFactory = SceneServiceFallbackFactory.class)
public interface SceneService {
    @RequestMapping(value = "/sceneJob/sendMessage", method = RequestMethod.POST)
    public void sendMessage();
}
