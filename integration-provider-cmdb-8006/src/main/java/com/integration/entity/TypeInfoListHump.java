package com.integration.entity;

import java.util.List;

/**
 * @ProjectName: integration
 * @Package: com.integration.entity
 * @ClassName: TypeInfoListHump
 * @Author: ztl
 * @Date: 2020-07-14
 * @Version: 1.0
 * @description:CI大类对象（驼峰）
 */
public class TypeInfoListHump {
    /**
     * id
     */
    private String id;
    /**
     * 类别名称
     */
    private String ciTypeMc;
    /**
     * 图标地址
     */
    private String ciTypeIcon;
    /**
     * CI数量
     */
    private int num;

    /**
     * ci信息集合
     */
    private List<InfoHump> infoList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCiTypeMc() {
        return ciTypeMc;
    }

    public void setCiTypeMc(String ciTypeMc) {
        this.ciTypeMc = ciTypeMc;
    }

    public String getCiTypeIcon() {
        return ciTypeIcon;
    }

    public void setCiTypeIcon(String ciTypeIcon) {
        this.ciTypeIcon = ciTypeIcon;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<InfoHump> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<InfoHump> infoList) {
        this.infoList = infoList;
    }
}
