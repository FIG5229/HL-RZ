package com.integration.utils.token;


import com.integration.config.Audience;
import com.integration.utils.JwtHelper;
import com.integration.utils.RedisUtils;
import com.integration.utils.SpringBeanUtils;
import com.integration.utils.web.ServletUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Token操作工具类
 *
 * @author dell
 */
@Component(value = "tokenUtilsNew")
public class TokenUtils {

	private static Logger logger = LoggerFactory.getLogger(TokenUtils.class);

	private static final Audience audience = (Audience) SpringBeanUtils.getBeanByName("tokenAudience");

	private static String kickOutKey = "token-kick-out";

	/**
	 * 从token里获取用户id
	 *
	 * @param
	 * @return
	 */
	public static String getTokenUserId() {
		try {
			HttpServletRequest request = ServletUtil.getRequest();
			if (StringUtils.isBlank(audience.getBase64Secret())) {
				return "-1";
			}
			String token = request.getHeader("token");
			Claims t = JwtHelper.parseJWT(token, audience.getBase64Secret());
			return t.get("id").toString();
		} catch (Exception e) {
			return "-1";
		}

	}

	/**
	 * 从token里获取用户id
	 *
	 * @param
	 * @return
	 */
	public static String getTokenUserId(String token) {
		try {
			if (StringUtils.isBlank(audience.getBase64Secret())) {
				return "-1";
			}
			Claims t = JwtHelper.parseJWT(token, audience.getBase64Secret());
			return t.get("id").toString();
		} catch (Exception e) {
			return "-1";
		}

	}

	/**
	 * 从token里获取用户登录代码
	 *
	 * @return
	 */
	public static String getTokenUserName(String token) {
		Claims t = JwtHelper.parseJWT(token, audience.getBase64Secret());
		return (t.get("czry_mc").toString());
	}

	/**
	 * 从token里获取用户登录代码
	 *
	 * @return
	 */
	public static String getTokenDldm(String token) {
		if (StringUtils.isBlank(audience.getBase64Secret())) {
			return "system";
		}
		Claims t = JwtHelper.parseJWT(token, audience.getBase64Secret());
		return (t.get("czry_dldm").toString());
	}

	/**
	 * 从token里获取用户登录代码
	 *
	 * @return
	 */
	public static String getTokenDldm() {
		HttpServletRequest request = ServletUtil.getRequest();
		if (StringUtils.isBlank(audience.getBase64Secret())) {
			return "system";
		}
		String token = request.getHeader("token");
		Claims t = JwtHelper.parseJWT(token, audience.getBase64Secret());
		return (t.get("czry_dldm").toString());
	}

	/**
	 * 从token里获取移动端
	 * 1 移动端请求
	 *
	 * @return
	 */
	public static String getTokenMobile(String token) {
		Claims t = JwtHelper.parseJWT(token, audience.getBase64Secret());
		return (t.get("mobile").toString());
	}

	/**
	 * 从token里获取移动端
	 *1 移动端请求
	 *
	 * @return
	 */
	public static String getTokenMobile() {
		HttpServletRequest request = ServletUtil.getRequest();
		if (StringUtils.isBlank(audience.getBase64Secret())) {
			return "0";
		}
		String token = request.getHeader("token");
		Claims t = JwtHelper.parseJWT(token, audience.getBase64Secret());
		return (t.get("mobile").toString());
	}

	/**
	 * 从token里获取用户登录代码
	 *
	 * @return
	 */
	public static String getTokenUserName() {
		String token = ServletUtil.getRequest().getHeader("token");
		Claims t = JwtHelper.parseJWT(token, audience.getBase64Secret());
		return (t.get("czry_dldm").toString());
	}

