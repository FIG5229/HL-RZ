package com.integration.fegin;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 性能管理 pmv-8005
 * @author zhuxd
 * @date 2019-09-20 15:00
 */
@FeignClient(value = "index",fallbackFactory = PmvServiceFallbackFactory.class)
 public interface PmvService {

   /**
    * 下载模板
    * @param ciTypeId
    * @param
    * @return
    */

   @RequestMapping(value = "/bigScreen/exportSimulationPerformance",consumes = MediaType.APPLICATION_PROBLEM_JSON_VALUE, method = RequestMethod.GET)
   Response exportDataRelToExcelAll(@RequestParam("ciTypeId")String ciTypeId);

   /**
    * 根据ciId查询是否挂载指标
    * @param ciId
    * @param ciCode
    * @return
    */
   @RequestMapping(value = "/bigScreen/getHangKpiByCiId", method = RequestMethod.GET)
   public Object getHangKpiByCiId(@RequestParam("ciId") String ciId,@RequestParam("ciCode") String ciCode);

    /**
     * 导入模拟性能数据
     * @param file
     * @param ciName
     * @param
     * @return
     */
    @RequestMapping(value = "/bigScreen/importSimulationPerformance",consumes = "multipart/form-data", method = RequestMethod.POST)
    public Object importSimulationPerformance( MultipartFile file, @RequestParam("ciName") String ciName);

  }
