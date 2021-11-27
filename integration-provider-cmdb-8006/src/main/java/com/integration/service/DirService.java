package com.integration.service;

import com.integration.entity.Dir;
import com.integration.entity.Tree;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.service
* @ClassName: DirService
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 目录管理
*/
public interface DirService {

    /**
     * 获取所有目录结构
     *
     * @return
     */
    public List<Dir> findDirList(int type);

    /**
     * 获取目录树形结构
     *
     * @return
     */
    public List<Tree> findTreeList(String name);

    /**
     * 查询排序
     *
     * @return
     */
    Integer fndTreeSort(String pId);

    /**
     * 添加目录
     *
     * @return
     */
    public Dir addDir(Dir dir, HttpServletRequest request);

    /**
     * 删除目录
     *
     * @return
     */
    public boolean deleteDir(String dirId);

    /**
     * 修改目录
     *
     * @param dir
     * @return
     */
    public boolean updateDir(Dir dir);

    /**
     * 根据id获取目录对象
     *
     * @param dirId
     * @return
     */
    public Dir findDirById(String dirId);

    /**
     * 获取所有的图标文件夹
     *
     * @return
     */
    public List<Dir> findIconDirList();

    /**
     * 判断目录名字是否存在
     *
     * @return
     */
    public boolean dirNameExists(String dir_name, String dir_type,String domainId);

    List<Map<String, Object>> getDirListByParentId(String diagDir, String user_id);

	List<Tree> findTreeListByViewModel(String name);

    /**
     *根据目录名称获取目录对象
     *
     * @param dirName 目录名称
     * @return
     */
    Dir getDirByDirName(String dirName);

	List<Dir> findDirListByDiagram(int type, String cjrId);

    /**
     * 初始化目录和大类数据域
     * @param domainId
     */
    void updateDirDomian(String domainId);
}
