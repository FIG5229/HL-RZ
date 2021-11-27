package com.integration.service.impl;

import com.integration.dao.DictDao;
import com.integration.entity.Dict;
import com.integration.entity.Dicts;
import com.integration.rabbit.Sender;
import com.integration.service.DictService;
import com.integration.utils.DateUtils;
import com.integration.utils.RedisUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.service.impl
* @ClassName: DictServiceImpl
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 字典管理
*/
@Service
public class DictServiceImpl implements DictService {

    @Resource
    private DictDao dictMapper;

    @Autowired
    private Sender sender;

    /**
     * 新增字典
     */
    @Override
    public Dict addDict(Dict dict) {
        dict.setDict_id(SeqUtil.nextId() + "");
        // 给新增字典添加创建时间,默认为系统当前时间
        dict.setCjsj(DateUtils.getDate());
        // 给新增字典添加有效标识, 默认为1 有效
        dict.setYxbz("1");
        // 添加成功后返回当前添加的字典ID
        int add = dictMapper.addDict(dict);
        if (add > 0) {
        	redisDict();
            return dict;
        } else {
            return null;
        }
    }

    /**
     * 新增字典 驼峰
     */
    @Override
    public Dicts addDictHump(Dicts dict) {
        dict.setDictId(SeqUtil.nextId() + "");
        dict.setGnfl(0);
        dict.setSort(0);
        dict.setYxbz("1");
        dict.setCjsj(DateUtils.getDate());
        dict.setCjrId(TokenUtils.getTokenUserId());
        dict.setXgsj(DateUtils.getDate());
        dict.setXgrId(TokenUtils.getTokenUserId());
        int add = dictMapper.addDictHump(dict);
        if (dict.getSjId()!=null && "258888".equals(dict.getSjId())){
            sender.sendDatasourceChgNotice();
        }
        if (add > 0) {
            redisDict();
            return dict;
        } else {
            return null;
        }
    }

    @Override
    public boolean addDictHump(List<Map> dicts) {
        for (int i = 0; i < dicts.size(); i++) {
            dicts.get(i).put("dictId", SeqUtil.nextId() + "");
            dicts.get(i).put("gnfl", 0);
            dicts.get(i).put("sort", 0);
            dicts.get(i).put("cjrId", TokenUtils.getTokenUserId());
            dicts.get(i).put("cjsj", DateUtils.getDate());
            dicts.get(i).put("xgrId", TokenUtils.getTokenUserId());
            dicts.get(i).put("xgsj", DateUtils.getDate());
            dicts.get(i).put("yxbz", "1");
            dicts.get(i).put("coutent", "");
        }
        int add = dictMapper.addDictHumps(dicts);
        if (add > 0) {
            redisDict();
            return true;
        } else {
            return false;
        }

    }

    /**
     * 删除字典
     */
    @Override
    public int deleteDict(String dict_id) {
        Dict dict = findDictById(dict_id);
        if ("0".equals(dict.getSj_id())) {
            //一级
            dictMapper.deleteDictBySjId(dict.getDict_bm());
        }

        int result = dictMapper.deleteDict(dict_id);
        redisDict();

        return result;
    }

    /**
     * 修改字典
     * flag 为true，不存在；
     * flag 为false，存在
     *
     * 返回的map里，true，修改成功；false，修改失败
     */
    @Override
    public Map<String,Object> updateDict(Dict dict) {
        //根据此id查询记录
        dict.setXgsj(DateUtils.getDate());
        Dict dicta = dictMapper.findDictById(dict.getDict_id());
        int bool = 0;
        Map<String,Object> map = new HashMap<>();
        //在此记录里名字和编码都匹配，没有修改
        if (dicta.getDict_name().equals(dict.getDict_name()) && dicta.getDict_bm().equals(dict.getDict_bm())) {
            bool = dictMapper.updateDict(dict);
            if (bool > 0) {
                map.put("flag",true);
                map.put("message","修改成功!");
            } else {
                map.put("flag",false);
                map.put("message","修改失败!");
            }
        } else if (dicta.getDict_name().equals(dict.getDict_name())) {
            //名字没变，修改了编码（编码需要验重）
            //全部的编码
            List<String> list = dictMapper.findDictBm();
            boolean flag = true;
            for (String s : list) {
                //编码有重复
                if (s.equals(dict.getDict_bm())) {
                    flag = false;
                    break;
                }
            }
            //编码有重复
            if (!flag) {
                map.put("flag",false);
                map.put("message","编码重复!");
            } else {
                //编码没有重复
                bool = dictMapper.updateDict(dict);
                if (bool > 0) {
                    map.put("flag",true);
                    map.put("message","修改成功!");
                } else {
                    map.put("flag",false);
                    map.put("message","修改失败!");
                }
            }

        } else if (dicta.getDict_bm().equals(dict.getDict_bm())) {
            //编码没变，修改了名字
            //全部的名字
            List<String> list = dictMapper.findDictName();
            boolean flag = true;
            for (String s : list) {
                //名字有重复
                if (s.equals(dict.getDict_name())) {
                    flag = false;
                    break;
                }
            }
            //名字有重复
            if (!flag) {
                map.put("flag",false);
                map.put("message","名字重复!");
            } else {
                //名字没有重复
                bool = dictMapper.updateDict(dict);
                if (bool > 0) {
                    map.put("flag",true);
                    map.put("message","修改成功!");
                } else {
                    map.put("flag",false);
                    map.put("message","修改失败!");
                }
            }
        } else {
            //两者都变了(1.编码重复，名字重复2.编码不重复，名字不重复)
            //全部的名字
            List<String> list = dictMapper.findDictName();
            //全部的编码
            List<String> list1 = dictMapper.findDictBm();
            boolean flag = true;
            for (String s : list) {
                //名字
                if (s.equals(dict.getDict_name())) {
                    //名字有重复
                    flag = false;
                    break;
                }
            }
            boolean flag1 = true;
            for (String s1 : list1) {
                //名字
                if (s1.equals(dict.getDict_bm())) {
                    //名字有重复
                    flag1 = false;
                    break;
                }
            }
            if (!flag && flag1) {
                //名字重复，编码不重复
                map.put("flag",false);
                map.put("message","名字重复!");
            } else if (flag && !flag1) {
                //名字不重复，编码重复
                map.put("flag",false);
                map.put("message","编码重复!");
            } else if (!flag && !flag1) {
                //名字重复，编码重复
                map.put("flag",false);
                map.put("message","名字和编码都重复!");
            } else {
                //都不重复
                bool = dictMapper.updateDict(dict);
                if (bool > 0) {
                    map.put("flag",true);
                    map.put("message","修改成功!");
                } else {
                    map.put("flag",false);
                    map.put("message","修改失败!");
                }
            }
        }
        redisDict();
        if (dicta.getSj_id()!=null && "258888".equals(dicta.getSj_id())){
            sender.sendDatasourceChgNotice();
        }
        return map;
    }

