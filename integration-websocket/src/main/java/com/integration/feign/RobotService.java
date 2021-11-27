package com.integration.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "robot", fallbackFactory = RobotServiceFallbackFactory.class)
public interface RobotService {

    @RequestMapping(value = "/receiveMessage", method = RequestMethod.POST)
    public void receiveMessage(@RequestBody String content);

    @RequestMapping(value = "/discussionGroup/updatePersonnelOnGroupState", method = RequestMethod.POST)
    public Object updatePersonnelOnGroupState(@RequestParam("personnelId") String personnelId, @RequestParam("groupId") String groupId);
}
