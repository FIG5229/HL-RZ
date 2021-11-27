package com.integration.feign;

import com.integration.entity.Dict;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.feign
* @ClassName: DictServiceFallbackFactory
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 字典管理 8004
*/
@Component
public class DictServiceFallbackFactory implements FallbackFactory<DictService> {

	@Override
	public DictService create(Throwable cause) {
		return new DictService() {

			@Override
			public List<Map<String, Object>> findDiceBySjIdHump(String sjId) {
				return null;
			}

			@Override
			public Object addDictHumps(String dicts) {
				return null;
			}

			@Override
			public List<Map<String, Object>> findDiceBySj_id(int sj_id) {
				return null;
			}
		};
	}
}
