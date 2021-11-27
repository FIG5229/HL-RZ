package com.integration.utils;

import javax.servlet.http.HttpServletRequest;

import com.integration.exceptions.PageParamException;
import com.integration.mybatis.entity.Page;
import com.integration.utils.web.ServletUtil;

/**
 * 对分页进行操作的工具类
 * 
 * @author dell
 *
 */
public class PageUtils {

	/**
	 * 获取page对象
	 * 
	 * @return
	 */
	public static Page getPage() {
		HttpServletRequest request = ServletUtil.getRequest();
		String pageIndexStr = request.getParameter("pageIndex");
		String pageCountStr = request.getParameter("pageCount");
		
		/*
		 * 验证是否符合规则，不符合抛出异常
		 */
		if(!RegexUtil.validPositiveInt(pageIndexStr) || !RegexUtil.validPositiveInt(pageCountStr)) {
			throw new PageParamException("pageIndex或pageCount非正整数");
		}
		
		int pageIndex = Integer.parseInt(pageIndexStr);
		int pageCount = Integer.parseInt(pageCountStr);
		
		return new Page(pageIndex, pageCount);
	}
}
