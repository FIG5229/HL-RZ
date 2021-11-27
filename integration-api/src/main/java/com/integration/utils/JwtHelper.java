package com.integration.utils;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtHelper {
	/**
     * 解析jwt
     */
    public static Claims parseJWT(String jsonWebToken, String base64Security){
        try
        {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        }
        catch(Exception ex)
        {
            return null;
        }
    }

    /**
     * 构建jwt
     */
    public static String createJWT(Long id,String mc,String czry_dldm,String dept_id,String org_id,String domainId,String audience, String issuer, long TTLMillis, String base64Security,String dataDomain,String orgDomain, String mobile)
    {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("id", id)
                .claim("czry_mc", mc)
                .claim("czry_dldm", czry_dldm)
                .claim("dept_id",dept_id)
                .claim("org_id",org_id)
                .claim("domainId",domainId)
                .claim("createTime",System.nanoTime())
                .claim("dataDomain",dataDomain)
                .claim("orgDomain",orgDomain)
                .claim("mobile",mobile)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(signatureAlgorithm, signingKey);

        //ID,CZRY_DM,CZRY_DLDM,CZRY_MC
        //添加Token过期时间
        //屏蔽jwt过期时间，采用redis统一控制
        /*if (TTLMillis >= 0) {
            long expMillis = nowMillis + TTLMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }*/

        //生成JWT
        return builder.compact();
    }

    /**
     * 构建jwt
     */
    public static String createJWT(Long id,String mc,String czry_dldm,String dept_id,String org_id,String domainId,String audience, String issuer, long TTLMillis, String base64Security,String dataDomain,String orgDomain)
    {
        return createJWT(id, mc, czry_dldm, dept_id, org_id, domainId, audience, issuer, TTLMillis, base64Security, dataDomain, orgDomain, "0");
    }

    /**
     * 构建jwt
     */
    public static String createJWT(Long id,String czry_dldm,String dept_id,String org_id,String audience, String issuer, long TTLMillis, String base64Security)
    {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("id", id)
                .claim("czry_dldm", czry_dldm)
                .claim("dept_id",dept_id)
                .claim("org_id",org_id)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(signatureAlgorithm, signingKey);

        //ID,CZRY_DM,CZRY_DLDM,CZRY_MC
        //添加Token过期时间
        if (TTLMillis >= 0) {
            long expMillis = nowMillis + TTLMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        //生成JWT
        return builder.compact();
    }
}
