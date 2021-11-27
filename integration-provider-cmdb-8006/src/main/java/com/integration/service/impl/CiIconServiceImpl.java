package com.integration.service.impl;

import com.integration.dao.CiIconDao;
import com.integration.entity.CiIconInfo;
import com.integration.entity.Dir;
import com.integration.entity.PageResult;
import com.integration.entity.Tree;
import com.integration.generator.dao.IomCiIconMapper;
import com.integration.generator.entity.IomCiIcon;
import com.integration.generator.entity.IomCiIconExample;
import com.integration.service.CiIconService;
import com.integration.service.DirService;
import com.integration.utils.*;
import com.integration.utils.token.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author hbr
 * @version 1.0
 * @date 2018-12-03 09:49:27
 */
@Transactional
@Service
public class CiIconServiceImpl implements CiIconService {

    @Autowired
    private CiIconDao iCiIconDao;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private FastDfsUtils fastDfsUtils;
    
    @Autowired
    private DirService dirService;
    
    @Resource
    private IomCiIconMapper iomCiIconMapper;

    private final static Logger logger = LoggerFactory.getLogger(CiIconServiceImpl.class);


    /**
     * 查询单条
     *
     * @param iconId
     * @return
     */
    @Override
    public CiIconInfo getInfo(String iconId) {

        return iCiIconDao.getInfo(iconId);
    }

    /**
     * 修改单条记录
     *
     * @param path
     * @param iconId
     */
    @Override
    public int updateInfo(String path, String iconId) {
        //通过id查询出对象
        CiIconInfo info = iCiIconDao.getInfo(iconId);
        //上传返回组名
        ArrayList<String> list = new ArrayList<>();
        list.add(path);
        ArrayList<String> upload = fastDfsUtils.upload(list);
        //存储ip地址
        String storageIp = FastDfsUtils.getStorageip();
        String iconPath = storageIp + upload.get(0);
        //修改图标的路径
        info.setIcon_path(iconPath);
        //获取目录名称
        String dirName = null;
        Dir dir = dirService.findDirById(info.getIcon_dir());
        if (dir != null && !"".equals(dir)){
            dirName=dir.getDir_name();
        }
        info.setIcon_full_name(dirName+"|"+info.getIcon_name());
        return iCiIconDao.updateInfo(info);
    }


    /**
     * 删除单条记录
     *
     * @param iconId
     */
    @Override
    public int deleteInfo(String iconId) {
        return iCiIconDao.deleteInfo(iconId);
    }

    /**
     * 导入图标
     *
     * @param importPath
     * @return
     */
    @Override
    public List<CiIconInfo> importIcon(String importPath, String importDirId, HttpServletRequest request) {
    	//返回值集合
        ArrayList<CiIconInfo> iconList = new ArrayList<>();
        File file = new File(importPath);
        String zipDir = FileUtils.getZipFile();
        File zipFile = new File(zipDir);
        if (!zipFile.exists()) {
        	zipFile.mkdirs();
		}
        String destDir = zipDir + "/" + SeqUtil.nextId() + "/";
        try {
        	ArrayList<String> filePathList = null;
            ArrayList<String> uploadList = null;
            //解压缩
            fileUtils.decompression(file, destDir);
            //获取文件的绝对路径
            ArrayList<String> filePath = new ArrayList<>();
            filePathList = fileUtils.getFilePath(destDir, filePath);
            //上传文件
            uploadList = fastDfsUtils.upload(filePathList);
            //存储ip地址
            String storageIp = FastDfsUtils.getStorageip();
            //获取目录名称
            String dirName = null;
            Dir dir = dirService.findDirById(importDirId);
            if (dir != null && !"".equals(dir)){
                dirName=dir.getDir_name();
            }
            //路径集合
            ArrayList<String> resultList = new ArrayList<>();
            for (int i = 0; i < uploadList.size(); i++) {
                //（改造）绝对路径改为相对路径20200420
                resultList.add(("/"+uploadList.get(i)));
            }
            //获取文件名
            for (String s : filePathList) {
                CiIconInfo info = new CiIconInfo();
                String substring = s.substring(s.lastIndexOf(File.separator), s.lastIndexOf("."));
                String fileName = substring.substring(1);
                String iconForm = s.substring(s.lastIndexOf(".")).substring(1);
                String reg = "(JPEG|jpeg|JPG|jpg|GIF|gif|BMP|bmp|PNG|png)$";
                Pattern pattern = Pattern.compile(reg);
                Matcher matcher = pattern.matcher(iconForm.toLowerCase());
                if (!matcher.find()){
                    resultList.remove(filePathList.indexOf(s));
                    continue;
                }
                File file1 = new File(s);
                int fileLength = fileUtils.getFileLength(file1);
                String time = DateUtils.getDate();
                String userId = TokenUtils.getTokenUserId();
                info.setId(SeqUtil.nextId() +"");
                //名称
                info.setIcon_name(fileName);
                //目录
                info.setIcon_dir(importDirId);
                //类型
                info.setIcon_type(1);                          
                //格式
                info.setIcon_form(iconForm);
                //图标大小
                info.setIcon_size(fileLength);
                //上传时间
                info.setScsj(time);
                //上传人
                info.setScr_id(userId);
                info.setYxbz(1);
                //图标全限定名称
                info.setIcon_full_name(dirName+"|"+fileName);
                iconList.add(info);
            }

            //名称和路径组合到一起
            for (int i = 0; i < iconList.size(); i++) {
                iconList.get(i).setIcon_path(resultList.get(i));
            }
            for (CiIconInfo info : iconList) {
                iCiIconDao.addInfo(info);
            }
		} finally {
			fileUtils.delAllFile(destDir);
		}
        return iconList;
    }

