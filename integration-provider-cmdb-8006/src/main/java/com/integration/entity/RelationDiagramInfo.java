package com.integration.entity;

import java.util.List;

/**
 * @ProjectName: integration-provider-alarm-8005
 * @Package: com.integration.entity
 * @ClassName: RelationDiagramInfo
 * @Author: ztl
 * @Date: 2021-04-15
 * @Version: 1.0
 * @description:关联视图查询实体类
 */
public class RelationDiagramInfo {
    /**
     * 主键
     */
    private String id;

    /**
     * 视图名称
     */
    private String diagName;

    /**
     * 视图目录
     */
    private String diagDir;

    /**
     * 视图类型1:单图;2:组图;3:模板
     */
    private Integer diagType;

    /**
     * 视图描述
     */
    private String diagDesc;

    /**
     * 视图SVG
     */
    private String diagSvg;

    /**
     * 视图XML
     */
    private String diagXml;

    /**
     * 背景图地址
     */
    private String bgImgUrl;

    /**
     * 背景样式
     */
    private String bgStyle;

    /**
     * 视图图标１
     */
    private String iconoUrl;

    /**
     * 视图图标２
     */
    private String icontUrl;

    /**
     * 视图图标３
     */
    private String iconhUrl;

    /**
     * 所属用户
     */
    private String userId;

    /**
     * 数据驱动１：不更新；２：标记提示；３：更新
     */
    private Integer dataType;

    /**
     * CI坐标１
     */
    private String ciPointo;

    /**
     * CI坐标２
     */
    private String ciPointt;

    /**
     * 查询列
     */
    private String idxAttr;

    /**
     * 组合图行数
     */
    private Integer combRows;

    /**
     * 组合图列数
     */
    private Integer combCols;

    /**
     * 删除标志
     */
    private Integer isDelete;

    /**
     * 公开标志
     */
    private Integer isOpen;

    /**
     * 公开时间
     */
    private String openTime;

    /**
     * 浏览次数
     */
    private Integer readNum;

    /**
     * 创建人
     */
    private String cjrId;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 修改人
     */
    private String xgrId;

    /**
     * 修改时间
     */
    private String xgsj;

    /**
     * 有效标志
     */
    private String yxbz;

    /**
     * 视图关系集合
     */
    private List<CombDiagramInfo> combList;

    /**
     * 横向坐标
     */
    private Integer xcoor;

    /**
     * 纵向坐标
     */
    private Integer ycoor;

    /**
     * 方向 1水平 2垂直
     */
    private Integer direct;

    private String appCiId;

    /**
     * 数据域
     */
    private String domainId;

    private String czryName;

    public String getCzryName() {
        return czryName;
    }

    public void setCzryName(String czryName) {
        this.czryName = czryName;
    }

    public String getAppCiId() {
        return appCiId;
    }

    public void setAppCiId(String appCiId) {
        this.appCiId = appCiId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiagName() {
        return diagName;
    }

    public void setDiagName(String diagName) {
        this.diagName = diagName;
    }

    public String getDiagDir() {
        return diagDir;
    }

    public void setDiagDir(String diagDir) {
        this.diagDir = diagDir;
    }

    public Integer getDiagType() {
        return diagType;
    }

    public void setDiagType(Integer diagType) {
        this.diagType = diagType;
    }

    public String getDiagDesc() {
        return diagDesc;
    }

    public void setDiagDesc(String diagDesc) {
        this.diagDesc = diagDesc;
    }

    public String getDiagSvg() {
        return diagSvg;
    }

    public void setDiagSvg(String diagSvg) {
        this.diagSvg = diagSvg;
    }

    public String getDiagXml() {
        return diagXml;
    }

    public void setDiagXml(String diagXml) {
        this.diagXml = diagXml;
    }

    public String getBgImgUrl() {
        return bgImgUrl;
    }

    public void setBgImgUrl(String bgImgUrl) {
        this.bgImgUrl = bgImgUrl;
    }

    public String getBgStyle() {
        return bgStyle;
    }

    public void setBgStyle(String bgStyle) {
        this.bgStyle = bgStyle;
    }

    public String getIconoUrl() {
        return iconoUrl;
    }

    public void setIconoUrl(String iconoUrl) {
        this.iconoUrl = iconoUrl;
    }

    public String getIcontUrl() {
        return icontUrl;
    }

    public void setIcontUrl(String icontUrl) {
        this.icontUrl = icontUrl;
    }

    public String getIconhUrl() {
        return iconhUrl;
    }

    public void setIconhUrl(String iconhUrl) {
        this.iconhUrl = iconhUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getCiPointo() {
        return ciPointo;
    }

    public void setCiPointo(String ciPointo) {
        this.ciPointo = ciPointo;
    }

    public String getCiPointt() {
        return ciPointt;
    }

    public void setCiPointt(String ciPointt) {
        this.ciPointt = ciPointt;
    }

    public String getIdxAttr() {
        return idxAttr;
    }

    public void setIdxAttr(String idxAttr) {
        this.idxAttr = idxAttr;
    }

    public Integer getCombRows() {
        return combRows;
    }

    public void setCombRows(Integer combRows) {
        this.combRows = combRows;
    }

    public Integer getCombCols() {
        return combCols;
    }

    public void setCombCols(Integer combCols) {
        this.combCols = combCols;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public String getCjrId() {
        return cjrId;
    }

    public void setCjrId(String cjrId) {
        this.cjrId = cjrId;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public String getXgrId() {
        return xgrId;
    }

    public void setXgrId(String xgrId) {
        this.xgrId = xgrId;
    }

    public String getXgsj() {
        return xgsj;
    }

    public void setXgsj(String xgsj) {
        this.xgsj = xgsj;
    }

    public String getYxbz() {
        return yxbz;
    }

    public void setYxbz(String yxbz) {
        this.yxbz = yxbz;
    }

    public List<CombDiagramInfo> getCombList() {
        return combList;
    }

    public void setCombList(List<CombDiagramInfo> combList) {
        this.combList = combList;
    }

    public Integer getXcoor() {
        return xcoor;
    }

    public void setXcoor(Integer xcoor) {
        this.xcoor = xcoor;
    }

    public Integer getYcoor() {
        return ycoor;
    }

    public void setYcoor(Integer ycoor) {
        this.ycoor = ycoor;
    }

    public Integer getDirect() {
        return direct;
    }

    public void setDirect(Integer direct) {
        this.direct = direct;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }
}
