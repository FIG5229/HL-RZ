package com.integration.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.StringField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.integration.dao.TypeDao;
import com.integration.entity.PageResult;
import com.integration.entity.Tree;
import com.integration.entity.Type;
import com.integration.generator.dao.IomCiTypeRelMapper;
import com.integration.generator.entity.IomCiType;
import com.integration.generator.entity.IomCiTypeFocusRel;
import com.integration.generator.entity.IomCiTypeRel;
import com.integration.generator.entity.IomCiTypeRelDiagram;
import com.integration.generator.entity.IomCiTypeRelExample;
import com.integration.service.CiTypeRelService;
import com.integration.service.DirService;
import com.integration.service.RelService;
import com.integration.utils.DataUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;

/**
* @Package: com.integration.controller
* @ClassName: CiTypeRelController
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 大类关系
*/
@RestController
@RequestMapping("ciType")
public class CiTypeRelController {
	@Autowired
	public RelService rs;
	@Autowired
	private CiTypeRelService ciTypeRelService;
	@Resource
    private IomCiTypeRelMapper iomCiTypeRelMapper;
	
	@Autowired
    private DirService dirService;
	
	@Autowired
	private TypeDao typeDao;

	/**
	 * 保存类别关系
	 *
	 * @param relArr
	 * @return
	 */
	@PutMapping("/type/rel")
	public Object typeRelSave(String relArr) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenOrgDomainId();
		String userId = TokenUtils.getTokenUserId();
		List list = null;
		if (StringUtils.isNotEmpty(relArr)) {
			list = JSON.parseArray(relArr);
			Date now = new Date();
			Map<String, Object> one = null;
			for (Object object : list) {
				one = (Map<String, Object>) object;
				String sourceTypeName=(String) one.get("sourceTypeName");
				Type typeSource=typeDao.findByMc(sourceTypeName,domainId);
				String sourceTypeId=typeSource.getId();
				one.put("sourceTypeId", sourceTypeId);
				String targetTypeName=(String) one.get("targetTypeName");
				Type typeTarget=typeDao.findByMc(targetTypeName,domainId);
				String targetTypeId=typeTarget.getId();
				one.put("targetTypeId", targetTypeId);
				one.put("id", SeqUtil.nextId());
				one.put("cjrId", userId);
				one.put("xgrId", userId);
				one.put("djsj", now);
				one.put("xgsj", now);
				one.put("yxbz", 1);
				one.put("domainId", domainId==null?"-1":domainId);
			}
			return ciTypeRelService.save(list);
		}
		return DataUtils.returnPr(false, "保存类别关系失败!");
	}
	/**
	 * @Author: ztl
	 * date: 2021-08-06
	 * @description: 删除类别关系
	 */
	@DeleteMapping("/type/rel")
	public Object deleteTypeRelSave(String ids) {
		IomCiTypeRelExample iomCiTypeRelExample = new IomCiTypeRelExample();
		com.integration.generator.entity.IomCiTypeRelExample.Criteria criteria = iomCiTypeRelExample.createCriteria();
		criteria.andYxbzEqualTo(1);
		criteria.andIdIn(Arrays.asList(ids.split(",")));
		IomCiTypeRel iomCiTypeRel = new IomCiTypeRel();
		iomCiTypeRel.setXgrId(Long.parseLong(TokenUtils.getTokenUserId()));
		iomCiTypeRel.setXgsj(new Date());
		iomCiTypeRel.setYxbz(0);
		return iomCiTypeRelMapper.updateByExample(iomCiTypeRel, iomCiTypeRelExample);
	}

	/**
	 * 查询大类关系
	 *
	 * @return
	 */
	@GetMapping("/type/rel")
	public Object selectCiTypeRels() {
		return ciTypeRelService.selectCiTypeRels();
	}

	/**
	 * 查询大类
	 *
	 * @return
	 */
	@GetMapping("/type")
	public List<Tree> selectCiTypes(String id,String ciTypeMc) {
		 List<Tree> list=dirService.findTreeListByViewModel("");
         List<Tree> returnList=new ArrayList<Tree>();
         if(ciTypeMc!=null && !"".equals(ciTypeMc)) {
        	 List<Tree> list3=new ArrayList<Tree>();
        	 for(Tree tree:list) {
        		 List<Tree> list1=tree.getChildren();
        		 List<Tree> list2=new ArrayList<Tree>();
        		 for(Tree tree1:list1) {
        			 String name=tree1.getName();
        			 boolean flag=name.contains(ciTypeMc);
        			 if(flag) {
        				 tree1.setCiCode(name);
        				 list2.add(tree1); 
        			 }
        		 }
        		 if(list2!=null && list2.size()>0) {
        			 tree.setChildren(list2);
        			 tree.setCount(list2.size());
        			 list3.add(tree);
        		 }
        		 
        	 }
        	 returnList=list3;
        	 return returnList; 
         }
         
         if(id!=null && !"".equals(id)) {
        	 List<Tree> list1=new ArrayList<Tree>();
        	 for(Tree tree:list) {     		
        		 String idStr=tree.getId();
        		 List<Tree> list3=tree.getChildren();
        		 if(id.equals(idStr)) {
        			 //循环list3增加ciCode字段给前端提供展示用
        			 if(list3!=null && list3.size()>0) {
        				 for(Tree treeData:list3) {
        					 String name=treeData.getName();
        					 treeData.setCiCode(name);
        				 }
        			 }
        			 tree.setCount(list3.size());
        			 list1.add(tree);
        		 }else {
        			 tree.setChildren(new ArrayList<Tree>());
        			 tree.setCount(list3.size());
        			 list1.add(tree);
        		 }
        	 }
        	 returnList=list1;
         }else {
        	for(Tree tree:list) {
        		List<Tree> list3=tree.getChildren();
        		tree.setChildren(new ArrayList<Tree>());
        		tree.setCount(list3.size());
        	}
        	returnList=list;
         }
         return returnList;
	}
	
	/**
	 * @Author: ztl
	 * date: 2021-08-06
	 * @description: 把JavaBean转化为map
	 */
  	public static Map<String,Object> bean2map(Object bean) throws Exception{
  	    Map<String,Object> map = new HashMap<>();
  	    //获取JavaBean的描述器
  	    BeanInfo b = Introspector.getBeanInfo(bean.getClass(),Object.class);
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
	 * 查询关系
	 *
	 * @return
	 */
	@GetMapping("/rel")
	public Object selectCiRels(String lineName) {
		return ciTypeRelService.selectCiRels(lineName);
	}

	/**
	 * 保存影响
	 *
	 * @return
	 */
	@PutMapping(value = "/type/focus")
	public boolean saveInfluence(String typeId, String ciTypeRelId, Boolean type,String ciTypeName) {
		//获取数据域ID
		String orgDomainId = TokenUtils.getTokenOrgDomainId();
		Type typeData=typeDao.findByMc(ciTypeName,orgDomainId);
		if(typeData!=null) {
			typeId=typeData.getId();
	        //获取数据域ID
		    String domainId = TokenUtils.getTokenDataDomainId();

			String userId = TokenUtils.getTokenUserId();
			//关系主键ID
			IomCiTypeRel iomCiTypeRel = iomCiTypeRelMapper.selectByPrimaryKey(ciTypeRelId,domainId);
			if (iomCiTypeRel!=null) {
				IomCiTypeFocusRel iomCiTypeFocusRel = new IomCiTypeFocusRel();
				iomCiTypeFocusRel.setSourceTypeId(iomCiTypeRel.getSourceTypeId());
				iomCiTypeFocusRel.setTargetTypeId(iomCiTypeRel.getTargetTypeId());
				iomCiTypeFocusRel.setRltId(iomCiTypeRel.getRelId());
				iomCiTypeFocusRel.setDomainId(orgDomainId==null?"-1":orgDomainId);
				if (type) {
					iomCiTypeFocusRel.setId(SeqUtil.nextId() + "");
					iomCiTypeFocusRel.setTypeId(typeId);
					iomCiTypeFocusRel.setCjrId(userId);
					iomCiTypeFocusRel.setXgrId(userId);
					iomCiTypeFocusRel.setCjsj(new Date());
					iomCiTypeFocusRel.setXgsj(new Date());
					iomCiTypeFocusRel.setYxbz(1);
					ciTypeRelService.insert(iomCiTypeFocusRel);
				}else {
					iomCiTypeFocusRel.setTypeId(typeId);
					ciTypeRelService.getIomCiTypeRelBySourceIdAndTargetId(iomCiTypeFocusRel);
				}
			}
			PageResult.success("保存影响关系成功!");
			return true;
		}
		PageResult.success("保存影响关系失败!");
		return false;
	}

	/**
	 * 查询影响
	 *
	 * @return
	 */
	@GetMapping(value = "/type/focus")
	public Map<String, Object> getCiTypeRelByTypeId(String typeId,String ciTypeName){
		//获取数据域ID
		String orgDomainId = TokenUtils.getTokenOrgDomainId();
		Type type=typeDao.findByMc(ciTypeName,orgDomainId);
		if(type!=null) {
			typeId=type.getId();
			Map<String, Object> returnMap=new HashMap<String, Object>();
			List<Map<String, Object>> list=ciTypeRelService.getCiTypeRelByTypeId(typeId);
			returnMap.put("infoList", list);
			List<Tree> treeList=dirService.findTreeListByViewModel("");
			Tree tree2=new Tree();
			for(Tree tree:treeList) {
				List<Tree> list1=tree.getChildren();
				for(Tree tree1:list1) {
					String ciTypeIdStr=tree1.getId();
					if(typeId.equals(ciTypeIdStr)) {
						returnMap.put("icon", tree1.getIcon());
						returnMap.put("name", tree1.getName());
						tree2=tree;
						break;
					}
				}
			}
			returnMap.put("parentName", tree2.getName());
			returnMap.put("parentId", tree2.getId());
			returnMap.put("count", 0);
			return returnMap;
		}
		return null;
	}


	/**
	 * 保存视图
	 *
	 * @return
	 */
	@PutMapping(value = "/diagram")
    public Object saveIomCiTypeRelDiagram(IomCiTypeRelDiagram iomCiTypeRelDiagram){
		Integer i=ciTypeRelService.saveIomCiTypeRelDiagram(iomCiTypeRelDiagram);
		if(i>0) {
			return DataUtils.returnPr(true, "保存成功!");
		}else {
			return DataUtils.returnPr(false, "保存失败!");
		}
	}

	/**
	 * 查询视图
	 *
	 * @return
	 */
	@GetMapping(value = "/diagram")
	public Object getIomCiTypeRelDiagram() {
		return ciTypeRelService.getIomCiTypeRelDiagram();
	}

	/**
	 * 校验两个对象的编码是否符合可视化建模的规范
	 * @param sourceBm
	 * @param relId
	 * @param targetBm
	 * @return
	 */
	@GetMapping(value = "/checkCiRel")
	public Object checkCiRel(String sourceBm, String relId, String targetBm) {
		return ciTypeRelService.checkCiRel(sourceBm, relId, targetBm);
	}
	/**
	 * @Author: ztl
	 * date: 2021-08-06
	 * @description: 获取关系对应的大类ID
	 */
	@GetMapping(value = "/selectRelTypeIds")
	public Object selectRelTypeIds(String ciId, String typeId,String ciCode) {
		return ciTypeRelService.selectRelTypeIds(ciId, typeId,ciCode);
	}
	/**
	 * @Author: ztl
	 * date: 2021-08-06
	 * @description: 查询大类关系对应的CI数据
	 */
	@GetMapping(value = "/selectRelCi")
	public Object selectRelCi(String ciId, String typeId1, String typeId2, String relId,String ciCode) {
		return ciTypeRelService.selectRelCi(ciId, typeId1, typeId2, relId,ciCode);
	}
}
