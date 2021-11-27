package com.integration.entity.vo;

import java.util.List;

import com.integration.generator.entity.IomCiRltNodeCdt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
* @Package: com.integration.entity.vo
* @ClassName: IomCiRltNodeVO
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IomCiRltNodeVO {
	
    private List<IomCiRltNodeCdt> nodeCdts;
    
    
}
