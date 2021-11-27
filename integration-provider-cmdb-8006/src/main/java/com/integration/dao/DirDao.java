package com.integration.dao;

import com.integration.entity.Dir;
import com.integration.entity.Tree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.dao
* @ClassName: DirDao
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 目录管理
*/
@Mapper
@Repository
public interface DirDao {

    /**
     * 获取所有目录结构
     *
     * @return
     */
    public List<Dir> findDirList(@Param("type") int type);
    
    public List<Dir> findDirListByDiagram(@Param("type") int type,@Param("cjrId") String cjrId);

    /**
     * 获取目录树形结构
     *
     * @return
     */
    public List<Tree> findTreeList(@Param("name") String name,@Param("domainId") String domainId);

    /**
     * 查询排序
     *
     * @return
     */
    Integer fndTreeSort(@Param("pId")String pId);

    public List<Tree> findTreeListByName(@Param("name") String name,@Param("domainId") String domainId);

    /**
     * dir包含name的树形结构
     *
     * @param name
     * @return
     */
    public List<Tree> findDirTreeList(String name);

    /**
     * dir包含name和所有type的树形结构
     *
     * @param name
     * @return
     */
    public List<Tree> DirTreeList(String name);

    /**
     * type包含name的树形结构
     *
     * @param name
     * @return
     */
    public List<Tree> findTypeList(String name);

    /**
     * 根据大类ID从下向上找父节点
     *
     * @param id
     * @return
     */
    public List<Tree> findParentList(String id);

    /**
     * 添加目录
     *
     * @return
     */
    public int addDir(Dir dir);

    /**
     * 删除目录
     *
     * @return
     */
    public int deleteDir(String dirId);

    /**
     * 修改目录
     *
     * @param dir
     * @return
     */
    public int updateDir(Dir dir);

    /**
     * 根据id获取目录对象
     *
     * @param dirId
     * @return
     */
    public Dir findDirById(String dirId);

    /**
     * 查询所有图标的文件夹
     *
     * @return
     */
    public List<Dir> findIconDirList();

    /**
     * 判断目录名字是否存在
     *
     * @return
     */
    public int dirNameExists(@Param("dir_name") String dir_name, @Param("dir_type") String dir_type,@Param("domainId") String domainId);

    /**
     * 获取视图目录
     *
     * @param diagDir
     * @param user_id
     * @return
     */
    public List<Map<String, Object>> getDirListByParentId(@Param("diagDir") String diagDir, @Param("user_id") String user_id, @Param("domainId") String domainId);

    /**
     * 根据目录名称获取目录对象
     * @param dirName 目录名称
     * @return
     */
    Dir getDirByDirName(String dirName);

    void updateDirDomian(String domainId);

    void updateCiTypeDomian(String domainId);
}
