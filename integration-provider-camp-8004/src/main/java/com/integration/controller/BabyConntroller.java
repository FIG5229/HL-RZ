package com.integration.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.integration.config.Audience;
import com.integration.entity.AscKEY;
import com.integration.entity.PageResult;
import com.integration.service.SubsystemService;
import com.integration.service.UserMappingService;
import com.integration.utils.*;
import com.integration.utils.token.TokenUtils;
import io.jsonwebtoken.Claims;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.controller
* @ClassName: BabyConntroller
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description:
*/
@RestController
public class BabyConntroller {
	private static final Logger logger = LoggerFactory.getLogger(BabyConntroller.class);
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private UserMappingService umService;
	@Autowired
	private Audience audience;
	@Autowired
	private AscKEY asckey;
	@Autowired
	private SubsystemService subsystemService;

	/**
	 * 验证token
	 *
	 * @return
	 */
	@PostMapping(value = "/VerificationTicket")
	public String VerificationTicket(String ticket,String identification) {

		Map resultMap = new HashMap();
		Claims t = JwtHelper.parseJWT(ticket, audience.getBase64Secret());
		String id = t.get("id").toString();
		byte[] jiami=null;
		String jiami16=null;
		String cc=null;
		String ticketKey = new StringBuilder().append("ticket").append(id).
				append("-").append(ticket).toString();
		Object tic=RedisUtils.get(ticketKey);
		if(!"".equals(ticket) && ticket!=null){
			if(tic.equals(ticket)){
				try {
					//获取配置文件里的密钥
					String key=asckey.getAscKey();
					//获取用户信息
					Map userInfo=umService.findPassWordByID(id,identification);
					Map infoMap = new HashMap();
					String userName = userInfo.get("MAPPING_DLDM").toString();
					String pwd=AESUtils.Decrypt(userInfo.get("MAPPING_PASSWORD").toString(), key);
					infoMap.put("userName", userName);
					infoMap.put("passWord", pwd);
					//加密
					jiami=AESUtils.getAESEncode(key,JSON.toJSONString(infoMap));
					//转成16进制
					jiami16=AESUtils.parseByte2HexStr(jiami);
					resultMap.put("code", "200");
					resultMap.put("message", jiami16);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
					resultMap.put("code", "500");
					resultMap.put("message", "ticket验证失败，请联系管理员！");
				}
			}
		}
		return JSON.toJSONString(resultMap);



	}


	/**
	 * 验证token
	 *
	 * @return
	 */
	@PostMapping(value = "/VerificationTicketforBody")
	public String VerificationTicketforBody(@RequestBody String ticket,@RequestBody String identification) {

		Map resultMap = new HashMap();
		Claims t = JwtHelper.parseJWT(ticket, audience.getBase64Secret());
		String id = t.get("id").toString();
		byte[] jiami=null;
		String jiami16=null;
		String cc=null;
		String ticketKey = new StringBuilder().append("ticket").append(id).
				append("-").append(ticket).toString();
		Object tic=RedisUtils.get(ticketKey);
		if(!"".equals(ticket) && ticket!=null){
			if(tic.equals(ticket)){
				try {
					//获取配置文件里的密钥
					String key=asckey.getAscKey();
					//获取用户信息
					Map userInfo=umService.findPassWordByID(id,identification);
					Map infoMap = new HashMap();
					String userName = userInfo.get("MAPPING_DLDM").toString();
					String pwd=AESUtils.Decrypt(userInfo.get("MAPPING_PASSWORD").toString(), key);
					infoMap.put("userName", userName);
					infoMap.put("passWord", pwd);
					//加密
					jiami=AESUtils.getAESEncode(key,JSON.toJSONString(infoMap));
					//转成16进制
					jiami16=AESUtils.parseByte2HexStr(jiami);
					resultMap.put("code", "200");
					resultMap.put("message", jiami16);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
					resultMap.put("code", "500");
					resultMap.put("message", "ticket验证失败，请联系管理员！");
				}
			}
		}
		return JSON.toJSONString(resultMap);



	}


	/**
	 * 验证token
	 *
	 * @return
	 */
	@PostMapping(value = "/VerificationTicketforJson")
	public String VerificationTicket(@RequestBody String json) {

		Map resultMap = new HashMap();
		Map jsonMap = JSONObject.parseObject(json);
		String ticket = jsonMap.get("ticket").toString();
		String identification = jsonMap.get("identification").toString();
		Claims t = JwtHelper.parseJWT(ticket, audience.getBase64Secret());
		String id = t.get("id").toString();
		byte[] jiami=null;
		String jiami16=null;
		String cc=null;
		String ticketKey = new StringBuilder().append("ticket").append(id).
				append("-").append(ticket).toString();
		Object tic=RedisUtils.get(ticketKey);
		if(!"".equals(ticket) && ticket!=null){
			if(tic.equals(ticket)){
				try {
					//获取配置文件里的密钥
					String key=asckey.getAscKey();
					//获取用户信息
					Map userInfo=umService.findPassWordByID(id,identification);
					Map infoMap = new HashMap();
					String userName = userInfo.get("MAPPING_DLDM").toString();
					String pwd=AESUtils.Decrypt(userInfo.get("MAPPING_PASSWORD").toString(), key);
					infoMap.put("userName", userName);
					infoMap.put("passWord", pwd);
					//加密
					jiami=AESUtils.getAESEncode(key,JSON.toJSONString(infoMap));
					//转成16进制
					jiami16=AESUtils.parseByte2HexStr(jiami);
					resultMap.put("code", "200");
					resultMap.put("message", jiami16);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
					resultMap.put("code", "500");
					resultMap.put("message", "ticket验证失败，请联系管理员！");
				}

			}
		}
		return JSON.toJSONString(resultMap);



	}

