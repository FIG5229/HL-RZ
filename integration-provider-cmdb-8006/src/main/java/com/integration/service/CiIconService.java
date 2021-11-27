package com.integration.service;

import com.integration.entity.CiIconInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author hbr
 * @date 2018-12-03 09:49:27
 * @version 1.0
 */
 
 public interface CiIconService {



	/**
	 * 查询单条
	 * @param iconId
	 * @return
	 */
	public CiIconInfo getInfo(String iconId);

		/**
	 * 修改单条记录
	 * @param path,iconId
	 */
	public int updateInfo(String path, String iconId);
	
	/**
	 * 删除单条记录
	 * @param iconId
	 */
	public int deleteInfo(String iconId);

	/**
	 * 导入图标
	 * @param importPath
	 * @return
	 */
	public List<CiIconInfo> importIcon(String importPath, String importDirId, HttpServletRequest request) ;

	/**
	 * 导出图标
	 * @param dirId
	 */
	public void exportIcon(String dirId) throws Exception;

	/**
	 * 通过名称进行搜索
	 * @return
	 */
	public List<CiIconInfo> searchByName(Map map);

    /**
     * 通过目录id搜索
     * @param iconDirId
     * @return
     */
    public List<CiIconInfo> searchByDirId(String iconDirId);

	List<Map<String, Object>> searchIconByDirId(String iconDirId,String iconName);

	List<Map<String, Object>> getIconByDmvImg(String iconType);

	/**
	 * 根据目录清空全部图标
	 * @param dirId
	 * @return
	 */
	Object deleteIconByFolderId(String dirId);

	/**
	 * 批量删除图片
	 * @param dirId
	 * @return
	 */
	Object deleteIconByIcons(String dirId, String icons);

	List<Map<String, Object>> getIconInfoByIconFullName(String iconFullName);

	List<String> getIconInfoByIconFullNames(Map<String, Object> itemMap);

 }