package com.integration.config;

import java.security.Principal;

/**
 * @ClassName User
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2020/7/30 15:45
 * @Version 1.0
 **/
public class User implements Principal {

    private final String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