    /**
     * 导出图标
     *
     * @param dirId
     */
    @Override
    public void exportIcon(String dirId) {
        //查询所有的图标
        ArrayList<CiIconInfo> ciIconList = (ArrayList<CiIconInfo>) iCiIconDao.searchByDirId(dirId);
        String downloadPath = null;
        try {
            //下载图标
            downloadPath = fastDfsUtils.download(ciIconList);
            //进行压缩
            fileUtils.compress(downloadPath);
            fileUtils.delAllFile(downloadPath);
        } catch (Exception e) {
            logger.error("exportIcon:异常");
            logger.error(e.getMessage());
        } finally {
            String destDir = FileUtils.getZipFile();
            fileUtils.delAllFile(destDir);
        }

    }

    /**
     * 通过名称查询
     *
     * @param map
     * @return
     */
    @Override
    public List<CiIconInfo> searchByName(Map map) {
        return iCiIconDao.searchByName(map);
    }

    /**
     * 通过目录id搜索
     *
     * @param iconDirId
     * @return
     */
    @Override
    public List<CiIconInfo> searchByDirId(String iconDirId) {
    	
        return iCiIconDao.searchByDirId(iconDirId);
    }
    
    @Override
    public List<Map<String, Object>> searchIconByDirId(String iconDirId,String iconName) {
        return iCiIconDao.searchIconByDirId(iconDirId,iconName);
    }
    
    @Override
    public List<Map<String, Object>> getIconByDmvImg(String iconType) {
        return iCiIconDao.getIconByDmvImg(iconType);
    }

    /**
     * 根据目录清空全部图标
     * @param dirId
     * @return
     */
    @Override
    public Object deleteIconByFolderId(String dirId){
    	List<CiIconInfo> listCiIconInfos=iCiIconDao.searchByDirId(dirId);
    	List<Tree> listTrees=dirService.findTreeList("");
    	Set<String> set=new HashSet<String>();
    	if(listTrees!=null && listTrees.size()>0) {
    		for(Tree tree:listTrees) {
    			List<Tree> list=tree.getChildren();
    			for(Tree tree2 :list) {
    				String iconString=tree2.getIcon();
    				set.add(iconString);
    			}
        	}
    	}
    	for(CiIconInfo ciIconInfo:listCiIconInfos) {
    		String iconPath=ciIconInfo.getIcon_path();
    		String iconName=ciIconInfo.getIcon_name();
    		boolean flag=set.contains(iconPath);
    		if(flag) {
    			//return "图标"+"["+iconName+"]"+"在对象管理中已使用,无法清空!";
    			PageResult.setMessage("图标"+"["+iconName+"]"+"在对象管理中已使用,无法清空!", false);
    			return "";
    		}
    	}
    	
        int result = iCiIconDao.deleteIconByFolderId(dirId);
        if(result > 0){
            PageResult.setMessage("清除成功", true);
        }else{
            PageResult.setMessage("清除成功", false);
        }
        return String.valueOf(result);
    }

    /**
     * 批量删除图片
     * @param dirId
     * @return
     */
    @Override
    public Object deleteIconByIcons(String dirId, String icons){
    	List<String> listIconIds = Arrays.asList(icons.split(","));
    	IomCiIconExample example=new IomCiIconExample();
    	example.createCriteria().andIdIn(listIconIds).andYxbzEqualTo(1);
    	List<IomCiIcon> list=iomCiIconMapper.selectByExample(example);
    	List<Tree> listTrees=dirService.findTreeList("");
    	Set<String> set=new HashSet<String>();
    	if(listTrees!=null && listTrees.size()>0) {
    		for(Tree tree:listTrees) {
    			List<Tree> list1=tree.getChildren();
    			for(Tree tree2 :list1) {
    				String iconString=tree2.getIcon();
    				set.add(iconString);
    			}
        	}
    	}
    	for(IomCiIcon iomCiIcon:list) {
    		String iconPath=iomCiIcon.getIconPath();
    		String iconName=iomCiIcon.getIconName();
    		boolean flag=set.contains(iconPath);
    		if(flag) {
    			PageResult.setMessage("图标"+"["+iconName+"]"+"在对象管理中已使用,无法批量删除!", false);
    			return "";
    		}
    	}
    	
        int result = 0;
        try{
            result = iCiIconDao.deleteIconByIcons(dirId, icons.split(","));
            if(result > 0){
                PageResult.setMessage("清除成功", true);
            }else{
                PageResult.setMessage("清除成功", false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return String.valueOf(result);
    }
    
    @Override
    public List<Map<String,Object>> getIconInfoByIconFullName(String iconFullName){
    	return iCiIconDao.getIconInfoByIconFullName(iconFullName);
    }
    
    @Override
    public List<String> getIconInfoByIconFullNames(Map<String,Object> itemMap){
    	return iCiIconDao.getIconInfoByIconFullNames(itemMap);
    }

}