package com.integration.service;

import com.integration.entity.Dict;
import com.integration.entity.Dicts;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.service
* @ClassName: DictService
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 字典管理
*/
public interface DictService {

    /**
     * 新增字典
     *
     * @param dict
     * @return
     */
    public Dict addDict(Dict dict);

    /**
     * 新增字典  驼峰
     *
     * @param dict
     * @return
     */
    public Dicts addDictHump(Dicts dict);

    /**
     * 新增字典  驼峰
     *
     * @param dict
     * @return
     */
    public boolean addDictHump(List<Map> dict);

    /**
     * 删除字典
     *
     * @param dict_id
     * @return
     */
    public int deleteDict(String dict_id);

    /**
     * 修改字典
     *
     * @param dict
     * @return
     */
    public Map<String,Object> updateDict(Dict dict);

    /**
     * 批量删除字典
     *
     * @param dictIdList
     * @return
     */
    public int deleteDictList(String[] dictIdList);

    /**
     * 根据字典名称模糊查询字典列表
     *
     * @param dict_name
     * @return
     */
    public List<Dict> findDict(String dict_name);

    /**
     * 查询字典编号和名字是否存在
     *
     * @param dict_bm
     * @return
     */
    public int existsDict_bm(String dict_bm, String sj_id, String dict_name);

    /**
     * 根据字典ID获取字典信息
     *
     * @param dict_id
     * @return
     */
    public Dict findDictById(String dict_id);

    /**
     * 根据上级ID获取字典信息
     *
     * @param sj_id
     * @return
     */
    public List<Map<String, Object>> findDiceBySj_id(String sj_id);

    /**
     * 根据上级ID查询字典
     *
     * @return
     */
    public List<Dict> findParentDict(String sj_id,String dict_name);

    /**
     * 根据上级ID查询字典
     *
     * @return
     */
    List<Dict> findParentDictByBm(String sj_id,String dict_name);
    

    /**
     * 查询所有存在的名字
     *
     * @return
     */
    List<String> findDictName();

    /**
     * 查询所有存在的编码
     *
     * @return
     */
    List<String> findDictBm();
    
    /**
     * 将字典加载到redis
     */
    void redisDict();

    /**
     * 处理事件来源数据
     * @param data
     */
    void handleSourceData(List<Map> data);
}
