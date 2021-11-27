package com.integration.utils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.integration.entity.PageResult;
/**
* @Package: com.integration.utils
* @ClassName: MyPagUtile
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 分页工具类
*/
public class MyPagUtile {
	/**
	 * 外界传入的页码
	 */
	public static final String PAGE_NUM = "pageNum";
	/**
	 * 外界传入的每页大小
	 */
	public static final String PAGE_SIZE = "pageSize";
	
	public static void startPage(Object pageNum, Object pageSize) {
		Integer num = null;
		Integer size = null;
		if (pageNum != null) {
			num = Integer.parseInt(pageNum.toString());
		}

		if (pageSize != null) {
			size = Integer.parseInt(pageSize.toString());
		}

		startPage(num, size);
	}

	public static void startPage() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		Object pageNumObj = request.getAttribute(PAGE_NUM);
		Object pageSizeObj = request.getAttribute(PAGE_SIZE);
		if (pageNumObj==null) {
			pageNumObj = request.getParameter(PAGE_NUM);
		}
		if (pageSizeObj==null) {
			pageSizeObj = request.getParameter(PAGE_SIZE);
		}
		startPage(pageNumObj, pageSizeObj);
	}
	
	/**
	 * 分页处理
	 * 
	 * @param pageNum
	 * @param pageSize
	 */
	public static void startPage(Integer pageNum, Integer pageSize) {
		if (pageNum == null) {
			pageNum = 1;
		}

		if (pageSize == null) {
			pageSize = 10;
		}

		PageHelper.startPage(pageNum, pageSize);
	}
	
	public static PageResult getPageResult(List pageInfo) {
		return getPageResult(new PageInfo(pageInfo));
	}
	
	public static PageResult getPageResult(PageInfo pageInfo) {
		PageResult pageResult=new PageResult();
		pageResult.setTotalPage(pageInfo.getPages());    
		pageResult.setTotalResult(Integer.parseInt(pageInfo.getTotal()+""));
		pageResult.setCurrentPage(pageInfo.getPageNum());
		pageResult.setReturnObject(pageInfo.getList());
		pageResult.setReturnBoolean(true);
		pageResult.setReturnMessage("成功");
		return pageResult;
	}

}
