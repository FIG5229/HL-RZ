package com.integration.service;

import com.integration.entity.PageResult;

/**
 * @ClassName LisenceService
 * @Description 授权服务
 * @Author zhangfeng
 * @Date 2021/8/9 15:44
 * @Version 1.0
 **/
public interface LisenceService {

    /**
     * 授权检测
     * @return
     */
    PageResult authCodeCheck();
}
