package com.integration.service;

import java.util.List;

import com.integration.entity.Face;
/**
* @Package: com.integration.service
* @ClassName: FaceService
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 人像比对
*/
public interface FaceService {
	
	public List<Face> findFaceList();

}
