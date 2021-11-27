package com.integration.dao;

import com.integration.entity.Dict;
import com.integration.entity.Dicts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.dao
* @ClassName: DictDao
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 字典管理
*/
@Mapper
public interface DictDao {

    /**
     * 新增字典
     *
     * @param dict
     * @return
     */
    public int addDict(Dict dict);

    /**
     * 新增字典  驼峰
     *
     * @param dict
     * @return
     */
    public int addDictHump(Dicts dict);


    /**
     * 新增字典  驼峰
     *
     * @param dicts
     * @return
     */
    public int addDictHumps(@Param("dicts") List<Map> dicts);


    /**
     * 根据上级ID查询字典
     *
     * @return
     */
    public List<Dict> findParentDict(@Param("sj_id")String sj_id,@Param("dict_name")String dict_name);
    /**
     * 根据上级ID查询字典
     *
     * @return
     */
    public List<Dict> findParentDictByBm(@Param("sj_id")String sj_id,@Param("dict_name")String dict_name);
    /**
     * 删除字典
     *
     * @param dict_id
     * @return
     */
    public int deleteDict(@Param("dict_id") String dict_id);

    /**
     * 根据上级字典ID删除子字典集合
     *
     * @return
     */
    public int deleteDictBySjId(String sj_id);

    /**
     * 批量删除字典
     *
     * @param dictIdList
     * @return
     */
    public int deleteDictList(String[] dictIdList);

    /**
     * 修改字典
     *
     * @param dict
     * @return
     */
    public int updateDict(Dict dict);

    /**
     * 根据字典名称查询字典
     *
     * @param dict_name
     * @return
     */
    public List<Dict> findDict(@Param("dict_name") String dict_name);

    /**
     * 根据字典ID获取字典信息
     *
     * @param dict_id
     * @return
     */
    public Dict findDictById(@Param("dict_id") String dict_id);

    /**
     * 根据上级ID获取字典信息
     *
     * @param sj_id
     * @return
     */
    public List<Map<String, Object>> findDiceBySj_id(@Param("sj_id") String sj_id);

    /**
     * 查询编码是否存在
     *
     * @return
     */
    public int existsDict_bm(@Param("dict_bm") String dict_bm,
                             @Param("sj_id") String sj_id);

    /**
     * 查询名字是否存在
     *
     * @param dict_name
     * @return
     */
    public int existsDict_name(@Param("sj_id") String sj_id, @Param("dict_name") String dict_name);

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

    Dict findDictBySjIdBm(@Param("sjId") String sjId,@Param("dictBm") String dictBm);

    void updateDictList(@Param("list") List<Dict> updateList);

    void addDictList(@Param("list") List<Dict> addList);
}