    /**
     * 根据字典名称查询字典列表
     */
    @Override
    public List<Dict> findDict(String dict_name) {
        return dictMapper.findDict(dict_name);
    }

    /**
     * 查询字典编码是否已经存在
     */
    @Override
    public int existsDict_bm(String dict_bm, String sj_id, String dict_name) {
        int count1 = dictMapper.existsDict_bm(dict_bm, sj_id);
        int count2 = dictMapper.existsDict_name(sj_id, dict_name);
        if (count1 > 0 && count2 > 0) {
            //两者都存在
            return 1;
        } else if (count1 > 0) {
            //编码存在
            return 2;
        } else if (count2 > 0) {
            //名字存在
            return 3;
        } else {
            //二者都不存在（走接口）
            return 0;
        }
    }

    /**
     * 根据字典ID获取字典信息
     */
    @Override
    public Dict findDictById(String dict_id) {
        return dictMapper.findDictById(dict_id);
    }

    @Override
    public List<Map<String, Object>> findDiceBySj_id(String sj_id) {
        return dictMapper.findDiceBySj_id(sj_id);
    }

    /**
     * 根据上级ID查询字典
     *
     * @return
     */
    @Override
    public List<Dict> findParentDict(String sj_id,String dict_name) {
        return dictMapper.findParentDict(sj_id,dict_name);

    }
    /**
     * 根据上级ID查询字典
     *
     * @return
     */
    @Override
    public List<Dict> findParentDictByBm(String sj_id,String dict_name) {
        return dictMapper.findParentDictByBm(sj_id,dict_name);

    }

    /**
     * 查询所有存在的名字
     *
     * @return
     */
    @Override
    public List<String> findDictName() {
        return dictMapper.findDictName();
    }

    /**
     * 查询所有存在的编码
     *
     * @return
     */
    @Override
    public List<String> findDictBm() {
        return dictMapper.findDictBm();
    }


    /**
     * 批量删除字典
     */
    @Override
    public int deleteDictList(String[] dictIdList) {
    	int result = dictMapper.deleteDictList(dictIdList);
    	sender.sendDatasourceChgNotice();
    	redisDict();
        return result;
    }

	@Override
	public void redisDict() {

        List<Dict> list = dictMapper.findParentDict("0","");
        RedisUtils.set("dictInfo0",list);
        for (int i = 0; i < list.size(); i++) {
            RedisUtils.set("dictInfo"+list.get(i).getDict_bm(),dictMapper.findParentDict(list.get(i).getDict_bm(),""));
		}

	}

    /**
     * 处理事件来源数据
     * @param data
     */
    @Override
    public void handleSourceData(List<Map> data) {
        List<Dict> addList = new ArrayList<>();

        for (Map map:data) {
            Dict dict = new Dict();
            dict.setDict_id(String.valueOf(map.get("dictId")));
            dict.setDict_name(String.valueOf(map.get("dictName")));
            dict.setDict_bm(String.valueOf(map.get("dictBm")));
            dict.setSort(Integer.valueOf(String.valueOf(map.get("sort"))));
            dict.setCoutent(String.valueOf(map.get("coutent")));
            dict.setYxbz("1");
            dict.setSj_id(String.valueOf(map.get("sjId")));
            dict.setGnfl(0);
            dict.setCjr_id(String.valueOf(map.get("cjrId")));
            addList.add(dict);
        }
        dictMapper.deleteDictBySjId("258888");
        if (addList!=null && addList.size()>0){
            dictMapper.addDictList(addList);
        }

    }


}
