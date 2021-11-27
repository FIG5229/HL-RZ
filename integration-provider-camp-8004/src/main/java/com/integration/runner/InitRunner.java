package com.integration.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.integration.service.DictService;

/**
* @Package: com.integration.runner
* @ClassName: InitRunner
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description:
*/
@Component
public class InitRunner implements ApplicationRunner{
	
    @Autowired
    private DictService dictService;
   

	@Override
	public void run(ApplicationArguments args) throws Exception {

		dictService.redisDict();
		
	}

}
