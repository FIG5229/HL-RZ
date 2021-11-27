package com.integration.mybatis.entity;

/**
 * 分页对象
 * 
 * @author dell
 *
 */
public class Page {

	// 当前页
	private int currentPage;
	// 实际当前页，矫正传入的当前页数值越界问题
	private int realCurPage;
	// 每页大小
	private int pageSize;
	// 总记录数
	private int totalResult;
	// 偏移量
	private int offset;
	// 总页数
	private int totalPage;
	
	/**
	 * 获取当前页
	 * 
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	/**
	 * 获取实际当前页，矫正传入的当前页数值越界问题
	 * 
	 * @return
	 */
	public int getRealCurPage() {
		return realCurPage;
	}
	/**
	 * 获取每页大小
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * 获取总记录数
	 * 
	 * @return
	 */
	public int getTotalResult() {
		return totalResult;
	}
	/**
	 * 传入总记录数
	 * 
	 * @param totalResult
	 */
	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
		
		if(totalResult%pageSize == 0) {
			totalPage = totalResult/pageSize;
		} else {
			totalPage = (totalResult/pageSize)+1;
		}
		
		realCurPage = Math.min(currentPage, totalPage);
		offset = Math.max(realCurPage-1, 0)*pageSize;
	}
	
	/**
	 * 获取偏移量
	 * 
	 * @return
	 */
	public int getOffset() {
		return offset;
	}
	
	/**
	 * 获取总页数
	 * 
	 * @return
	 */
	public int getTotalPage() {
		return totalPage;
	}
	
	public Page(int currentPage, int pageSize) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}
	
	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", realCurPage=" + realCurPage + ", pageSize=" + pageSize
				+ ", totalResult=" + totalResult + ", offset=" + offset + ", totalPage=" + totalPage + "]";
	}
}
