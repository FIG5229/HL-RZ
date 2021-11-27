package com.integration.service.impl;

import com.integration.dao.FaceDao;
import com.integration.entity.Face;
import com.integration.service.FaceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
* @Package: com.integration.service.impl
* @ClassName: FaceServiceImpl
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 人像对比
*/
@Service
public class FaceServiceImpl implements FaceService {
	
	@Resource
	private FaceDao faceDao;

	@Override
	public List<Face> findFaceList() {
		
		return faceDao.findFaceList();
	}

}
