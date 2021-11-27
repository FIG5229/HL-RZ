package com.integration.utils;

import com.integration.entity.PageResult;

/**
 * @Author:Starry
 * @Description:
 * @Date:Created in 9:46 2018/4/13 Modified By:
 */
public class DataUtils {

	/**
	 * 增删改结果封装给前端
	 *
	 * @param flag
	 * @return
	 */
	public static PageResult returnPr(boolean flag) {
		return returnPr(flag, "", null, 0);
	}

	/**
	 * 查询结果封装给前端
	 *
	 * @param o
	 * @return
	 */
	public static PageResult returnPr(Object o) {
		return returnPr(true, "", o, 0);
	}

	/**
	 * 查询结果封装给前端,加上提示消息
	 *
	 * @param o
	 * @return
	 */
	public static PageResult returnPr(Object o, String message) {
		if (o == null) {
			return returnPr(false, message, o, 0);
		}
		return returnPr(true, message, o, 0);
	}

	/**
	 * 分页查询结果封装给前端
	 *
	 * @param totalRecord
	 * @param o
	 * @return
	 */
	public static PageResult returnPr(int totalRecord, Object o) {

		return returnPr(true, "", o, totalRecord);
	}

	/**
	 * 操作提示消息封装给前端
	 * 
	 * @param bl
	 * @param message
	 * @return
	 */
	public static PageResult returnPr(boolean bl, String message) {
		return returnPr(bl, message, null, 0);
	}

	/**
	 * 添加后返回添加后的实体
	 * 
	 * @param flag
	 * @param message
	 * @param resultObject
	 * @return
	 */
	public static PageResult returnPr(boolean flag, String message,
			Object resultObject) {
		return returnPr(flag, message, resultObject, 0);
	}

	/**
	 * 前后端交互实体
	 * 
	 * @param bl
	 * @param message
	 * @param resultObject
	 * @param total
	 * @return
	 */
	public static PageResult returnPr(boolean bl, String message,
			Object resultObject, int total) {
		PageResult pr = new PageResult();
		pr.setReturnBoolean(bl);
		pr.setReturnMessage(message);
		pr.setReturnObject(resultObject);
		pr.setTotalResult(total);
		return pr;
	}
	
	/**
	 * 带有错误堆栈信息的返回体
	 * @param bl
	 * @param message
	 * @param resultObject
	 * @param total
	 * @param returnExceptionMsg
	 * @return
	 */
	public static PageResult returnPr(boolean bl, String message,
			Object resultObject, int total, String returnExceptionMsg) {
		PageResult pr = new PageResult();
		pr.setReturnBoolean(bl);
		pr.setReturnCode(bl?"200":"500");
		pr.setReturnMessage(message);
		pr.setReturnExceptionMsg(returnExceptionMsg);
		pr.setReturnObject(resultObject);
		pr.setTotalResult(total);
		return pr;
	}

	/**
	 * 
	 * 外部系统调用返回实体
	 * 
	 * @param code
	 * @param message
	 * @param resultObject
	 * @param total
	 * @return
	 */
	public static PageResult returnPrExt(String code, String message,
			Object resultObject, int total) {
		PageResult pr = new PageResult();
		pr.setReturnCode(code);
		pr.setReturnMessage(message);
		pr.setReturnObject(resultObject);
		pr.setTotalResult(total);
		return pr;
	}

	/**
	 * 初始化方法
	 *
	 * @return
	 */
	public static PageResult defaultPageResult() {
		PageResult pageResult = new PageResult();
		pageResult.setReturnBoolean(false);
		return pageResult;
	}

}