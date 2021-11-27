/**
 * 
 */
package com.integration.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.integration.dao.CzryConfigDao;
import com.integration.service.CzryConfigService;

import javax.annotation.Resource;

/** 
* @author 作者 :$Author$
* @version Revision
* 创建时间:2019年3月24日 下午8:35:04 
* id:Id
*/

@Transactional
@Service
public class CzryConfigServiceImpl implements CzryConfigService {

	@Resource
	private CzryConfigDao czryConfigDao;
	@Override
	public List<Map> findCzryConfigList(String userId){
		// TODO Auto-generated method stub
		return czryConfigDao.findCzryConfigList(userId);
	}
	@Override
	public int updateCzryConfig(Map map) {
		// TODO Auto-generated method stub
		return czryConfigDao.updateCzryConfig(map);
	}
	@Override
	public int addCzryConfig(Map map) {
		// TODO Auto-generated method stub
		return czryConfigDao.addCzryConfig(map);
	}


}
