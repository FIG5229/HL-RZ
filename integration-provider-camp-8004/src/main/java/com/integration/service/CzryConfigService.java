package com.integration.service;

import java.util.List;
import java.util.Map;

/** 
* @author 作者 :$Author$
* @version Revision
* 创建时间:2019年3月24日 下午8:21:44 
* id:Id
*/
public interface CzryConfigService {


/**
 * 查询
 */
public List<Map> findCzryConfigList(String userId);
/**
 * 更新
 */
public int updateCzryConfig(Map map);
/**
 * 添加
 */
public int addCzryConfig(Map map);


}
