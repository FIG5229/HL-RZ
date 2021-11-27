package com.integration.service.impl;

import com.integration.dao.DirDao;
import com.integration.entity.Dir;
import com.integration.entity.Tree;
import com.integration.generator.dao.IomCiDirMapper;
import com.integration.generator.dao.IomCiIconMapper;
import com.integration.generator.entity.IomCiDir;
import com.integration.generator.entity.IomCiDirExample;
import com.integration.generator.entity.IomCiIcon;
import com.integration.generator.entity.IomCiIconExample;
import com.integration.service.DirService;
import com.integration.utils.DateUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
* @Package: com.integration.service.impl
* @ClassName: DirServiceImpl
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 目录管理
*/
@Transactional
@Service
public class DirServiceImpl implements DirService {

	@Autowired
	private DirDao dirDao;

	@Resource
	private IomCiDirMapper iomCiDirMapper;
	
	@Resource
	private IomCiIconMapper iomCiIconMapper;
	
	/**
	 * 获取所有目录结构
	 * 
	 * @return
	 */
	@Override
	public List<Dir> findDirList(int type) {
		return selectDirListAll(dirDao.findDirList(type));
	}
	
	/**
	 * 获取所有目录结构
	 * 
	 * @return
	 */
	@Override
	public List<Dir> findDirListByDiagram(int type,String cjrId) {
		List<Dir> list=dirDao.findDirListByDiagram(type,cjrId);
		return list;
	}

	/**
	 * 初始化iomci库中相应数据域
	 * @param domainId
	 */
	@Override
	public void updateDirDomian(String domainId) {
		//更新目录数据域
		dirDao.updateDirDomian(domainId);
		//更新大类数据域
		dirDao.updateCiTypeDomian(domainId);
	}

	public List<Tree> selectTreeListAll(List<Tree> treeList) {
		// 根节点
		List<Tree> root = new ArrayList<Tree>();
		// 获取父节点
		for (Tree nav : treeList) {
			if ("0".equals(nav.getPid())) {
				// 父节点是0的，为根节点。
				root.add(nav);
			}
		}
		// 排序
		Collections.sort(root, (s1, s2) -> (s1.getSort() - s2.getSort()));

		// 为根菜单设置子菜单，getClild是递归调用的
		for (Tree nav : root) {
			/* 获取根节点下的所有子节点 使用getChild方法 */
			List<Tree> childList = getChild(nav.getId(), treeList);
			// 给根节点设置子节点
			nav.setChildren(childList);
		}

		return root;
	}

	public List<Dir> selectDirListAll(List<Dir> dirList) {
		// 根节点
		List<Dir> root = new ArrayList<Dir>();
		// 获取父节点
		for (Dir dir : dirList) {
			if ("0".equals(dir.getParent_dir_id())) {
				// 父节点是3的，为根节点。
				root.add(dir);
			}
		}
		// 排序
		Collections.sort(root, (s1, s2) -> (s1.getSort() - s2.getSort()));

		// 为根菜单设置子菜单，getClild是递归调用的
		for (Dir dir : root) {
			/* 获取根节点下的所有子节点 使用getChild方法 */
			List<Dir> childList = getDirChild(dir.getId(), dirList);
			// 给根节点设置子节点
			dir.setChildren(childList);
		}

		return root;
	}

	/**
	 * 获取子节点
	 * 
	 * @param id
	 * 父节点id
	 * 所有菜单列表
	 * @return 每个根节点下，所有子菜单列表
	 */
	public List<Tree> getChild(String id, List<Tree> allTree) {
		// 子菜单
		List<Tree> childList = new ArrayList<Tree>();
		for (Tree nav : allTree) {
			// 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
			// 相等说明：为该根节点的子节点。
			if (nav.getPid().equals(id)) {
				childList.add(nav);
			}
		}
		// 递归
		for (Tree nav : childList) {
			nav.setChildren(getChild(nav.getId(), allTree));
		}
		// 如果节点下没有子节点，返回一个空List（递归退出）
		if (childList.size() == 0) {
			return new ArrayList<Tree>();
		}
		return childList;
	}

	public List<Dir> getDirChild(String id, List<Dir> allDir) {
		// 子菜单
		List<Dir> childList = new ArrayList<Dir>();
		for (Dir dir : allDir) {
			// 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
			// 相等说明：为该根节点的子节点。
			if (dir.getParent_dir_id().equals(id)) {
				childList.add(dir);
			}
		}
		// 递归
		for (Dir dir : childList) {
			dir.setChildren(getDirChild(dir.getId(), allDir));
		}
		// 如果节点下没有子节点，返回一个空List（递归退出）
		if (childList.size() == 0) {
			return new ArrayList<Dir>();
		}
		return childList;
	}

	@Override
	public List<Tree> findTreeList(String name) {
		String domainId = TokenUtils.getTokenOrgDomainId();
		if (!"".equals(name) && null != name) {
			 return selectTreeListAll(dirDao.findTreeListByName(name,domainId));
		}
		return selectTreeListAll(dirDao.findTreeList(name,domainId));
	}
	
