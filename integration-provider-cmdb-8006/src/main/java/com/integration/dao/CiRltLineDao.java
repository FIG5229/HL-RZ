package com.integration.dao;

import com.integration.generator.entity.IomCiRltLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ProjectName: integration
 * @Package: com.integration.dao
 * @ClassName: CiRltLineDao
 * @Author: sgh
 * @Description: 朋友圈Dao
 * @Date: 2019/10/18 11:20
 * @Version: 1.0
 */
@Mapper
public interface CiRltLineDao {

    List<IomCiRltLine> findByStartTypeId(IomCiRltLine iomCiRltLine);

}
