

package com.integration.entity;

import java.util.Date;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @version 1.0
 * @date 2018-12-18 09:14:37
 */

public class CombDiagramInfo {


    /**
     * 主键
     */
    private String id;


    /**
     * 组合试图
     */
    private String comb_diagram_id;


    /**
     * 单张视图
     */
    private String diagram_id;


    /**
     * 横坐标位置
     */
    private Integer xcoor;


    /**
     * 纵坐标位置
     */
    private Integer ycoor;


    /**
     * 方向1:水平;2:垂直
     */
    private Integer direct;


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
    private Integer yxbz;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getComb_diagram_id() {
        return comb_diagram_id;
    }

    public void setComb_diagram_id(String comb_diagram_id) {
        this.comb_diagram_id = comb_diagram_id;
    }


    public String getDiagram_id() {
        return diagram_id;
    }

    public void setDiagram_id(String diagram_id) {
        this.diagram_id = diagram_id;
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


    public String getCjr_id() {
        return cjr_id;
    }

    public void setCjr_id(String cjr_id) {
        this.cjr_id = cjr_id;
    }


    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }


    public String getXgr_id() {
        return xgr_id;
    }

    public void setXgr_id(String xgr_id) {
        this.xgr_id = xgr_id;
    }


    public String getXgsj() {
        return xgsj;
    }

    public void setXgsj(String xgsj) {
        this.xgsj = xgsj;
    }


    public Integer getYxbz() {
        return yxbz;
    }

    public void setYxbz(Integer yxbz) {
        this.yxbz = yxbz;
    }


}