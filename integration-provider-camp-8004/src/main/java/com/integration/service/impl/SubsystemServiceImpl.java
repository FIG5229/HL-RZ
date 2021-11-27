package com.integration.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.integration.dao.ConfigureDao;
import com.integration.dao.SubsystemDao;
import com.integration.entity.Subsystem;
import com.integration.service.SubsystemService;

import javax.annotation.Resource;
/**
* @Package: com.integration.service.impl
* @ClassName: SubsystemServiceImpl
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 子系统管理
*/
@Service
@Transactional
public class SubsystemServiceImpl implements SubsystemService {

    @Resource
    private SubsystemDao subsystemDao;

    @Resource
    private ConfigureDao configureDao;

    @Override
    public List<Subsystem> findAllS() {
        return subsystemDao.findAllS();
    }

    @Override
    public int addSubsystem(Subsystem subsystem) {
        return subsystemDao.addSubsystem(subsystem);
    }

    /**
     * 删除子系统
     *
     * @param id
     * @return
     */
    @Override
    public int deleteSubsystem(String czryId, String id) {
        //根据子系统id和当前登录人id删除关系映射
        int i2 = subsystemDao.deleteUserByCzrySys(czryId, id);
        //根据子系统id查询servername
        String serverName = subsystemDao.findServerNameById(id);
        //根据servername查询配置id
        List<String> idList = configureDao.findIdByServer(serverName);
        //根据配置id删除入参和出参
        for (String s : idList) {
            int i = configureDao.deleteMappingByConfId(s);
            int i1 = configureDao.deleteOutMappingByConfId(s);
        }
        //根据servername删除配置
        int i = configureDao.deleteConfByServer(serverName);
        //删除子系统
        return subsystemDao.deleteSubsystem(id);
    }

    @Override
    public int updateSubsystem(Subsystem subsystem) {
        return subsystemDao.updateSubsystem(subsystem);
    }

    @Override
    public List<String> findAllName() {
        return subsystemDao.findAllName();
    }

    @Override
    public String findKpiCode(String kpi_name) {
        return subsystemDao.findKpiCode(kpi_name);
    }

    @Override
    public String findNameById(String id) {
        return subsystemDao.findNameById(id);
    }

	@Override
	public Map getUserInfoByLoginId(String userId, String sub) {
		// TODO Auto-generated method stub
		return subsystemDao.getUserInfoByLoginId(userId,sub);
	}
}
