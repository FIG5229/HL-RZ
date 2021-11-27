package com.integration.controller;

import com.integration.aop.log.annotations.OptionLog;
import com.integration.entity.PageResult;
import com.integration.entity.Parameter;
import com.integration.service.ParameterService;
import com.integration.utils.DataUtils;
import com.integration.utils.MapUtils;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.controller
* @ClassName: ParameterController
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 系统参数
*/
@RestController
public class ParameterController {

    @Autowired
    private ParameterService parameterService;
    private final static Logger logger = LoggerFactory
            .getLogger(ParameterController.class);

    /**
     * 添加参数
     *
     * @param param
     * @param request
     * @return
     */
    @OptionLog(desc = "添加参数", module = "参数模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/addParam", method = RequestMethod.POST)
    public Parameter addParam(Parameter param, HttpServletRequest request) {
        param.setCjr_id(TokenUtils.getTokenUserId());
        param.setSort("1");
        //要做名称验证
        List<Parameter> allParam = parameterService.findAllParam();
        for (Parameter parameter : allParam) {
            if (parameter.getPara_mc().equals(param.getPara_mc())) {
                PageResult.setMessage("名称已存在", false);
                return null;
            }
        }
        Parameter result = parameterService.addParameter(param);
        if (result != null) {
            PageResult.setMessage("添加成功!", true);
            return param;
        } else {
            PageResult.setMessage("添加异常!", true);
            return param;
        }
    }

    /**
     * 删除参数
     *
     * @param para_id
     * @return
     */
    @OptionLog(desc = "删除参数", module = "参数模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/deleteParam", method = RequestMethod.POST)
    public Object deleteParam(String para_id) {
        int result = parameterService.deleteParameter(para_id);
        if (result > 0) {
            PageResult.setMessage("删除成功!", true);
            return true;
        } else {
            PageResult.setMessage("删除失败!", false);
            return false;
        }
    }

    /**
     * 修改参数
     *
     * @param param
     * @param request
     * @return
     */
    @OptionLog(desc = "修改参数", module = "参数模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/updateParam", method = RequestMethod.POST)
    public Object updateParam(Parameter param, HttpServletRequest request) {
        param.setXgr_id(TokenUtils.getTokenUserId());
        int result = parameterService.updateParameter(param);
        if (result > 0) {
            PageResult.setMessage("修改成功!", true);
            return true;
        } else {
            PageResult.setMessage("修改失败!", false);
            return false;
        }
    }

    /**
     * 分页查询参数列表
     *
     * @param search
     * @param
     * @param
     * @param request
     * @return
     */
    @RequestMapping(value = "/findParameterList", method = RequestMethod.GET)
    public PageResult findParameterList(String search, String pageIndex,
                                        String pageCount, HttpServletRequest request) {
        // 总数
        Integer totalRecord = 0;
        List<Parameter> paralist = new ArrayList<Parameter>();
        boolean flag = false;
        try {
            totalRecord = parameterService.findParameterCount(search);
            paralist = parameterService.findParameter(search,
                    Integer.parseInt(pageCount), Integer.parseInt(pageIndex));
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            flag = false;
            return DataUtils.returnPr(null, "查询异常");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", paralist);
        map.put("total", totalRecord);
        return DataUtils.returnPr(map);
    }

    /**
     * 根据ID查询参数信息
     *
     * @param para_id
     * @return
     */
    @RequestMapping(value = "/findParamById", method = RequestMethod.GET)
    public Object findParamById(int para_id) {
        Parameter parameter = parameterService.findParameterById(para_id);
        return parameter;
    }

    /**
     * @Method findParByCode
     * @Description 根据Code查询参数信息
     * @Param [code]
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     * @Author sgh
     * @Date 2020/4/2 14:17
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping(value = "/findParByCode", method = RequestMethod.GET)
    public Map<String, Object> findParByCode(String code) {
        //判断Code参数是否为空
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        return parameterService.findParByCode(code);
    }

    /**
     * 根据Code查询参数信息
     * @param code
     * @return
     */
    @RequestMapping(value = "/findParByCodeHump", method = RequestMethod.GET)
    public Map<String, Object> findParByCodeHump(String code) {
        //判断Code参数是否为空
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        return MapUtils.formatHumpName(parameterService.findParByCode(code));
    }
}
