package com.integration.service.impl;

import com.integration.dao.IomCiKpiClassDao;
import com.integration.entity.IomCiKpiClass;
import com.integration.service.IomCiKpiClassService;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 指标大类表(IomCiKpiClass)表服务实现类
 *
 * @author makejava
 * @since 2019-11-01 11:37:02
 */
@Service("iomCiKpiClasService")
public class IomCiKpiClassServiceImpl implements IomCiKpiClassService {
    @Resource
    private IomCiKpiClassDao iomCiKpiClasDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public IomCiKpiClass queryById(String id) {
        return this.iomCiKpiClasDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<IomCiKpiClass> queryAllByLimit(int offset, int limit) {
        return this.iomCiKpiClasDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param iomCiKpiClas 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(IomCiKpiClass iomCiKpiClas) {
        //获取数据域ID
        String domainId = TokenUtils.getTokenOrgDomainId();
        iomCiKpiClas.setId(SeqUtil.nextId() + "");
        iomCiKpiClas.setYxbz(1);
        iomCiKpiClas.setDomain_id(domainId==null?"-1":domainId);
        int num = this.iomCiKpiClasDao.insert(iomCiKpiClas);
        return num;
    }

    /**
     * 修改数据
     *
     * @param iomCiKpiClas 实例对象
     * @return 实例对象
     */
    @Override
    public IomCiKpiClass update(IomCiKpiClass iomCiKpiClas) {
        this.iomCiKpiClasDao.update(iomCiKpiClas);
        return this.queryById(iomCiKpiClas.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.iomCiKpiClasDao.deleteById(id) > 0;
    }

    /**
     * 查询所有的大类
     *
     * @return
     */
    @Override
    public List<Map> selectAll() {
        //获取数据域ID
        String domainId = TokenUtils.getTokenOrgDomainId();
        return iomCiKpiClasDao.selectAll(domainId);
    }

    /**
     * 查询名称是否存在
     *
     * @param name
     * @return
     */
    @Override
    public int booById(String name) {
        return iomCiKpiClasDao.booById(name);
    }
    
    @Override
	public List<IomCiKpiClass> getAllIomCiKpiClas() {
    	
		return iomCiKpiClasDao.selectAllByPmv();
	}

    @Override
    public List<Map<String, Object>> getCiKpiAllByPmv(){
    	return iomCiKpiClasDao.getCiKpiAllByPmv();
    }

    @Override
    public IomCiKpiClass findByName(String name) {
        return iomCiKpiClasDao.findByName(name);
    }
}