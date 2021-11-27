package com.integration.sgcc.service;

import com.alibaba.fastjson.JSONObject;
import com.integration.sgcc.config.Property;
import com.integration.sgcc.sm.SM3Util;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author demo
 */
@Service
public class IscQueryInfo {
    private Property property;

    public IscQueryInfo(Property property){
        this.property=property;
    }

    public String getAccessToken() throws Exception {
        String at = "Client ";
        String url=property.getGateway()+"/zuul/sgid-provider-console/res/iscMincroService/getAccessToken";
        String returnValue = "这是默认返回值，接口调用失败";
        JSONObject iscInfo=new JSONObject();
        iscInfo.put("appId",property.getIscAppId());
        iscInfo.put("clientSecret",property.getIscClientSecret());

        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            // 第一步：创建HttpClient对象
            httpClient = HttpClients.createDefault();
            // 第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(url);
            // 设置头信息
            httpPost.setHeader("Content-Type", property.getContentType());
            httpPost.setHeader("Accept", property.getAccept());
            SM3Util sm3Util=new SM3Util();
            String digest=sm3Util.SM3Digest(iscInfo.toJSONString().replace("\\", ""));
            httpPost.setHeader("X-Acloud-Data-Sign",digest);
            httpPost.setHeader("X-Clientid", property.getIscAppId());
            // 第三步：给httpPost设置JSON格式的参数
            StringEntity requestEntity = new StringEntity(iscInfo.toJSONString(), property.getContentEncoding());
            requestEntity.setContentEncoding(property.getContentEncoding());
            httpPost.setEntity(requestEntity);

            // 第四步：发送HttpPost请求，获取返回值
            // 调接口获取返回值时，必须用此方法
            returnValue = httpClient.execute(httpPost, responseHandler);
            JSONObject jsStr = JSONObject.parseObject(returnValue);
            JSONObject d = jsStr.getJSONObject("data");
            at=at+d.get("accessToken").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return at;
    }


    public String getUserInfoByTicket(String st) throws Exception {
        //服务uri
        String getUserInfoByTicket=property.getGateway()+"/zuul/sgid-frontmv/identity/getUserInfoByTicket";
        String returnValue = "这是默认返回值，接口调用失败";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            JSONObject iscInfo=new JSONObject();
            iscInfo.put("appId",property.getIscAppId());
            iscInfo.put("service",property.getIscServiceUrl());
            iscInfo.put("ticket",st);
            // 第一步：创建HttpClient对象
            httpClient = HttpClients.createDefault();
            // 第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(getUserInfoByTicket);
            String at = this.getAccessToken();
            System.out.println(at);
            // 设置头信息
            httpPost.setHeader("Content-Type", property.getContentType());
            httpPost.setHeader("Accept", property.getAccept());
            httpPost.setHeader("X-ISC-AccessToken", at);
            httpPost.setHeader("X-Clientid", property.getIscAppId());

            // 第三步：给httpPost设置JSON格式的参数
            SM3Util sm3Util=new SM3Util();
            String digest=sm3Util.SM3Digest(iscInfo.toJSONString().replace("\\", ""));
            httpPost.setHeader("X-Acloud-Data-Sign",digest);
            StringEntity requestEntity = new StringEntity(iscInfo.toJSONString(), property.getContentEncoding());
            requestEntity.setContentEncoding(property.getContentEncoding());
            httpPost.setEntity(requestEntity);
            // 第四步：发送HttpPost请求，获取返回值
            // 调接口获取返回值时，必须用此方法
            returnValue = httpClient.execute(httpPost, responseHandler);
            System.out.println("结果>"+returnValue);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return returnValue;
    }
}
