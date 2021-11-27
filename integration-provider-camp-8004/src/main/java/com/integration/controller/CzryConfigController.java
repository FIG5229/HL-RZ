package com.integration.controller;

import com.integration.entity.PageResult;
import com.integration.service.CzryConfigService;
import com.integration.utils.DataUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/** 
* @author 作者 :$Author$
* @version Revision
* 创建时间:2019年3月25日 上午9:39:41 
* id:Id
*/

@RestController
public class CzryConfigController {
	private static final Logger logger = LoggerFactory.getLogger(CzryConfigController.class);
    @Autowired
    private CzryConfigService czryConfigService;

    /**
     * 查询用户配置
     *
     * @return
     */
    @RequestMapping(value = "/findCzryConfigList", method = RequestMethod.GET)
    public PageResult findCzryList( HttpServletRequest request) {
    	String userId = TokenUtils.getTokenUserId();
        List<Map> map = new ArrayList<Map>();
            try {
            	map = czryConfigService.findCzryConfigList(userId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
				return DataUtils.returnPr(false, "查询异常!");
			}

        
        return DataUtils.returnPr(map);
    }

    /**
     * 添加or删除用户配置
     *
     * @return
     */
    @RequestMapping(value = "/updateCzryConfigList", method = RequestMethod.POST)
    public PageResult updateCzryConfigList(@RequestParam Map map,HttpServletRequest request) {
            try {
            	String czryId=SeqUtil.nextId().toString();
            	String userId = TokenUtils.getTokenUserId();
            	List<Map> mapList = czryConfigService.findCzryConfigList(userId);         	
            	if(mapList !=null && mapList.size()>0){
            		map.put("userId", userId);
            		int result =czryConfigService.updateCzryConfig(map);
            		if(result>0){
                		return DataUtils.returnPr(true, "更新成功!");
                	}else{
                		return DataUtils.returnPr(false, "更新失败!");
                	}
        		}else{
        			map.put("id",czryId);
        			map.put("userId", userId);
        			int result=czryConfigService.addCzryConfig(map);
        			if(result>0){
                		return DataUtils.returnPr(true, "新增成功!");
                	}else{
                		return DataUtils.returnPr(false, "新增失败!");
                	}
        		}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
				return DataUtils.returnPr(false, "操作异常!");
			}

    }

}
