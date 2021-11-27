package com.integration.fegin;

import feign.Response;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
/**
* @Package: com.integration.fegin
* @ClassName: PmvServiceFallbackFactory
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 性能模块feign调用
*/
@Component
public class PmvServiceFallbackFactory implements FallbackFactory<PmvService> {
    @Override
    public PmvService create(Throwable cause) {
        cause.printStackTrace();
        return new PmvService() {


            @Override
            public Response exportDataRelToExcelAll(String ciTypeId) {
                return null;
            }

            @Override
            public Object getHangKpiByCiId(String ciId, String ciCode) {
                return null;
            }

            @Override
            public Object importSimulationPerformance(MultipartFile file, String ciName) {
                return null;
            }
        };
    }
}