	@Override
	public List<Tree> findTreeListByViewModel(String name) {
		//数据域ID,区分不同省公司用户查询的数据
		String domainId = TokenUtils.getTokenOrgDomainId();
		if (!"".equals(name) && null != name) {
			 return selectTreeListAll(dirDao.findTreeListByName(name,domainId));
		}
		return selectTreeListAll(dirDao.findTreeList(name,domainId));
	}

	/**
	 * 根据目录名称获取目录对象
	 * @param dirName 目录名称
	 * @return
	 */
	@Override
	public Dir getDirByDirName(String dirName) {
		return dirDao.getDirByDirName(dirName);
	}


	@Override
	public Integer fndTreeSort(String pId) {
		return dirDao.fndTreeSort(pId);
	}
	/**
	 * 获取所有的图标文件夹
	 * 
	 * @return
	 */
	@Override
	public List<Dir> findIconDirList() {
		return selectDirListAll(dirDao.findIconDirList());
	}

	/**
	 * 添加目录
	 * 
	 * @return
	 */
	@Override
	public Dir addDir(Dir dir, HttpServletRequest request) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenOrgDomainId();
		// 获取序列
		dir.setId(SeqUtil.nextId() + "");
		// 创建时间
		dir.setCjsj(DateUtils.getDate());
		// 有效标志
		dir.setYxbz(1);
		dir.setCjr_id(TokenUtils.getTokenUserId());
		if ("shape".equals(dir.getDir_name())){
			dir.setDomain_id(null);
		}else {
			dir.setDomain_id(domainId==null?"-1":domainId);
		}
		int result = dirDao.addDir(dir);
		if (result > 0) {
			return dir;
		}
		return null;
	}

	/**
	 * 删除目录
	 * 
	 * @return
	 */
	@Override
	public boolean deleteDir(String dirId) {
		//获取当前以及孩子的id
		IomCiDirExample iomCiDirExample = new IomCiDirExample();
		com.integration.generator.entity.IomCiDirExample.Criteria criteria = iomCiDirExample.createCriteria();
		criteria.andYxbzEqualTo(1);
		List<IomCiDir> iomCiDirs = iomCiDirMapper.selectByExample(iomCiDirExample);
		List<String> upIds = new ArrayList<String>(); 
		List<String> needDeleteList = new ArrayList<String>();
		needDeleteList.add(dirId);
		upIds.add(dirId);
		getNeedDeleteList(iomCiDirs, upIds, needDeleteList);
		
		//修改当前以及孩子
		IomCiDirExample iomCiDirExample2 = new IomCiDirExample();
		com.integration.generator.entity.IomCiDirExample.Criteria criteria2 = iomCiDirExample2.createCriteria();
		criteria2.andIdIn(needDeleteList);
		IomCiDir iomCiDir = new IomCiDir();
		iomCiDir.setYxbz(0);
		iomCiDirMapper.updateByExampleSelective(iomCiDir, iomCiDirExample2);
		
		IomCiIconExample iomCiIconExample = new IomCiIconExample();
		iomCiIconExample.createCriteria().andIconDirIn(needDeleteList);
		IomCiIcon iomCiIcon = new IomCiIcon();
		iomCiIcon.setYxbz(0);
		// 删除目录
		iomCiIconMapper.updateByExampleSelective(iomCiIcon, iomCiIconExample);
		return true;

	}
	
	/**
	 * 递归查子集
	 * @param iomCiDirs
	 * @param upIds
	 * @param needDeleteList
	 */
	private void getNeedDeleteList(List<IomCiDir> iomCiDirs,List<String> upIds,List<String> needDeleteList) {
		if (iomCiDirs!=null && iomCiDirs.size()>0 && upIds!=null && upIds.size()>0) {
			List<String> ids = new ArrayList<String>(); 
			for (IomCiDir iomCiDir : iomCiDirs) {
				for (String upId : upIds) {
					if (upId.equals(iomCiDir.getParentDirId())) {
						needDeleteList.add(iomCiDir.getId());
						ids.add(iomCiDir.getId());
						break;
					}
				}
			}
			
			if (ids.size()>0) {
				getNeedDeleteList(iomCiDirs, ids, needDeleteList);
			}
			
		}
		
	}

	/**
	 * 修改目录
	 * 
	 * @return
	 */
	@Override
	public boolean updateDir(Dir dir) {
		dir.setXgsj(DateUtils.getDate());
		int result = dirDao.updateDir(dir);
		if (result > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据ID查询目录信息
	 */
	@Override
	public Dir findDirById(String dirId) {
		return dirDao.findDirById(dirId);
	}

	@Override
	public boolean dirNameExists(String dir_name,String dir_type,String domainId) {
		return dirDao.dirNameExists(dir_name,dir_type,domainId) > 0;
	}
	
	@Override
	public List<Map<String, Object>> getDirListByParentId(String diagDir, String user_id){
		//获取数据域
		String domainId = TokenUtils.getTokenDataDomainId();
		return dirDao.getDirListByParentId(diagDir, user_id,domainId);
	}

}
