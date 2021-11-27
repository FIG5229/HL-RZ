package com.integration.dao;

import com.integration.entity.DataIndex;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
* @Package: com.integration.dao
* @ClassName: DataIndexDao
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
@Mapper
public interface DataIndexDao {

	/**
	 * 添加数据时要同步 index数据
	 * @return
	 */
	public int addDataIndex(DataIndex dataIndex);

	/**
	 * 批量添加index数据
	 * @return
	 */
	public int addDataIndexs(List<DataIndex> dataIndex);

	/**
	 * 修改index数据
	 * @param dataIndex index数据
	 * @return 受影响行数
	 */
	public int updateDataIndex(DataIndex dataIndex);

	/**
	 * 批量修改index数据
	 * @param dataIndex index数据列表
	 * @return 受影响行数
	 */
	public int updateDataIndexs(List<DataIndex> dataIndex);

	/**
	 * 根据CIID删除所有属性信息
	 * @param ciId
	 * @return 受影响行数
	 */
	public int deleteDataIndexByciId(@Param("ciId") String ciId);

	/**
	 * 根据CIID删除所有属性信息
	 * @param list
	 * @return 受影响行数
	 */
	public int deleteDataIndexByciIds(@Param("list") List list);

	/**
	 * 根据大类删除
	 * @param tid
	 * @return
	 */
	int deleteDataIndexByTypeId(@Param("tid") String tid);
	
	/**
	 * 去index根据 itemid typeid获取删除字段的所有ciid;
	 * @param Itemid
	 * @param typeId
	 * @return
	 */
	public List<DataIndex> findInfoByItemAndType(@Param("Itemid")String Itemid, @Param("typeId")String typeId);
	
	/**
	 * 根据itemid typeid删除index表数据
	 * @param typeId
	 * @return
	 */
	public int deleteDataIndexByTypeIdAndCI(@Param("Itemid")String Itemid, @Param("typeId")String typeId);
}
