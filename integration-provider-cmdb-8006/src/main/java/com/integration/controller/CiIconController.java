package com.integration.controller;

import com.alibaba.fastjson.JSON;
import com.integration.aop.log.annotations.OptionLog;
import com.integration.dao.CiIconDao;
import com.integration.entity.CiIconInfo;
import com.integration.entity.Dir;
import com.integration.entity.PageResult;
import com.integration.generator.dao.IomCiDirMapper;
import com.integration.generator.dao.IomCiIconMapper;
import com.integration.generator.dao.IomCiTypeMapper;
import com.integration.generator.entity.*;
import com.integration.service.CiIconService;
import com.integration.service.DirService;
import com.integration.utils.DataUtils;
import com.integration.utils.FastDfsUtils;
import com.integration.utils.FileUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author hbr
 * @version 1.0
 * @date 2018-12-03 09:49:27
 */
@RestController
public class CiIconController {


    @Autowired
    private CiIconService ciIconService;

    @Autowired
    private DirService dirService;

    @Autowired
    private FastDfsUtils fast;

    @Resource
    private IomCiIconMapper iomCiIconMapper;

    @Resource
    private IomCiTypeMapper iomCiTypeMapper;

    @Resource
    private IomCiDirMapper iomCiDirMapper;
    
    @Autowired
    private CiIconDao iCiIconDao;

    private static final Logger logger = LoggerFactory.getLogger(CiIconController.class);