	@PostMapping(value = "/VerificationTicket1")
	public String zzzzqwe(String baby,String key){
		String a= AESUtils.Decrypt(baby, key);
		return a ;
	}


	/**
	 * 防火墙策略-获取命中数为零的策略集合
	 * @return
	 */
	@GetMapping(value = "getHitZeroCountsOverstep")
	public  PageResult getHitZeroCountsOverstep(HttpServletRequest req){
		String url="http://119.162.123.28:8085/api/getHitZeroCountsOverstep";

		String  data=goToFirstPage(null,url,"get",req);
		if(data !=null){
			Map map=JSONObject.parseObject(data);
			List<Map> objMap=JSON.parseArray(map.get("obj").toString(),Map.class);
			return DataUtils.returnPr(objMap);
		}
		return DataUtils.returnPr(data);
	}


	/**
	 * 防火墙策略-单台策略下发接口
	 * @return
	 */
	@PostMapping(value = "policyIssued")
	public  PageResult policyIssued(HttpServletRequest req,String paramMap){
		String url="http://119.162.123.28:8085/api/policyIssued";

		String  data=goToFirstPage(paramMap,url,"post",req);
		if(data !=null){
			Map map=JSONObject.parseObject(data);
			String id=map.get("obj").toString();
			String param="{\"policyId\":\""+id+"\"}";
			String result=goToFirstPage(param,"http://119.162.123.28:8085/api/checkPolicyIssuedResult","get",req);
			Map resultMap=JSONObject.parseObject(result);
			Map attributes=JSONObject.parseObject(resultMap.get("obj").toString());
			return DataUtils.returnPr(attributes);
		}
		return DataUtils.returnPr(data);
	}

	/**
	 *  防火墙策略-获取防火墙列表接口
	 * @return
	 */
	@GetMapping(value = "getFirewallList")
	public  PageResult getFirewallList(HttpServletRequest req,String params){
		//firewallName：防火墙名称 manageIp：管理地址
		String url="http://119.162.123.28:8085/api/getFirewallList";
		String  data=goToFirstPage(params,url,"get",req);
		if(data !=null){
			Map map=JSONObject.parseObject(data);
			List<Map> objMap=JSON.parseArray(map.get("obj").toString(),Map.class);
			return DataUtils.returnPr(objMap);
		}
		return DataUtils.returnPr(data);
	}

	/**
	 * 调用第三方公用接口
	 * @return
	 * @throws
	 */
	@GetMapping(value = "goToFirstPage")
	public String goToFirstPage(String paramsMap,String url,String request,HttpServletRequest req){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		if(!"".equals(req.getHeader("Token")) && req.getHeader("Token")!=null ){
			headers.add("token", req.getHeader("token"));
		}
		Map map=new HashMap();
		if(paramsMap !=null && !"".equals(paramsMap)){
			map = JSON.parseObject(paramsMap,Map.class);
		}
		if("get".equals(request)){
			HttpEntity<String> requestEntity = new HttpEntity(headers);
			URIBuilder uriBuilder=null;
			try {
				uriBuilder = new URIBuilder(url);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
			if(paramsMap !=null && !"".equals(paramsMap)){
				for (Object key : map.keySet()) {
					uriBuilder.addParameter(key.toString(), map.get(key).toString());
				}
			}


			ResponseEntity<String> responseEntity =restTemplate.
					exchange(uriBuilder.toString(), HttpMethod.GET,requestEntity,String.class);
			String result = responseEntity.getBody();

			return result;
		}else{
			HttpEntity<String> formEntitys;
			if(paramsMap !=null && !"".equals(paramsMap)){
				JSONObject jsonObj = JSON.parseObject(paramsMap);
				formEntitys = new HttpEntity(jsonObj.toString(), headers);
			}else{
				formEntitys = new HttpEntity( headers);
			}
			ResponseEntity<byte[]> requests = restTemplate
					.postForEntity(url, formEntitys, byte[].class);
			byte[] messages = requests.getBody();
			String messageConvert="";
			try {
				messageConvert = new String(messages, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
			return messageConvert;

		}
	}

	  /**
      * 根据登录人获取特权账号
      *
      * @return
      */
      @GetMapping(value = "/getUserInfoByLoginId")
 	  public String getUserInfoByLoginId(HttpServletRequest req) {
    	  String userId=TokenUtils.getTokenUserId();
    	  String sub="30071953339596800";
    	  Map map=new HashMap();
    	  String data="";
		try {
			map = subsystemService.getUserInfoByLoginId(userId,sub);
			String pwd=map.get("MAPPING_PASSWORD").toString();
			String newPwd=AESUtils.Decrypt(pwd, "303EAA507626351F22B71C086E998F49");
			map.put("MAPPING_PASSWORD", newPwd);
			String json=JSON.toJSONString(map);
			data= AesEncryptUtil.encrypt(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}

    	  return data;
      }
}


