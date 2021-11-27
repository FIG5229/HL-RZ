package com.integration.service.impl;

import com.integration.dao.ParameterDao;
import com.integration.entity.Parameter;
import com.integration.service.ParameterService;
import com.integration.utils.DateUtils;
import com.integration.utils.SeqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.service.impl
* @ClassName: ParameterServiceImpl
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 系统参数
*/
@Service
@Transactional
public class ParameterServiceImpl implements ParameterService {

	@Resource
	private ParameterDao parameterMapper;

	/**
	 * 添加参数
	 */
	@Override
	public Parameter addParameter(Parameter parameter) {
		parameter.setPara_id(SeqUtil.nextId()+"");
		// 默认创建时间为系统当前时间
		parameter.setCjsj(DateUtils.getDate());
		// 默认有效标识为1
		parameter.setYxbz("1");
		// 返回添加的参数ID
		int add = parameterMapper.addParameter(parameter);
		if (add > 0) {
			return parameter;
		} else {
			return null;
		}
	}

	/**
	 * 删除参数
	 */
	@Override
	public int deleteParameter(String para_id) {
		return parameterMapper.deleteParameter(para_id);
	}

	/**
	 * 修改参数
	 */
	@Override
	public int updateParameter(Parameter parameter) {
		parameter.setXgsj(DateUtils.getDate());
		return parameterMapper.updateParameter(parameter);
	}

	/**
	 * 分页查询参数列表
	 */
	@Override
	public List<Parameter> findParameter(String search, Integer pageCount,
			Integer pageIndex) {

		return parameterMapper.findParameterPage(search, pageIndex, pageCount);
	}

	/**
	 * 查询参数总数
	 */
	@Override
	public int findParameterCount(String search) {
		return parameterMapper.findParameterCount(search);
	}

	/**
	 * 根据参数ID查询参数信息
	 */
	@Override
	public Parameter findParameterById(int para_id) {
		return parameterMapper.findParameterById(para_id);
	}

	@Override
	public List<Parameter> findAllParam() {
		return parameterMapper.findAllParam();
	}

	@Override
	public Map<String, Object> findParByCode(String code) {
		return parameterMapper.findParByCode(code);
	}

}
