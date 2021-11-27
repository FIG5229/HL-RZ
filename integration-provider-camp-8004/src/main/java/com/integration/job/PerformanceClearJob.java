package com.integration.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.integration.service.ParameterService;
import com.integration.utils.es.EsUtil;
import com.integration.utils.es.service.Pmv;
/**
* @Package: com.integration.job
* @ClassName: PerformanceClearJob
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 定时清理三个月性能数据定时任务
*/
@Component
public class PerformanceClearJob implements Job{
	
	Logger log = LoggerFactory.getLogger(PerformanceClearJob.class);
	
	@Autowired
	private ParameterService parameterService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	Map<String,Object> mapIsSubIndex=parameterService.findParByCode("is_sub_index");
        	Map<String,Object> mapTime=parameterService.findParByCode("retain_performance_time");
        	if(mapIsSubIndex!=null && mapIsSubIndex.size()>0) {
        		String val=(String) mapIsSubIndex.get("PARA_DATA");
        		int valInt=Integer.parseInt(val);
        		int days=0;    	        	
	        	if(mapTime!=null && mapTime.size()>0) {
	        		String valStr=(String) mapTime.get("PARA_DATA");
                    days=Integer.parseInt(valStr);
	        	}else {
	        		days=30;
	        	}
        		//分索引
        		if(valInt==1) {        			
    	        	EsUtil.timedTaskDeleteIomPmvPerformanceTable(days);
        		}else {
        			String index="iom_pmv_performance";			        			
        			int daysInt=days;
        			String startTime="";
        			String endTime="";
        			Date date=new Date();
    	    		Calendar calendar = Calendar.getInstance();  
    	            calendar.setTime(date);  
    	            calendar.add(Calendar.DAY_OF_MONTH, -daysInt);  
    	            date = calendar.getTime();
    	            String time=sdf.format(date);
    	            endTime=time+" 23:59:59";
    	            //先按照时间升序查出性能表最老的数据时间
        			Map<String,Object> map=Pmv.selectPerformanceAllDataByPaging(index);
        			if(map!=null && map.size()>0) {
        				String occTime=(String) map.get("occTime");
        				Date dateOcc=sdf.parse(occTime);
        				String occTimeStr=sdf.format(dateOcc);
        				startTime=occTimeStr+" 00:00:00";
        			}else {
        				startTime=time+" 00:00:00";
        			}
    	            Map<String,Object> criterias=new HashMap<String,Object>();
    	       	    criterias.put(">=occ_time", startTime);
    	    		criterias.put("<=occ_time", endTime);
    	    		EsUtil.timedTaskDeleteSingleIndexDatas(criterias,index);
        		}
        	}
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
