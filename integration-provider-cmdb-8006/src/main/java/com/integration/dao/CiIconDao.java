
package com.integration.dao;

import com.integration.entity.CiIconInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author hbr
 * @date 2018-12-03 09:49:27
 * @version 1.0
 */

 @Mapper
 @Repository
 public interface CiIconDao {

    /**
     * 查询单条
     *
     * @param iconId
     * @return
     */
    public CiIconInfo getInfo(String iconId);

    /**
     * 修改单条记录
     *
     * @param info
     */
    public int updateInfo(CiIconInfo info);

    /**
     * 新增单条记录
     *
     * @param info
     */
    public void addInfo(CiIconInfo info);

    /**
     * 删除单条记录
     *
     * @param id
     */
    public int deleteInfo(String id);


    /**
     * 通过图标名称搜索
     *
     * @return
     */
    public List<CiIconInfo> searchByName(Map map);

   /**
    * 通过目录id搜索
    * @param iconDirId
    * @return
    */
    public List<CiIconInfo> searchByDirId(String iconDirId);
    
    public List<Map<String,Object>> searchIconByDirId(@Param("iconDirIdStr") String iconDirIdStr,@Param("iconName") String iconName);
    
    public List<Map<String,Object>> getIconByDmvImg(@Param("iconType")String iconType);

    /**
     * 根据目录清空全部图标
     * @param dirId
     * @return
     */
    int deleteIconByFolderId(@Param("dirId") String dirId);

    /**
     * 批量删除图片
     * @param dirId
     * @param icons
     * @return
     */
    int deleteIconByIcons(@Param("dirId") String dirId, @Param("icons") String[] icons);
    
    public List<Map<String,Object>> getIconInfoByIconFullName(@Param("iconFullName") String iconFullName);
    
    public List<String> getIconInfoByIconFullNames(Map<String,Object> itemMap);
}

