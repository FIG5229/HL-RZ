package com.integration.entity;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;
import com.integration.utils.DataUtils;
import com.integration.utils.MyPagUtile;
import com.integration.utils.web.ServletUtil;

public class PageResult extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int showCount; // 每页显示记录数
	private int totalPage; // 总页数
	private int totalResult; // 总记录数
	private int currentPage; // 当前页
	private int currentResult; // 当前记录起始索引
	private boolean entityOrField; // true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属性

	private String name;
	private boolean returnBoolean;// 方法执行是否成功
	private String returnMessage;// 方法执行提示信息
	private String returnCode;// 返回编码
	private Object returnObject;// 方法返回结果
	private String returnExceptionMsg;// 此次错误的堆栈信息

	/**
	 * 成功
	 * 
	 */
	public static void success() {
		setMessage("成功", true);
	}

	/**
	 * 成功
	 * 
	 * @param returnMessage
	 */
	public static void success(String returnMessage) {
		setMessage(returnMessage, true);
	}

	/**
	 * 失败
	 * 
	 */
	public static void fail() {
		setMessage("失败", false);
	}

	/**
	 * 失败
	 * 
	 * @param returnMessage
	 */
	public static void fail(String returnMessage) {
		setMessage(returnMessage, false);
	}

	public static void setMessage(boolean returnBoolean, String returnMessage) {
		setMessage(returnMessage, returnBoolean);
	}

	public static void setMessage(String returnMessage, boolean returnBoolean) {
		HttpServletResponse response = ServletUtil.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.addHeader("returnMessage", str2HexStr(returnMessage));
		response.addHeader("returnBoolean", String.valueOf(returnBoolean));
	}
	
	private static final String myStr = "0123456789ABCDEF";
	
	/**
	 * 字符串转换成为16进制(无需Unicode编码)
	 * 
	 * @param str
	 * @return
	 */
	public static String str2HexStr(String str) {
		char[] chars = myStr.toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
			// sb.append(' ');
		}
		return sb.toString().trim();
	}
 
	/**
	 * 16进制直接转换成为字符串(无需Unicode解码)
	 * 
	 * @param hexStr
	 * @return
	 */
	public static String hexStr2Str(String hexStr) {
		String str = myStr;
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;
		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}

	public static String getPageResultString(String body, String returnMessage, String returnBoolean,boolean isString) {
		PageResult pageResult = null;
		Object object = null;
		try {
			object = JSON.parse(body,Feature.OrderedField); // OrderedField 按照原始map的key值排序
		} catch (JSONException e) {
			object = body;
		}

		// 因为
		if (object instanceof JSONObject) {
			@SuppressWarnings("unchecked")
			JSONObject jsonObject = (JSONObject) object;
			if (isPageResult(jsonObject)) {
				// 返回值本身就是一个PageResult
				try {
					pageResult = JSON.parseObject(body, PageResult.class,Feature.OrderedField);
				} catch (Exception e) {
					pageResult = DataUtils.returnPr(object, "成功");
				}
			} else if (isPageInfo(jsonObject)) {
				// 返回值是一个pageInfo
				try {
					@SuppressWarnings("rawtypes")
					PageInfo pageInfo = JSON.parseObject(body, PageInfo.class);
					pageResult = MyPagUtile.getPageResult(pageInfo);
				} catch (Exception e) {
					pageResult = DataUtils.returnPr(object, "成功");
				}
			} else if (isDefultErrResult(jsonObject)) {
				return body;
			} else {
				pageResult = DataUtils.returnPr(object, "成功");
			}
		} else {
			//防止id超长
			if (isString || object instanceof Long && body.length()>16) {
				object = String.valueOf(object);
			}
			pageResult = DataUtils.returnPr(true, "成功", object);
		}
		if (StringUtils.isNotEmpty(returnMessage)) {
			pageResult.setReturnMessage(hexStr2Str(returnMessage));
		}
		if (StringUtils.isNotEmpty(returnBoolean)) {
			pageResult.setReturnBoolean(Boolean.valueOf(returnBoolean));
		}
		return JSON.toJSONString(pageResult,SerializerFeature.PrettyFormat,
				SerializerFeature.WriteMapNullValue);
	}
	
	public static boolean isPageResult(JSONObject jsonObject) {
		return jsonObject.containsKey("returnBoolean") && jsonObject.containsKey("returnMessage")
				&& jsonObject.containsKey("returnCode");
	}

	private static boolean isDefultErrResult(JSONObject jsonObject) {
		return jsonObject.containsKey("exception") && jsonObject.containsKey("error")
				&& jsonObject.containsKey("message") && jsonObject.containsKey("status");
	}

	private static boolean isPageInfo(JSONObject jsonObject) {
		return jsonObject.containsKey("pageSize") && jsonObject.containsKey("list") && jsonObject.containsKey("total")
				&& jsonObject.containsKey("pageNum") && jsonObject.containsKey("pages");
	}

	public int getShowCount() {
		return showCount;
	}

	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCurrentResult() {
		return currentResult;
	}

	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}

	public boolean isEntityOrField() {
		return entityOrField;
	}

	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isReturnBoolean() {
		return returnBoolean;
	}

	public void setReturnBoolean(boolean returnBoolean) {
		this.returnBoolean = returnBoolean;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public Object getReturnObject() {
		return returnObject;
	}

	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}

	public String getReturnExceptionMsg() {
		return returnExceptionMsg;
	}

	public void setReturnExceptionMsg(String returnExceptionMsg) {
		this.returnExceptionMsg = returnExceptionMsg;
	}

}
