package com.integration.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integration.config.Advice.ErrorControllerAdviceBase;

public class ExcelUtilWrapper<T> extends ExcelUtil<T> {
	private static final Logger logger = LoggerFactory.getLogger(ExcelUtilWrapper.class);
	/**
	 * <p>
	 * 导出带有头部标题行的Excel <br>
	 * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
	 * </p>
	 * 
	 * @param title
	 *            表格标题
	 * @param headers
	 *            头部标题集合
	 * @param dataset
	 *            数据集合
	 * @param out
	 *            输出流
	 * @param version
	 *            2003 或者 2007，不传时默认生成2003版本
	 */
	public void exportExcel(HSSFWorkbook workbook2003,XSSFWorkbook workbook2007,String fileName, String title, String[] headers,
			Collection<T> dataset, HttpServletResponse response, String version) {
		try {
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ URLEncoder.encode(fileName, "UTF-8") + ".xls");
			if (StringUtils.isBlank(version)
					|| EXCEL_FILE_2003.equals(version.trim())) {
				exportExcel2003(workbook2003,title, headers, dataset,
						response.getOutputStream(), "yyyy-MM-dd hh:mm:ss");
			} else {
				exportExcel2007(workbook2007,title, headers, dataset,
						response.getOutputStream(), "yyyy-MM-dd hh:mm:ss");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void MapExcel(HSSFWorkbook wb,String fileName,String title, List<String> headersName,
			List<String> headersId, List<LinkedHashMap<String, String>> dtoList,HttpServletResponse response){
			exportMapExcel(wb,title,headersName,headersId,dtoList);
	}

}
