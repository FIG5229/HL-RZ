package com.integration.service;

import com.integration.entity.CiAssociatedFieldConf;
import com.integration.entity.PageResult;

import java.util.List;

/**
 * @ProjectName: integration
 * @Package: com.integration.service
 * @ClassName: CiAssociatedFieldConfService
 * @Author: ztl
 * @Date: 2021-07-07
 * @Version: 1.0
 * @description:关联字段配置实现接口
 */
public interface CiAssociatedFieldConfService {
    /**
     * 确定保存修改关联字段配置
     *
     * 增加给接口平台推送MQ消息
     *
     * @param ciAssociatedFieldConf
     * @return
     */
    boolean confirmFieldConf(CiAssociatedFieldConf ciAssociatedFieldConf);

    /**
     * 保存修改关联字段配置
     *
     * @param ciAssociatedFieldConf
     * @return
     */
    boolean saveFieldConf(CiAssociatedFieldConf ciAssociatedFieldConf);

    /**
     * 删除关联字段配置信息
     *
     * @param id
     * @return
     */
    boolean deleteFieldConf(String id);

    /**
     * 获取关联字段配置列表
     *
     * @param sourceCiId 来源ID
     * @param ciTypeId 大类ID
     * @return
     */
    PageResult findFieldConfList(String sourceCiId, String ciTypeId);

    /**
     *
     * 获取关联字段配置详情
     *
     * @param id
     * @return
     */
    CiAssociatedFieldConf findFieldConf(String id);

    /**
     * 触发关联字段配置
     *
     * @param id
     */
    void triggerFieldConf(String id);

    /**
     * 接口平台查询关联字段相关信息
     *
     * @param sourceCiId
     * @param ciTypeId
     * @return
     */
    List<CiAssociatedFieldConf> findConfList(String sourceCiId, String ciTypeId);

    /**
     * 根据大类ID获取数据来源编码
     *
     * @param ciTypeId
     * @return
     */
    List<String> findSourceCiIdByCiTypeId(String ciTypeId);
    /**
     * @Author: ztl
     * date: 2021-08-06
     * @description: 给接口平台推送CI变化状态
     */
    void sendCiChangeMsg(String ciTypeId);
}
