package com.integration.filter;

import com.integration.config.Audience;
import com.integration.utils.JwtHelper;
import com.integration.utils.RedisUtils;
import com.integration.utils.token.TokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 验证token
 * @program: integration
 * @description
 * @author: hlq
 * @create: 2020-03-05 16:34
 **/
@Component
public class TokenVerification {


    @Autowired
    private Audience audience;

    public boolean TokenVerification(String username){
        Claims t = JwtHelper.parseJWT(username, audience.getBase64Secret());
        if (t != null ) {
            String key = t.get("id").toString();
            String tokenKey = new StringBuilder().append(audience.getPrefix()).append(key).
                    append("-").append(username).toString();
            String logoutKey = new StringBuilder().append("logout-").append(key).
                    append("-").append(username).toString();

            if (RedisUtils.exists(tokenKey)) {
                //验证token成功后，更新过期时间
                RedisUtils.expires(tokenKey);
                if(RedisUtils.exists(logoutKey)){
                    RedisUtils.expires(logoutKey);
                }
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
}
