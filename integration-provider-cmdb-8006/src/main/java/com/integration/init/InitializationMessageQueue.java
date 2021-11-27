package com.integration.init;

import com.integration.generator.dao.IomCiKpiTypeMapper;
import com.integration.generator.entity.IomCiKpiType;
import com.integration.generator.entity.IomCiKpiTypeExample;
import com.integration.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.init
* @ClassName: InitializationMessageQueue
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 初始化
*/
@Component
public class InitializationMessageQueue implements ApplicationRunner{

	@Resource
	private IomCiKpiTypeMapper iomCiKpiTypeMapper;

	private static final Logger log = LoggerFactory.getLogger(InitializationMessageQueue.class);


    @Override
    public void run(ApplicationArguments args) throws Exception {
    	IomCiKpiTypeExample example=new IomCiKpiTypeExample();
		example.createCriteria().andYxbzEqualTo(1).andObjTypeEqualTo(2);
		List<IomCiKpiType> list=iomCiKpiTypeMapper.selectByExample(example);
		if(list!=null && list.size()>0) {
			for(IomCiKpiType iomCiKpiType:list) {
				Map<String,Object> map=bean2map(iomCiKpiType);
				String kpiId=(String) map.get("kpiId");
				String ciTypeId=(String) map.get("objId");
				String key = kpiId+"_"+ciTypeId;
				RedisUtils.set(key,map);
    		}
		}

    }

	/**
	 * @Author: ztl
	 * date: 2021-08-09
	 * @description: 把JavaBean转化为map
	 */
  	public static Map<String,Object> bean2map(Object bean) throws Exception{
  	    Map<String,Object> map = new HashMap<>();
  	    //获取JavaBean的描述器
  	    BeanInfo b = Introspector.getBeanInfo(bean.getClass(),Object.class);
  	    //获取属性描述器
  	    PropertyDescriptor[] pds = b.getPropertyDescriptors();
  	    //对属性迭代
  	    for (PropertyDescriptor pd : pds) {
  	        //属性名称
  	        String propertyName = pd.getName();
  	        //属性值,用getter方法获取
  	        Method m = pd.getReadMethod();
			//用对象执行getter方法获得属性值
  	        Object properValue = m.invoke(bean);

  	        //把属性名-属性值 存到Map中
  	        map.put(propertyName, properValue);
  	    }
  	    return map;
  	}



}
