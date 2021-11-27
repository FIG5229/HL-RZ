package com.integration.controller;

import com.integration.entity.EmvEvCurrentInfo;
import com.integration.entity.PageResult;
import com.integration.fegin.EmvService;
import com.integration.fegin.PmvService;
import feign.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.controller
 * @ClassName: AnalogDataController
 * @Author: ztl
 * @Date: 2021-05-08
 * @Version: 1.0
 * @description:模拟数据
 */
@RestController
public class AnalogDataController {
    private static final Logger logger = LoggerFactory.getLogger(AnalogDataController.class);
    @Autowired
    private EmvService emvService;
    @Autowired
    private PmvService pmvService;

    /**
     * 模拟告警
     * @param emvEvCurrentInfo
     * @return
     */
    @RequestMapping(value = "/eventCurrent/saveImitateEvcCurrentInfo", method = RequestMethod.POST)
    public boolean saveImitateEvcCurrentInfo(EmvEvCurrentInfo emvEvCurrentInfo) {
        return emvService.saveImitateEvcCurrentInfo(emvEvCurrentInfo);
    }

    /**
     * 下载模板
     * @param ciTypeId
     * @param response
     * @return
     */
    @RequestMapping(value = "/bigScreen/exportSimulationPerformance", method = RequestMethod.GET)
    public boolean exportDataRelToExcelAll(String ciTypeId, HttpServletResponse response) throws IOException {
        InputStream inputStream = null;
        try {
            Response serviceResponse = pmvService.exportDataRelToExcelAll(ciTypeId);
            Response.Body body = serviceResponse.body();
            inputStream = body.asInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            response.setHeader("Content-Disposition", serviceResponse.headers().get("Content-Disposition").toString().replace("[","").replace("]",""));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
            int length = 0;
            byte[] temp = new byte[1024 * 10];
            while ((length = bufferedInputStream.read(temp)) != -1) {
                bufferedOutputStream.write(temp, 0, length);
            }
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            bufferedInputStream.close();
            inputStream.close();
            PageResult.setMessage(true,"下载成功");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            PageResult.setMessage(false,"下载失败");
            logger.error("下载模板异常:",e);
            return false;
        }
    }

    /**
     * 根据ciId查询是否挂载指标
     * @param ciId
     * @param ciCode
     * @return
     */
    @RequestMapping(value = "/bigScreen/getHangKpiByCiCode", method = RequestMethod.GET)
    public Object getHangKpiByCiId(String ciId,String ciCode){
        return pmvService.getHangKpiByCiId(ciId,ciCode);
    }

    /**
     * 导入模拟性能数据
     * @param file
     * @param
     * @return
     */
    @RequestMapping(value = "/bigScreen/importSimulationPerformance", method = RequestMethod.POST)
    public Object importSimulationPerformance(MultipartFile file, String ciCode,HttpServletResponse response){
        try {
        	String messageStr=(String) pmvService.importSimulationPerformance(file,ciCode);
        	if("导入成功".equals(messageStr)) {
        		PageResult.setMessage(true,"导入成功");
        		return true;
        	}else {
        		PageResult.setMessage(false,"导入失败");
        		return false;
        	}
            
        } catch (Exception e) {
            PageResult.setMessage(false,"导入失败");
            e.printStackTrace();
            logger.error("导入模拟性能数据异常:",e);
            return false;
        }
    }
}
