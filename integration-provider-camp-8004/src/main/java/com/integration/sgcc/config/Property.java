package com.integration.sgcc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author demo
 */
@Component
public class Property {
    @Value("${isc.gateway}")
    String gateway;
    @Value("${isc.appId}")
    String iscAppId;
    @Value("${isc.clientSecret}")
    String iscClientSecret;
    @Value("${isc.serviceUrl}")
    String iscServiceUrl;
    @Value("${request.contentType}")
    String contentType;
    @Value("${request.accept}")
    String accept;
    @Value("${request.contentEncoding}")
    String contentEncoding;

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getIscAppId() {
        return iscAppId;
    }

    public void setIscAppId(String iscAppId) {
        this.iscAppId = iscAppId;
    }

    public String getIscClientSecret() {
        return iscClientSecret;
    }

    public void setIscClientSecret(String iscClientSecret) {
        this.iscClientSecret = iscClientSecret;
    }

    public String getIscServiceUrl() {
        return iscServiceUrl;
    }

    public void setIscServiceUrl(String iscServiceUrl) {
        this.iscServiceUrl = iscServiceUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    @Override
    public String toString() {
        return "Property{" +
                "gateway='" + gateway + '\'' +
                ", iscAppId='" + iscAppId + '\'' +
                ", iscClientSecret='" + iscClientSecret + '\'' +
                ", iscServiceUrl='" + iscServiceUrl + '\'' +
                ", contentType='" + contentType + '\'' +
                ", accept='" + accept + '\'' +
                ", contentEncoding='" + contentEncoding + '\'' +
                '}';
    }
}
