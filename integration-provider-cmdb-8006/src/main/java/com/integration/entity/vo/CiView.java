package com.integration.entity.vo;

import lombok.Data;
/**
* @Package: com.integration.entity.vo
* @ClassName: CiView
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
@Data
public class CiView{
    private String ciId;

    private String ciTypeId;

    private String ciCode;

    private Integer sourceId;

    private String major;
    
    private String ciTypeName;

    private String jsondata;

}