	/**
	 * 从token里获取部门ID
	 *
	 * @return 部门ID
	 */
	public static String getTokenDeptId() {
		String token = ServletUtil.getRequest().getHeader("token");
		Claims t = JwtHelper.parseJWT(token, audience.getBase64Secret());
		return (t.get("dept_id").toString());
	}
	/**
	 * 从token里获取机构（单位）ID
	 *
	 * @return 机构ID
	 */
	public static String getTokenOrgId() {
		String token = ServletUtil.getRequest().getHeader("token");
		Claims t = JwtHelper.parseJWT(token, audience.getBase64Secret());
		return (t.get("org_id").toString());
	}

	/**
	 * 从token里面获取数据域ID
	 * @return 数据域ID
	 */
	public static String getTokenDomainId() {
		try{
			String token = ServletUtil.getRequest().getHeader("token");
			if(token==null || "".equals(token)) {
				return null;
			}
			Claims t = JwtHelper.parseJWT(token, audience.getBase64Secret());
			return t.get("domainId")==null?null:t.get("domainId").toString();
		}catch (Exception e){
			logger.error("查询默认数据域时出现异常："+e);
			return null;
		}
	}

	/**
	 * 从token里面获取数据域ID
	 * @return 数据域ID
	 */
	public static String getTokenDataDomainId() {
		try{
			String token = ServletUtil.getRequest().getHeader("token");
			if(token==null || "".equals(token)) {
				return null;
			}
			Claims t = JwtHelper.parseJWT(token, audience.getBase64Secret());
			return t.get("dataDomain")==null?null:t.get("dataDomain").toString();
		}catch (Exception e){
			logger.error("查询查询时所用数据域时出现异常："+e);
			return null;
		}
	}
	/**
	 * 从token里面获取数据域ID
	 * @return 数据域ID
	 */
	public static String getTokenOrgDomainId() {
		try{
			String token = ServletUtil.getRequest().getHeader("token");
			if(token==null || "".equals(token)) {
				return null;
			}
			Claims t = JwtHelper.parseJWT(token, audience.getBase64Secret());
			return t.get("orgDomain")==null?null:t.get("orgDomain").toString();
		}catch (Exception e){
			logger.error("查询保存数据时所用数据域出现异常："+e);
			return null;
		}
	}


	public static final String token = "token";
	public static String getToken() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if(attributes!=null){
			HttpServletRequest request = attributes.getRequest();
			return request.getHeader(token);
		}
		return null;
	}

	/**
	 * 从token里获取用户名称
	 *
	 * @return
	 */
	public static String getTokenUserMc() {
		String token = ServletUtil.getRequest().getHeader("token");
		Claims t = JwtHelper.parseJWT(token, audience.getBase64Secret());
		return (t.get("czry_mc").toString());
	}

	/**
	 * 查看账号是否可以登录，如果登录终端数已经是允许最大值，则返回true
	 * @return
	 */
	public static boolean isFull(String id){
		String keyPre = new StringBuilder().append(audience.getPrefix()).append(id).
				append("-").append("*").toString();
		return RedisUtils.size(keyPre) >= audience.getMax();
	}

	public static boolean removeToken(String id) {
		try {
			String keyPre = new StringBuilder().append(audience.getPrefix()).append(id).
					append("-").append("*").toString();
			Set<String> keys = RedisUtils.keys(keyPre);
			String key = keys.iterator().next();
			RedisUtils.remove(key);
			String tokenRemove = key.replace(new StringBuilder().append(audience.getPrefix()).append(id).
					append("-").toString(),"");
			String logoutKey = new StringBuilder().append("logout-").append(id).
					append("-").append(tokenRemove).toString();
			RedisUtils.remove(logoutKey);

			//将删除的key存储起来，做被踢判断
			RedisUtils.setAdd(kickOutKey, tokenRemove);

			return true;
		}catch (Exception e){
			logger.error("删除token失败！", e);
			return false;
		}
	}

	public static boolean isKickOut(String token){
		boolean contains = RedisUtils.setContains(kickOutKey, token);
		if(contains){
			RedisUtils.setRemove(kickOutKey, token);
		}
		return contains;
	}
}
