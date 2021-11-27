package com.integration.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.integration.dao.IomCampActionLogDao;
import com.integration.entity.IomCampActionInfo;
import com.integration.entity.PageResult;
import com.integration.mybatis.entity.Page;
import com.integration.service.IomCampActionLogService;
import com.integration.utils.DataUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @date 2019-01-17 05:39:39
 *
 * @version 1.0
 */
@Transactional
@Service
public class IomCampActionLogServiceImpl implements IomCampActionLogService{

	@Resource
	private IomCampActionLogDao iIomCampActionDao;
	
	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
    @Override
	public List<IomCampActionInfo> getAllPage(Map<String, Object> params){
		return iIomCampActionDao.getAllPage(params);
	}


	@Override
	public PageResult getAllPageNew(Map<String, Object> params) {
		Page page = (Page) params.get("page");
		PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
		//sql排序缓存池容量有限，改为先取id，排好序根据id查所有
		List<IomCampActionInfo> idInfoList = iIomCampActionDao.getAllPageId(params);
		List<String> idList = new ArrayList<>();
		if(idInfoList != null && idInfoList.size() > 0){
			idInfoList.forEach(item->{idList.add(item.getId());});
		}
		PageInfo pageInfo = new PageInfo(idInfoList);
		if(idList.size() > 0){
			params.put("idList", idList);
			List<IomCampActionInfo>  infoList = iIomCampActionDao.getAllPageNew(params);
			Map<String, IomCampActionInfo> infoMap = infoList.stream().collect(Collectors.toMap(IomCampActionInfo::getId, item->{return item;}));
			List<IomCampActionInfo> result = new ArrayList<>();
			idList.forEach(id->{
				result.add(infoMap.get(id));
			});
			return DataUtils.returnPr(Long.valueOf(pageInfo.getTotal()).intValue(), result);
		}


		return DataUtils.returnPr(0, Collections.EMPTY_LIST);
	}

	/**
	 * 查询总数
	 * @param params
	 * @return
	 */
	@Override 
	public int getAllCount(Map<String, Object> params){
		
		return iIomCampActionDao.getAllCount(params);
	}
	
	/**
	 * 查询列表
	 * @param params
	 * @return
	 */
	@Override 
	public List<IomCampActionInfo> getAllList(HashMap<Object,Object> params){
		
		return iIomCampActionDao.getAllList(params);
	}
	
	/**
	 * 查询单条
	 * @param params
	 * @return
	 */
	@Override 
	public IomCampActionInfo getInfo(HashMap<Object,Object> params){
	
		return iIomCampActionDao.getInfo(params);
	}
	
	/**
	 * 修改单条记录
	 * @param info
	 */
	@Override 
	public void updateInfo(IomCampActionInfo info){
	
		iIomCampActionDao.updateInfo(info);
	}
	
	/**
	 * 新增单条记录
	 * @param info
	 */
	@Override 
	public void insertInfo(IomCampActionInfo info){
	
		iIomCampActionDao.insertInfo(info);
	}
	
	/**
	 * 删除单条记录
	 * @param id
	 */
	@Override 
	public void deleteInfo(int id){
	
		iIomCampActionDao.deleteInfo(id);
	}
	
}