    /**
     * 导入图标
     *
     * @param file
     * @param importDirId
     * @return
     * @throws Exception
     */
    @OptionLog(desc = "导入图标", module = "图标模块", writeParam = false, writeResult = false)
    @RequestMapping(value = "/importIcon", method = RequestMethod.POST)
    public List<CiIconInfo> importIcon(MultipartFile file, String importDirId,
                                       HttpServletRequest request) {
        try {
            List<CiIconInfo> infoList = null;
            InputStream is = null;
            FileOutputStream fos = null;
            String path = null;
            // 判断文件是否为空
            if (!file.isEmpty()) {
                try {

                    String fileName = file.getOriginalFilename();
                    String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                    path = FileUtils.getBasePath() + SeqUtil.nextId() + "." + suffix;

                    File file1 = new File(path);
                    is = file.getInputStream();

                    if (!file1.getParentFile().exists()) {
                        file1.getParentFile().mkdirs();
                    }

                    fos = new FileOutputStream(file1);

                    byte[] by = new byte[1024 * 30];
                    int count = -1;
                    if (is != null) {
                        while ((count = is.read(by, 0, by.length)) != -1) {
                            fos.write(by, 0, count);
                        }
                        fos.flush();
                    }

                    infoList = ciIconService.importIcon(path, importDirId, request);

                    file1.delete();
                } catch (IOException e) {
                    logger.error("importIcon:异常");
                    PageResult.fail("上传失败！");
                    logger.error(e.getMessage());
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                    }
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                    }

                    File file1 = new File(path);
                    file1.delete();
                }
            }
            logger.error("ceshi"+infoList.size());
            return infoList;
        }catch (Exception e){
            PageResult.fail("文件异常！");
            return null;
        }
    }

    public boolean isWindows() {
        return System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1;
    }

    /**
     * 导出图标
     *
     * @param dirId
     */
    @OptionLog(desc = "导出图标", module = "图标模块", writeParam = true, writeResult = false)
    @RequestMapping(value = "/exportIcon", method = RequestMethod.GET)
    public void exportIcon(String dirId, HttpServletResponse response)
            throws Exception {
        ciIconService.exportIcon(dirId);
        File file = new File(FileUtils.getDown() + "icon.zip");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        response.setContentType("application/zip;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        FileInputStream fis = null;
        OutputStream opt = null;
        try {
            fis = new FileInputStream(file);
            opt = response.getOutputStream();
            int b;
            while ((b = fis.read()) != -1) {
                opt.write(b);
            }
        } catch (Exception e) {
            logger.error("exportIcon:异常");
            logger.error(e.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (opt != null) {
                opt.close();
                opt.flush();
            }
            file.delete();
        }
    }

    /**
     * 通过图标名称搜索
     *
     * @return
     */
    @RequestMapping(value = "/searchByName", method = RequestMethod.GET)
    public List<CiIconInfo> searchByName(@RequestParam Map map) {
        List<CiIconInfo> infoList = ciIconService.searchByName(map);
        return infoList;
    }


    /**
     * 上传文件到fastDFS服务器
     *
     * @param imgurl
     * @return
     */
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public List<String> uploadImg(String imgurl) {
        ArrayList<String> list = new ArrayList<String>();
        list.add(imgurl);
        return fast.upload(list);
    }

    /**
     * 上传文件到fastDFS服务器
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/uploadImgDcv", method = RequestMethod.POST)
    public String uploadImgDcv(String imgurl) {
        ArrayList list = new ArrayList();
        list.add(imgurl);
        ArrayList<String> list1 = fast.upload(list);
        String patha = "/" + list1.get(0);
        return patha;
    }
    
    /**
     * 获取文件服务器地址
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getFastDfsUrl", method = RequestMethod.GET)
    public String getFastDfsUrl() {      
        String path = FastDfsUtils.getStorageip();
        return path;
    }


    /**
     * 通过id删除单个图标
     *
     * @param iconId
     */
    @OptionLog(desc = "通过ID删除", module = "图标模块", writeParam = false, writeResult = false)
    @RequestMapping(value = "/deleteInfo", method = RequestMethod.POST)
    public boolean deleteInfo(String iconId) {
        boolean flag = false;
        IomCiIcon iomCiIcon = iomCiIconMapper.selectByPrimaryKey(iconId);
        if (iomCiIcon != null) {
            String path = iomCiIcon.getIconPath();
            IomCiTypeExample example = new IomCiTypeExample();
            example.createCriteria().andCiTypeIconEqualTo(path).andYxbzEqualTo(1);
            List<IomCiType> list = iomCiTypeMapper.selectByExample(example);
            if (list != null && list.size() > 0) {
                PageResult.setMessage("该图标在对象管理中已被使用，无法删除!", flag);
                return flag;
            }
        }

        int a = ciIconService.deleteInfo(iconId);
        if (a > 0) {
            flag = true;
        }
        if (flag == true) {
            PageResult.setMessage("删除成功", flag);

        } else {
            PageResult.setMessage("删除失败", flag);
        }
        return flag;

    }

    /**
     * 修改单个图标
     *
     * @param file
     * @param iconId
     */
    @OptionLog(desc = "修改单个图标", module = "图标模块", writeParam = false, writeResult = false)
    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    public boolean updateInfo(MultipartFile file, String iconId,
                              HttpServletRequest request) {

        boolean flag = false;
        String path = FileUtils.getBasePath() + file.getOriginalFilename();
        File file1 = new File(path);
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = file.getInputStream();

            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }

            fos = new FileOutputStream(file1);

            byte[] by = new byte[1024 * 30];
            int count = -1;
            if (is != null) {
                while ((count = is.read(by, 0, by.length)) != -1) {
                    fos.write(by, 0, count);
                }
            }
            fos.flush();
        } catch (IOException e) {
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
        }

        int a = ciIconService.updateInfo(path, iconId);

        if (a > 0) {
            flag = true;
        }

        return flag;
    }

    /**
     * 通过目录id搜索
     *
     * @param iconDirId
     * @return
     */
    @RequestMapping(value = "/searchByDir", method = RequestMethod.GET)
    public List<CiIconInfo> searchByDirId(String iconDirId) {
        List<CiIconInfo> infoList = ciIconService.searchByDirId(iconDirId);
        return infoList;
    }

    /**
     * 获取所有目录结构
     *
     * @param dir_type
     * @return
     */
    @RequestMapping(value = "/findDirList", method = RequestMethod.GET)
    public PageResult findDirList(int dir_type) {
        List<Dir> dirList = dirService.findDirList(dir_type);
        return DataUtils.returnPr(dirList);
    }
    
    /**
     * 获取所有目录结构(视图查询当前登录人层级文件夹)
     *
     * @param dir_type
     * @return
     */
    @RequestMapping(value = "/findDirListByDiagram", method = RequestMethod.GET)
    public List<Dir> findDirListByDiagram(int dir_type,String cjrId) {
        List<Dir> dirList = dirService.findDirListByDiagram(dir_type,cjrId);
        return dirList;
    }

    /**
     * 添加目录
     *
     * @param dir
     * @return
     */
    @OptionLog(desc = "添加目录", module = "图标模块", writeParam = false, writeResult = false)
    @RequestMapping(value = "/addFolder", method = RequestMethod.POST)
    public Dir addDir(Dir dir, HttpServletRequest request) {
        String domainId = TokenUtils.getTokenOrgDomainId();
        Dir result = new Dir();
        String dirName = dir.getDir_name();
        boolean results = dirService.dirNameExists(dirName,String.valueOf(dir.getDir_type()),domainId);
        if (results) {
            PageResult.fail("图标名称不能重复!");
            return result;
        }

        result = dirService.addDir(dir, request);

        return result;
    }

    /**
     * 删除目录
     *
     * @param dirId
     * @return
     */
    @OptionLog(desc = "删除目录", module = "图标模块", writeParam = true, writeResult = false)
    @RequestMapping(value = "/deleteFolder", method = RequestMethod.POST)
    public boolean deleteDir(String dirId) {
        boolean flag = false;
        flag = dirService.deleteDir(dirId);
        return flag;
    }

    /**
     * 修改目录
     *
     * @param dir
     * @return
     */
    @OptionLog(desc = "修改目录", module = "图标模块", writeParam = true, writeResult = false)
    @RequestMapping(value = "/updateFolder", method = RequestMethod.POST)
    public boolean updateDir(Dir dir) {
        boolean flag = false;
        //查询单条信息
        IomCiDir dir1 = iomCiDirMapper.selectByPrimaryKey(dir.getId());
        String dirName = dir.getDir_name();
        if (!dir1.getDirName().equals(dirName)) {
            String parentDirId = dir.getParent_dir_id();
            IomCiDirExample example = new IomCiDirExample();
            example.createCriteria().andDirNameEqualTo(dirName).andParentDirIdEqualTo(parentDirId).andYxbzEqualTo(1);
            List<IomCiDir> list = iomCiDirMapper.selectByExample(example);
            if (list != null && list.size() > 0) {
                PageResult.fail("图标名称不能重复!");
                return flag;
            }
        }
        flag = dirService.updateDir(dir);
        return flag;
    }

    /**
     * 删除文件服务器上的文件
     *
     * @param iconPath
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteServerFile", method = RequestMethod.POST)
    public boolean deleteServerFile(String iconPath) throws Exception {
        boolean flag = false;
        int count = fast.delete(iconPath);
        if (count > 0) {
            flag = true;

        } else {
            flag = false;
        }

        return flag;
    }
    /**
     * @Author: ztl
     * date: 2021-08-06
     * @description: 获取视图中图标
     */
    @RequestMapping(value = "/getDiagramIconAll", method = RequestMethod.GET)
    public List<Map<String, Object>> getDiagramIconAll(@RequestParam String iconName,@RequestParam String dirId) throws Exception {
        IomCiDirExample example = new IomCiDirExample();
        List<Integer> dirTypeList = new ArrayList<>();
        dirTypeList.add(3);
        dirTypeList.add(6);
        if (!"".equals(dirId) && dirId != null){
            example.createCriteria().andYxbzEqualTo(1).andDirTypeIn(dirTypeList).andIdEqualTo(dirId);
        }else{
            example.createCriteria().andYxbzEqualTo(1).andDirTypeIn(dirTypeList);
        }
        List<IomCiDir> list = iomCiDirMapper.selectByExample(example);
        List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
        for (IomCiDir iomCiDir : list) {
            String id = iomCiDir.getId();
            List<Map<String, Object>> list1 = ciIconService.searchIconByDirId(id,iconName);
            Map<String, Object> map = bean2map(iomCiDir);
            map.put("child", list1);
            list2.add(map);
        }
        return list2;
    }

    /**
     * @Author: ztl
     * date: 2021-08-06
     * @description: 增加图标
     */
    @RequestMapping(value = "/addIconByDmvUploadImg", method = RequestMethod.POST)
    public String addIconByDmvUploadImg(@RequestParam Map map) {
    	CiIconInfo ciIconInfo = JSON.parseObject(JSON.toJSONString(map), CiIconInfo.class);
        //获取目录名称
        String dirName = null;
        Dir dir = dirService.findDirById(ciIconInfo.getIcon_dir());
        if (dir != null && !"".equals(dir)){
            dirName=dir.getDir_name();
        }
    	try {
    	    ciIconInfo.setIcon_full_name(dirName+"|"+ciIconInfo.getIcon_name());
    		iCiIconDao.addInfo(ciIconInfo);
			return "1";
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
    	return "0";
    }
    /**
     * @Author: ztl
     * date: 2021-08-06
     * @description: 获取图标
     */
    @RequestMapping(value = "/getIconByDmvImg", method = RequestMethod.POST)
    public List<Map<String, Object>> getIconByDmvImg(String iconType) {
    	List<Map<String, Object>> list=ciIconService.getIconByDmvImg(iconType);
    	return list;
    }
    /**
     * @Author: ztl
     * date: 2021-08-06
     * @description: 删除图标
     */
    @RequestMapping(value = "/deleteIconByDmvImg", method = RequestMethod.POST)
    public String deleteIconByDmvImg(String id,String iconPath) {
    	int a = ciIconService.deleteInfo(id);
    	boolean flag = false;
    	try {
    		flag=deleteServerFile(iconPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	if(a==1) {
    		return "1";
    	}
    	return null;
    }
    
    /**
     * @Author: ztl
     * date: 2021-08-06
     * @description:把JavaBean转化为map
     */
    public static Map<String, Object> bean2map(Object bean) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //获取JavaBean的描述器
        BeanInfo b = Introspector.getBeanInfo(bean.getClass(), Object.class);
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

    /**
     * 根据目录清空全部图标
     * @param dirId
     * @return
     */
    @RequestMapping(value = "/deleteIconByFolderId", method = RequestMethod.POST)
    public Object deleteIconByFolderId(String dirId){
        return ciIconService.deleteIconByFolderId(dirId);
    }

    /**
     * 批量删除图片
     * @param dirId
     * @return
     */
    @RequestMapping(value = "/deleteIconByIcons", method = RequestMethod.POST)
    public Object deleteIconByIcons(String dirId, String icons){
        return ciIconService.deleteIconByIcons(dirId, icons);
    }
    
    /**
     * 根据目录名称|图标名称获取icon信息
     * @return
     */
    @RequestMapping(value = "/getIconInfoByIconFullName", method = RequestMethod.GET)
    public List<Map<String,Object>> getIconInfoByIconFullName(String iconFullName){
    	List<Map<String,Object>> list=ciIconService.getIconInfoByIconFullName(iconFullName);
        return list;
    }
    
    /**
     * 根据目录名称|图标名称获取icon信息
     * @return
     */
    @RequestMapping(value = "/getIconInfoByIconFullNames", method = RequestMethod.POST)
    public List<String> getIconInfoByIconFullNames(String iconFullNames){
        if (iconFullNames != null && !"".equals(iconFullNames)) {
            List<String> list = Arrays.asList(iconFullNames.split(","));
            Map<String, Object> itemMap = new HashMap<String, Object>();
            if(iconFullNames!=null && !"".equals(iconFullNames)) {
    			itemMap.put("iconFullNameList", iconFullNames.split(","));
    		}else {
    			itemMap.put("iconFullNameList", "");
    		}
            List<String> listData = ciIconService.getIconInfoByIconFullNames(itemMap);
            List<String> returnList=new ArrayList<>();
            if(listData==null || listData.size()==0) {
            	return list;
            }else {
            	for(String iconFullName:list) {
            		boolean flag=listData.contains(iconFullName);
                	if(!flag) {
                		returnList.add(iconFullName);
                	}
            	}
            }
            return returnList;
        }
        return null;
    }
}