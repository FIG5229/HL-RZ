package com.integration.service.impl;

import com.integration.dao.ActionsDao;
import com.integration.service.ActionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/** 
* @author 作者 :$Author$
* @version Revision
* 创建时间:2019年7月4日 下午6:00:01 
* id:Id
*/
@Service
public class ActionServiceImpl implements ActionService{
	@Resource
	private ActionsDao actionsDao;
	@Override
	public void insertAction(Map ac) {
		// TODO Auto-generated method stub
		actionsDao.insertAction(ac); 
	}

}
