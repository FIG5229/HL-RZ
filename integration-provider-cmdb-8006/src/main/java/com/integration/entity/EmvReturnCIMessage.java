package com.integration.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: integration
 * @Package: com.integration.entity
 * @ClassName: EmvReturnCIMessage
 * @Author: sgh
 * @Description: 返回值CI的属性类
 * @Date: 2019/10/10 14:42
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmvReturnCIMessage {

    /**
     * id
     */
    private String id;


    private String ciCode;

    /**
     * ci描述
     */
    private String ci_desc;

    /**
     * 所属类别
     */
    private String ci_type_id;

    /**
     * 所属类别编码
     */
    private String ci_type_bm;

    /**
     * 来源
     */
    private int source_id;

    /**
     * 所有者
     */
    private int owner_id;

    /**
     * 所属组织
     */
    private int org_id;

    /**
     * CI版本
     */
    private String ci_version;

    /**
     * 创建人
     */
    private String cjr_id;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 修改人
     */
    private String xgr_id;

    /**
     * 修改时间
     */
    private String xgsj;

    /**
     * 有效标志
     */
    private String yxbz;

    private String ci_type_icon;
    /**
     * CI某属性值
     */
    private String zhi;
    /**
     * 关联ID
     */
    private String glid;
    /**
     * ci数量
     */
    private Integer cinum;
    /**
     * 某一个属性的值
     */
    private String valueString;

    /**
     * CI大类ID
     */
    private String typeId;
    /**
     * 某一个属性的ID
     */
    private String valueStringId;
}
