package com.integration.controller;

import com.integration.aop.log.annotations.OptionLog;
import com.integration.entity.Dir;
import com.integration.entity.PageResult;
import com.integration.entity.Tree;
import com.integration.service.DirService;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
* @Package: com.integration.controller
* @ClassName: DirController
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 目录管理
*/
@RestController
public class DirController {

    @Autowired
    private DirService dirService;

    /**
     * 获取所有目录结构
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findCiDirList", method = RequestMethod.GET)
    public List <Dir> findDirList(int dir_type) {
        return dirService.findDirList(dir_type);
    }

    /**
     * 获取目录树形结构
     *
     * @return
     */
    @RequestMapping(value = "/findTreeList", method = RequestMethod.GET)
    public List<Tree> findTreeList(String name) {
        return dirService.findTreeList(name);
    }

    /**
     * @Method findTreeSort
     * @Description
     * @Param []
     * @Return int
     * @Author tzq
     * @Date 2020/4/13 13:54
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping(value = "/findTreeSort",method = RequestMethod.GET)
    public int findTreeSort(String pId) {
        Integer sort = dirService.fndTreeSort(pId);
        if (sort == null) {
            return 1;
        }
        return sort+1;
    }
    /**
     * 添加目录
     *
     * @return
     */
    @OptionLog(desc = "添加目录", module = "目录模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/addDir", method = RequestMethod.POST)
    public Object addDir(Dir dir, HttpServletRequest request) {
        dir.setCjr_id(TokenUtils.getTokenUserId());
        boolean results = dirService.dirNameExists(dir.getDir_name(),String.valueOf(dir.getDir_type()),null);
        if (results){
            PageResult.setMessage(false, "目录名称["+dir.getDir_name()+"]已存在!");
            return new Dir();
        }else{
            Dir result = dirService.addDir(dir, request);
            //成功失败标志
            boolean flag;
            if (result != null) {
                PageResult.setMessage(true, "添加成功!");
            } else {
                PageResult.setMessage(false, "添加失败!");
            }
            return result;
        }

    }

    /**
     * 修改目录
     *
     * @return
     */
    @OptionLog(desc = "修改目录", module = "目录模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/updateDir", method = RequestMethod.POST)
    public boolean updateDir(Dir dir, HttpServletRequest request) {
            dir.setXgr_id(TokenUtils.getTokenUserId());
            boolean flag = dirService.updateDir(dir);
            if (flag) {
                flag = true;
                PageResult.setMessage(true, "修改成功!");
            } else {
                flag = false;
                PageResult.setMessage(true, "修改失败!");
            }
            return flag;
    }

    /**
     * 删除目录
     *
     * @return
     */
    @OptionLog(desc = "删除目录", module = "目录模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/deleteDir", method = RequestMethod.POST)
    public boolean deleteDir(String dirId) {

        boolean flag = dirService.deleteDir(dirId);
        if (flag) {
            flag = true;
            PageResult.success("删除成功!");
        } else {
            flag = false;
            PageResult.fail("删除失败!");
        }
        return flag;

    }

    /**
     * 根据目录ID查询目录信息
     *
     * @return
     */
    @RequestMapping(value = "/findDirById", method = RequestMethod.GET)
    public Dir findDirById(String dirId) {
        return dirService.findDirById(dirId);
    }

    /**
     * 获取所有的图标文件夹
     */
    @RequestMapping(value = "/findIconDirList", method = RequestMethod.GET)
    public List<Dir> findIconDirList() {
        return dirService.findIconDirList();
    }

    /**
     * 判断目录名称是否存在
     */
    @OptionLog(desc = "判断目录名称是否存在", module = "目录模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/dirNameExists", method = RequestMethod.GET)
    public boolean dirNameExists(String dir_name,String dir_type) {
    	if (StringUtils.isEmpty(dir_type)) {
    		dir_type = "1";
		}
        boolean result = dirService.dirNameExists(dir_name,dir_type,null);
        if (result){
            PageResult.success();
        }else{
            PageResult.fail();
        }
        return result;

    }

    /**
     * 获取视图目录
     *
     * @param diagDir
     * @param user_id
     * @return
     */
    @RequestMapping(value = "/getDirListByParentId", method = RequestMethod.GET)
    public List<Map<String, Object>> getDirListByParentId(@RequestParam("diagDir")String diagDir,@RequestParam("user_id") String user_id){
    	return dirService.getDirListByParentId(diagDir, user_id);
    }

}
