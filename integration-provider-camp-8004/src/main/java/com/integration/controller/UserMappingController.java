package com.integration.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integration.entity.Configure;
import com.integration.entity.PageResult;
import com.integration.entity.Subsystem;
import com.integration.entity.UserMapping;
import com.integration.service.ConfigureService;
import com.integration.service.SubsystemService;
import com.integration.service.UserMappingService;
import com.integration.utils.AESUtils;
import com.integration.utils.DataUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
/**
* @Package: com.integration.controller
* @ClassName: UserMappingController
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 用户映射
*/
@RestController
public class UserMappingController {
	private static final Logger logger = LoggerFactory.getLogger(UserMappingController.class);
    @Autowired
    private UserMappingService mappingService;

    @Autowired
    private ConfigureService configureService;

    @Autowired
    private SubsystemService subsystemService;

    private static final String KEY = "303EAA507626351F22B71C086E998F49";


    /**
     * 添加成功
     *
     * @param userMapping
     * @param request
     * @return
     */
    @PostMapping(value = "/addUserM")
    public PageResult addUserM(UserMapping userMapping, HttpServletRequest request) {
        int result = 0;
        try {
            //获取当前登录人Id
            String cjrId = TokenUtils.getTokenUserId();
            userMapping.setCzry_id(cjrId);
            //同一个用户和子系统只有一个用户名
            //查询所有用户名
            String dldm = mappingService.findAllDldm(userMapping.getCzry_id(), userMapping.getSubsystem());
            if (dldm != null) {
                return DataUtils.returnPr(false, "用户名已存在");
            }
            //添加id
            userMapping.setId(SeqUtil.nextId() + "");
            //对密码进行加密
            byte[] aesEncode = AESUtils.getAESEncode(KEY, userMapping.getMapping_password());
            //二进制转十六进制
            String password = AESUtils.parseByte2HexStr(aesEncode);
            //将加密的密码添加到对象
            userMapping.setMapping_password(password);
            result = mappingService.addUserM(userMapping);
        } catch (Exception e) {
            return DataUtils.returnPr(false, "添加异常");
        }
        if (result > 0) {
            return DataUtils.returnPr(true, "添加成功");
        } else {
            return DataUtils.returnPr(false, "添加失败");
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteUserM")
    public PageResult deleteUserM(String id) {
        int result = mappingService.deleteUserM(id);
        if (result > 0) {
            return DataUtils.returnPr(true, "删除成功");
        } else {
            return DataUtils.returnPr(false, "删除失败");
        }
    }

    /**
     * 修改
     *
     * @param userMapping
     * @return
     */
    @PutMapping(value = "/updateUserM")
    public PageResult updateUserM(UserMapping userMapping) {
        //通过id查询
        int result = 0;
        UserMapping mapping = mappingService.findById(userMapping.getId());
        if (!mapping.getMapping_dldm().equals(userMapping.getMapping_dldm()) && mapping.getSubsystem().equals(userMapping.getSubsystem())) {
            result = mappingService.updateUserM(userMapping);
        } else {
            //查询当前用户的所有子系统
            List<String> systemList = mappingService.findSystemByCzry(userMapping.getCzry_id());
            for (String s : systemList) {
                if (userMapping.getSubsystem().equals(s)) {
                    return DataUtils.returnPr(false, "同一个子系统只能有一个用户");
                }
            }
            int count = 0;
            for (String s : systemList) {
                if (!userMapping.getSubsystem().equals(s)){
                    count ++ ;
                }
            }
            if (count <= systemList.size()){
                result = mappingService.updateUserM(userMapping);
            }
        }
        if (result > 0) {
            return DataUtils.returnPr(true, "修改成功");
        } else {
            return DataUtils.returnPr(false, "修改失败");
        }
    }

    /**
     * 查询
     *
     * @return
     */
    @GetMapping(value = "/findAllM")
    public PageResult findAllM(HttpServletRequest request) {
        List<UserMapping> list = new ArrayList<>();
        try {
            //获取当前登录人Id
            String czry_id = TokenUtils.getTokenUserId();
            list = mappingService.findAllM(czry_id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return DataUtils.returnPr(true, "查询成功", list);
    }

    /**
     * 查询所有配置
     *
     * @return
     */
    @GetMapping(value = "/findAllC")
    public PageResult findAllC() {
        List<Configure> list = new ArrayList<>();
        try {
            list = configureService.findAllC();
        } catch (Exception e) {
            return DataUtils.returnPr(false, "查询异常");
        }
        return DataUtils.returnPr(true, "查询成功", list);
    }

    /**
     * 根据id查询密码
     *
     * @param
     * @return
     */
    @GetMapping(value = "/findPassWordById")
    public PageResult findPassWordById(String url, HttpServletRequest request) {
        //获取当前登录人Id
        String czry_id = TokenUtils.getTokenUserId();
        Map<Object, Object> map = new HashMap<>();
        try {
            //根据url获取子系统
            String subsystem = configureService.findSubsystemByUrl(url);
            //根据用户id和url查询用户名和密码
            map = mappingService.findPassWordByID(czry_id, url);
        } catch (Exception e) {
            return DataUtils.returnPr(false, "查询异常");
        }
        return DataUtils.returnPr(true, "查询成功", map);
    }

    /**
     * 查询子系统下拉框
     *
     * @return
     */
    @GetMapping(value = "/findAllS")
    public PageResult findAllS(HttpServletRequest request) {
        //获取当前登录人Id
        String czry_id = TokenUtils.getTokenUserId();
        //根据登录人id获取所有的子系统
        List<String> system = mappingService.findSystemByCzry(czry_id);
        List<Subsystem> list = new ArrayList<>();
        try {
            list = subsystemService.findAllS();
            for (String s : system) {
                for (int i = 0; i < list.size(); i++) {
                    if (s.equals(list.get(i).getId())) {
                        list.remove(i);
                    }
                }
            }
        } catch (Exception e) {
            return DataUtils.returnPr(false, "查询异常");
        }
        return DataUtils.returnPr(true, "查询成功", list);
    }

    /**
     * 查询所有子系统
     *
     * @return
     */
    @GetMapping(value = "/findAllSS")
    public PageResult findAllSS() {
        List<Subsystem> list = new ArrayList<>();
        try {
            list = subsystemService.findAllS();
        } catch (Exception e) {
            return DataUtils.returnPr(false, "查询异常");
        }
        return DataUtils.returnPr(true, "查询成功", list);
    }

    /**
     * 添加子系统
     *
     * @param subsystem
     * @return
     */
    @PostMapping(value = "/addSubsystem")
    public PageResult addSubsystem(Subsystem subsystem) {
        //查询所有名称
        List<String> list = subsystemService.findAllName();
        for (String s : list) {
            if (subsystem.getName().equals(s)) {
                return DataUtils.returnPr(false, "名称已存在");
            }
        }
        subsystem.setId(SeqUtil.nextId() + "");
        int result = subsystemService.addSubsystem(subsystem);
        if (result > 0) {
            return DataUtils.returnPr(true, "添加成功");
        }
        return DataUtils.returnPr(false, "添加失败");
    }


    /**
     * 删除子系统
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteSubsystem")
    public PageResult deleteSubsystem(String id,HttpServletRequest request) {
        try {
            String czryId = TokenUtils.getTokenUserId();
            int result = subsystemService.deleteSubsystem(czryId,id);
            if (result > 0) {
                return DataUtils.returnPr(true, "删除成功");
            }
            return DataUtils.returnPr(false, "删除失败");
        } catch (Exception e) {
            return DataUtils.returnPr(false, "删除异常");
        }
    }

    /**
     * 修改子系统
     *
     * @param subsystem
     * @return
     */
    @PutMapping(value = "/updateSubsystem")
    public PageResult updateSubsystem(Subsystem subsystem) {
        //根据id查询名字
        String nameById = subsystemService.findNameById(subsystem.getId());
        //没有改名字
        int result = 0;
        if (nameById.equals(subsystem.getName())) {
            //没有改名字,可以修改
            //serverName
            String kpiCode = subsystemService.findKpiCode(subsystem.getName());
            subsystem.setServerName(kpiCode);
            result = subsystemService.updateSubsystem(subsystem);
            //改了名字
        } else {
            List<String> list = subsystemService.findAllName();
            for (String s : list) {
                if (subsystem.getName().equals(s)) {
                    return DataUtils.returnPr(false, "名称已存在");
                }
            }
            //serverName
            String kpiCode = subsystemService.findKpiCode(subsystem.getName());
            subsystem.setServerName(kpiCode);
            result = subsystemService.updateSubsystem(subsystem);
        }
        if (result > 0) {
            return DataUtils.returnPr(true, "修改成功");
        }
        return DataUtils.returnPr(false, "修改失败");
    }

    /**
     * 修改密码
     *
     * @param newPassword
     * @param id
     * @return
     */
    @PutMapping(value = "/updatePassword")
    public PageResult updatePassword(String newPassword, String id) {
        int result = 0;
        try {
            //对密码进行加密
            byte[] aesEncode = AESUtils.getAESEncode(KEY, newPassword);
            //二进制转换为十六进制
            String password = AESUtils.parseByte2HexStr(aesEncode);
            result = mappingService.updatePassword(password, id);
        } catch (Exception e) {
            return DataUtils.returnPr(false, "修改异常");
        }
        if (result > 0) {
            return DataUtils.returnPr(true, "修改成功");
        }
        return DataUtils.returnPr(false, "修改失败");
    }

    /**
     * 验证密码
     *
     * @param oldPassword
     * @param subsystem
     * @param request
     * @return
     */
    @PostMapping(value = "/checkPassword")
    public PageResult checkPassword(String oldPassword, String subsystem, HttpServletRequest request) {
        //获取当前登录人Id
        String czry_id = TokenUtils.getTokenUserId();
        String password = mappingService.findPasswordByCzry(czry_id, subsystem);
        //对密码进行解密
        String decrypt = AESUtils.Decrypt(password, KEY);
        if (decrypt.equals(oldPassword)) {
            return DataUtils.returnPr(true, "密码正确");
        } else {
            return DataUtils.returnPr(false, "密码错误");
        }
    }
}
