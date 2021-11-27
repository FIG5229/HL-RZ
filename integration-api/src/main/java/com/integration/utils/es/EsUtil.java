package com.integration.utils.es;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.common.regex.Regex;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.integration.utils.DateUtils;
import com.integration.utils.RedisUtils;

@Component
@ConditionalOnClass(RestHighLevelClient.class)
public class EsUtil {
	private static final Logger logger = LoggerFactory.getLogger(EsUtil.class);

	public static final String TYPE = "doc";

	public static final String version = "version";

	public static final String updateDir = "types/update";

	//private static boolean initFlag = false;// 是否初始化
	
	private static RestHighLevelClient client = EsTcpClient.getClient();
	
	private static String[] parsePatterns = {"yyyy-MM-dd","yyyy年MM月dd日",
	        "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
	        "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"};

	/**
	 * 初始化es的各个表， 如果执行不成功会拦截请求，如果执行成功过，则不会再执行 如果已经初始化过了，则认为执行成功
	 * 该方法最有风险的的地方在于，elasticsearch不允许修改字段，但是新数据只要有新字段，就会自动生成一个新字段，这个新字段不是我们想要的表字段，
	 * 又占了位置，会导致表结构错误，解决办法是保证，在插入数据之前，一定要保证表结构已经修改完成了，或者可以考虑禁止随意在数据里加新字段
	 */
	public static void initIndex() {
		//监测是否有相同ES正在升级
		String initKey = "initES-" + EsTcpClient.getHost()+"-"+EsTcpClient.getPort();
		/*while (RedisUtils.exists(initKey)) {
			logger.info("检测到有其他项目正在初始化ES："+initKey+"，等30秒");
			try {
				Thread.sleep(1000*30L);
			} catch (InterruptedException e) {
				logger.error("Thread.sleep执行失败！");
			}
		}*/
		RedisUtils.set(initKey, "start",1000*60*60*3L);
		
		
		// File f = new File(path.toString());

		// 这个判读可以在重启后进行检测，但是如果easearch在中途损坏，然后又重新启动了新的时导致不会再次进行初始化设置，此时应该先关闭本服务
		// 这里是执行效率和系统稳健性的取舍，加大风险提高执行效率
		//if (!initFlag) {
			// 初始化
			// 如果连不上，判定方法就会报错
			indexCreate(version);

			// 升级
			try {
				updateType();
			} catch (IOException e) {
				logger.error("结构更新失败");
				e.printStackTrace();
			}

			// 上面报错会导致这里走不到，从而实现下次访问再次判断是否初始化
			//initFlag = true;
		//}
		//结束时,删除占位
		RedisUtils.remove(initKey);

	}

	public static boolean indexCreate(String indexName) {
		if (!existIndex(indexName)) {
			try {
				// 如果不存type，则初始化type
				InputStream in = EsUtil.class.getClassLoader()
						.getResourceAsStream("types/install/" + indexName + ".json");
				String result = IOUtils.toString(in, "UTF-8");
				result = JSONObject.toJSONString(JSONObject.parseObject(result));
				return indexCreate(indexName, result);
			} catch (IOException e) {
				logger.info("找不到" + indexName + "初始化文件");
				return false;
			}
		}
		return true;
	}

	public static boolean indexCreate(String indexName, String mapping) {
		if (!existIndex(indexName)) {
			try {
				CreateIndexRequest request = new CreateIndexRequest(indexName);
				request.settings(
						Settings.builder().put("index.number_of_shards", 3).put("index.number_of_replicas", 2).put("index.max_result_window",2000000000));
				request.mapping(mapping, XContentType.JSON);
				CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
				return true;
			} catch (IOException e) {
				logger.info("找不到" + indexName + "初始化文件");
				return false;
			}
		}
		return true;
	}

	public static boolean indexUpload(String indexName, String mapping) {
		if (!existIndex(indexName)) {
			try {
				PutMappingRequest request = new PutMappingRequest(indexName);
				request.source(mapping, XContentType.JSON);
				AcknowledgedResponse putMappingResponse = client.indices().putMapping(request, RequestOptions.DEFAULT);
				return putMappingResponse.isAcknowledged();
			} catch (IOException e) {
				logger.info("找不到" + indexName + "初始化文件");
				return false;
			}
		}
		return true;
	}

	public static boolean existIndex(String index) {
//		OpenIndexRequest openIndexRequest = new OpenIndexRequest(index);
//		try {
//			client.indices().open(openIndexRequest);
//			return true;
//		}catch (ElasticsearchException e) {
//			return false;
//		}catch (Exception e) {
//			return false;
//		}
		
		
		GetIndexRequest request = new GetIndexRequest();
		 request.indices(index);
		 try {
			 return client.indices().exists(request, RequestOptions.DEFAULT);
		 } catch (IOException e) {
			 e.printStackTrace();
			 return false;
		 }
	}

	public static boolean deleteIndex(String indexName) {
		DeleteIndexRequest index = new DeleteIndexRequest(indexName);
		try {
			client.indices().delete(index, RequestOptions.DEFAULT);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}
    
	public static void timedTaskCreateIomPmvPerformance(String date) throws IOException {
		List<Integer> fileNames = getFileNames();
		if (fileNames.size() > 0) {
			Integer old = null;
			// 获取需要升级的数据
			List<Integer> nameList = new ArrayList<Integer>();
			for (Integer key : fileNames) {
				if (old == null || key > old) {
					nameList.add(key);
				}
			}
			
			if (nameList.size() > 0) {
				// 按照从小到大排序
				Object[] nameArr = nameList.toArray();
				Arrays.sort(nameArr);// 从小到大排序

				// 依次执行版本升级文件
				for (Object name : nameArr) {
					Integer num=Integer.parseInt(String.valueOf(name));
					if(20200305==num) {
						InputStream in = EsUtil.class.getClassLoader()
								.getResourceAsStream(updateDir + "/" + name + ".json");
						String result = IOUtils.toString(in, "UTF-8");
						JSONArray arr = JSONArray.parseArray(result);
						for (Object object : arr) {
							JSONObject jsobj = (JSONObject) object;
							String type=jsobj.getString("type");
							type=type+"-"+date;
							indexCreate(type, JSONObject.toJSONString(jsobj.get("source")));
						}
					}else {
						continue;
					}
					//Map<String, Object> map = new HashMap<String, Object>();
					//map.put("name", name.toString());// 文件名
					//map.put("createTime", DateUtils.parseDate(new Date()));// 默认插入创建时间
					//insert(map, version);// 插入信息需要一段时间才能搜索出来，所以立马查查不到最新的，可以再刷新一下
				}
			}
			
		}
	}
	
	private static void updateType() throws IOException {
		// 升级版本
		List<Integer> fileNames = getFileNames();
		if (fileNames.size() > 0) {
			// 获取老版本号
			Map<String, Object> criterias = new HashMap<String, Object>();
			criterias.put(pageSize, "1");
			criterias.put(pageNum, "1");
			criterias.put(pageSort, "name desc");
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> versions = (List<Map<String, Object>>) searchPage(version, criterias)
					.get(pageList);
			Integer old = null;
			if (versions != null && versions.size() > 0) {
				old = Integer.parseInt(versions.get(0).get("name").toString());
			}

			// 获取需要升级的数据
			List<Integer> nameList = new ArrayList<Integer>();

			for (Integer key : fileNames) {
				if (old == null || key > old) {
					nameList.add(key);
				}
			}

			if (nameList.size() > 0) {
				// 按照从小到大排序
				Object[] nameArr = nameList.toArray();
				Arrays.sort(nameArr);// 从小到大排序

				// 依次执行版本升级文件
				for (Object name : nameArr) {
					Integer num=Integer.parseInt(String.valueOf(name));
					List<String> dateList=new ArrayList<String>();
					if(20200305==num) {
						Date date=new Date();
						Calendar calendar = Calendar.getInstance();  
			            calendar.setTime(date);  
			            calendar.add(Calendar.DAY_OF_MONTH, +0);  
			            date = calendar.getTime();
			            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			            String timeToday=sdf.format(date);
			            dateList.add(timeToday);
			            
			            Calendar calendar1 = Calendar.getInstance();  
			            calendar1.setTime(date);  
			            calendar1.add(Calendar.DAY_OF_MONTH, +1);  
			            date = calendar1.getTime();
			            String timeTomorrow=sdf.format(date);
			            dateList.add(timeTomorrow);
					}
					if(dateList!=null && dateList.size()>0) {
						for(String dateStr:dateList) {
							InputStream in = EsUtil.class.getClassLoader()
									.getResourceAsStream(updateDir + "/" + name + ".json");
							String result = IOUtils.toString(in, "UTF-8");
							JSONArray arr = JSONArray.parseArray(result);
							for (Object object : arr) {
								JSONObject jsobj = (JSONObject) object;
								indexCreate(jsobj.getString("type")+"-"+dateStr, JSONObject.toJSONString(jsobj.get("source")));
							}
						}
						
					}else {
						InputStream in = EsUtil.class.getClassLoader()
								.getResourceAsStream(updateDir + "/" + name + ".json");
						String result = IOUtils.toString(in, "UTF-8");
						JSONArray arr = JSONArray.parseArray(result);
						for (Object object : arr) {
							JSONObject jsobj = (JSONObject) object;
							indexCreate(jsobj.getString("type"), JSONObject.toJSONString(jsobj.get("source")));
						}
					}
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name", name.toString());// 文件名
					map.put("createTime", DateUtils.parseDate(new Date()));// 默认插入创建时间
					insert(map, version);// 插入信息需要一段时间才能搜索出来，所以立马查查不到最新的，可以再刷新一下
				}
			}else {
				for (Integer key : fileNames) {
						nameList.add(key);
				}
				if (nameList.size() > 0) {
					// 按照从小到大排序
					Object[] nameArr = nameList.toArray();
					Arrays.sort(nameArr);// 从小到大排序

					// 依次执行版本升级文件
					for (Object name : nameArr) {
						Integer num=Integer.parseInt(String.valueOf(name));
						List<String> dateList=new ArrayList<String>();
						if(20200305==num) {
							Date date=new Date();
							Calendar calendar = Calendar.getInstance();  
				            calendar.setTime(date);  
				            calendar.add(Calendar.DAY_OF_MONTH, +0);  
				            date = calendar.getTime();
				            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				            String timeToday=sdf.format(date);
				            dateList.add(timeToday);
				            
				            Calendar calendar1 = Calendar.getInstance();  
				            calendar1.setTime(date);  
				            calendar1.add(Calendar.DAY_OF_MONTH, +1);  
				            date = calendar1.getTime();
				            String timeTomorrow=sdf.format(date);
				            dateList.add(timeTomorrow);
						}
						if(dateList!=null && dateList.size()>0) {
							for(String dateStr:dateList) {
								InputStream in = EsUtil.class.getClassLoader()
										.getResourceAsStream(updateDir + "/" + name + ".json");
								String result = IOUtils.toString(in, "UTF-8");
								JSONArray arr = JSONArray.parseArray(result);
								for (Object object : arr) {
									JSONObject jsobj = (JSONObject) object;
									indexCreate(jsobj.getString("type")+"-"+dateStr, JSONObject.toJSONString(jsobj.get("source")));
								}
							}
							
						}else {
							InputStream in = EsUtil.class.getClassLoader()
									.getResourceAsStream(updateDir + "/" + name + ".json");
							String result = IOUtils.toString(in, "UTF-8");
							JSONArray arr = JSONArray.parseArray(result);
							for (Object object : arr) {
								JSONObject jsobj = (JSONObject) object;
								indexCreate(jsobj.getString("type"), JSONObject.toJSONString(jsobj.get("source")));
							}
						}
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("name", name.toString());// 文件名
						map.put("createTime", DateUtils.parseDate(new Date()));// 默认插入创建时间
						insert(map, version);// 插入信息需要一段时间才能搜索出来，所以立马查查不到最新的，可以再刷新一下
					}
				}
				
			}
		}
	}

	private static List<Integer> getFileNames() throws IOException {
		List<Integer> nameList = new ArrayList<Integer>();

		URL dirPath = EsUtil.class.getClassLoader().getResource(updateDir);
		logger.info("===================dirPath:" + dirPath.getPath());
		if (dirPath.getPath().indexOf("!/") > -1) {// jar包形态
			JarURLConnection jarURLConnection = (JarURLConnection) dirPath.openConnection();
			JarFile jarFile = jarURLConnection.getJarFile();
			Enumeration<JarEntry> jarEntrys = jarFile.entries();
			while (jarEntrys.hasMoreElements()) {
				JarEntry entry = jarEntrys.nextElement();
				String name = entry.getName();
				if (name.startsWith(updateDir) && !entry.isDirectory()) {
					if (name.lastIndexOf("/") > -1) {
						name = name.substring(name.lastIndexOf("/") + 1);
					}
					if (name.lastIndexOf("\\") > -1) {
						name = name.substring(name.lastIndexOf("\\") + 1);
					}
					name = name.substring(0, name.indexOf("."));

					nameList.add(Integer.parseInt(name.toString()));
				}
			}
		} else {// 普通形态
			File dir = new File(dirPath.getPath());
			if (dir.exists() && dir.isDirectory()) {
				File[] files = dir.listFiles();
				for (File file : files) {
					Integer name = Integer.parseInt(file.getName().split("\\.")[0]);
					nameList.add(name);
				}
			}
		}
		logger.info("===================nameList:" + nameList);
		return nameList;
	}

	/**
	 * 新增
	 * 
	 * @param doc
	 * @return
	 */
	public static String insert(Map<String, Object> doc, String index) {
		String esId = (String) doc.get("id");// 将id作为恒定参数，如果没有则认为找不到对应的修改对象
		String json = toJSONString(doc);

		IndexRequest request = new IndexRequest(index);
		if (esId != null && !"".equals(esId)) {
			request.id(esId);
		}
		request.type("_doc");
		request.source(json, XContentType.JSON);

		try {
			IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
			return indexResponse.getId();
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * 批量提交数据
	 * @param doc
	 * @param index
	 */
	public static void insertList(List doc, String index) {

		BulkRequest request = new BulkRequest();
		for (Object object : doc) {
			request.add(new IndexRequest(index,"_doc").source(JSONObject.toJSONString(object), XContentType.JSON).id(String.valueOf(((Map)object).get("id"))));
		}
         /*
         设置刷新策略
        */
		//request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
		request.setRefreshPolicy(WriteRequest.RefreshPolicy.NONE);
		try {
			client.bulk(request);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 替换
	 * 
	 * @param doc
	 * @param type
	 * @return
	 */
	public static boolean replace(Map<String, Object> doc, String type) {
		return update(doc, type);
	}

	/**
	 * 更新
	 * 
	 * @param doc
	 * @return
	 */
	public static boolean update(Map<String, Object> doc, String index) {
		String esId = (String) doc.get("id");// 将id作为恒定参数，如果没有则认为找不到对应的修改对象
		if (esId != null) {
			UpdateRequest request = new UpdateRequest(index, TYPE, esId);
			String json = toJSONString(doc);
			request.doc(json, XContentType.JSON);
			try {
				UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
				return true;
			} catch (IOException e) {
			}
		}
		return false;
	}

	/**
	 * 删除
	 *
	 * @param esId
	 * @return
	 */
	public static boolean delete(String index, String esId) {
		DeleteRequest request = new DeleteRequest(index, TYPE, esId);
		DeleteResponse deleteResponse;
		try {
			deleteResponse = client.delete(request, RequestOptions.DEFAULT);
			return true;
		} catch (IOException e) {
		}
		return false;
	}

	/**
	 * 这个方法会把json里面的id去掉，模拟数据库操作
	 * 
	 * @param doc
	 * @return
	 */
	private static String toJSONString(Map<String, Object> doc) {
		if (doc.get("id") != null) {
			doc.remove("id");
		}
		return JSON.toJSONString(doc);
	}

	/**
	 * 这个方法会给json添加个ID，模拟数据库操作
	 * 
	 * @param json
	 * @param id
	 * @return
	 */
	private static JSONObject parseObject(String json, String id) {
		JSONObject object = JSON.parseObject(json);
		object.put("id", id);
		return object;
	}

	/**
	 * 根据ID查询
	 *
	 * @param id
	 * @return
	 */
	public static Map<String, Object> searchById(String index, String id) {
		GetRequest getRequest = new GetRequest(index, TYPE, id);
		GetResponse getResponse;
		try {
			getResponse = client.get(getRequest, RequestOptions.DEFAULT);
			if (getResponse.isExists()) {
				String json = getResponse.getSourceAsString();
				return parseObject(json, getResponse.getId());
			}
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * 条件搜索
	 * 
	 * @param type
	 * @param criterias
	 * @return
	 */
	public static List<Map<String, Object>> search(String type, Map<String, Object> criterias) {
		return executeSearch(type, criterias, true);
	}

	/**
	 * 条件查询命中数
	 *
	 * @param type
	 * @param criterias
	 * @return
	 */
	public static int searchCount(String type, Map<String, Object> criterias) {
		return executeSearchCount(type, criterias, true);
	}

	/**
	 * 执行查询
	 * 
	 * @param type
	 * @param criterias
	 * @param needSource
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<Map<String, Object>> executeSearch(String type, Map<String, Object> criterias,
			boolean needSource) {
		Map<String, Object> resMap = executeSearchPage(type, criterias, needSource);
		if (resMap != null && resMap.get(pageList) != null) {
			return (List<Map<String, Object>>) resMap.get(pageList);
		}
		return null;
	}

	/**
	 * 执行命中数查询
	 *
	 * @param type
	 * @param criterias
	 * @param needSource
	 * @return
	 */
	private static int executeSearchCount(String type, Map<String, Object> criterias,
														   boolean needSource) {
		Map<String, Object> resMap = executeSearchCounts(type, criterias, needSource);
		return Integer.parseInt(String.valueOf(resMap.get(pageTotal)));
	}

	/**
	 * 条件分页查询
	 * 
	 * @param type
	 * @return
	 */
	public static Map<String, Object> searchPage(String type) {
		return executeSearchPage(type, new HashMap<String, Object>(), true);
	}

	/**
	 * 条件分页查询
	 * 
	 * @param type
	 * @param criterias
	 * @return
	 */
	public static Map<String, Object> searchPage(String type, Map<String, Object> criterias) {
		return executeSearchPage(type, criterias, true);
	}

	public final static String pageNum = "pageNum";
	public final static String pageSize = "pageSize";
	public final static String pageSort = "sort";
	public final static String pageTotal = "total";
	public final static String pagePages = "pages";
	public final static String pageList = "list";

	/**
	 * 执行分页查询
	 * 
	 * @param type
	 * @param criterias
	 * @param needSource
	 * @return
	 */
	private static Map<String, Object> executeSearchPage(String type, Map<String, Object> criterias,
			boolean needSource) {
		List<String> dates=Arrays.asList(type.split(","));
		String [] datesArray = dates.toArray(new String[dates.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		// 指定查询的库表
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		int page = criterias.get(pageNum) == null ? 1 : new Integer(criterias.get(pageNum).toString());
		// 在smv指标模型中查询出来的性能数据折线图对象点位不一致，故将分页数据增加到1000，原先是10---2020-04-01修改
		int size = criterias.get(pageSize) == null ? 1000 : new Integer(criterias.get(pageSize).toString());
		criterias.remove(pageNum);
		criterias.remove(pageSize);
		int from = (page - 1) * size;
		searchSourceBuilder.trackTotalHits(true);
		searchSourceBuilder.size(size);
		searchSourceBuilder.from(from);
		if (criterias.get(pageSort) != null) {
			String[] sort = criterias.get(pageSort).toString().split(" ");

			if (sort.length == 2) {
				if ("desc".equals(sort[1])) {// createTime desc
					searchSourceBuilder.sort(sort[0], SortOrder.DESC);
				} else if ("asc".equals(sort[1])) {// createTime asc
					searchSourceBuilder.sort(sort[0], SortOrder.ASC);
				}
			} else if (sort.length == 1) {// createTime
				searchSourceBuilder.sort(sort[0], SortOrder.ASC);
			}
			criterias.remove(pageSort);
		}

		if (!needSource) {
			// searchSourceBuilder.addFields();
		}

		if (criterias != null && !criterias.isEmpty()) {
			// 构建查询条件必须嵌入filter中！
			searchSourceBuilder.query(builder(criterias));
		}
		searchRequest.source(searchSourceBuilder);
		// 请求
		SearchResponse searchResponse;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			// 响应
			SearchHits hits = searchResponse.getHits();
			Map<String, Object> results = new HashMap<String, Object>();
			long total = hits.getTotalHits();
			results.put(pageNum, page);
			results.put(pageSize, size);
			results.put(pageTotal, total);
			results.put(pagePages, (total - 1) / size + 1);
			List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
			// 无查询结果
			if (total > 0) {
				SearchHit[] hitList = hits.getHits();
				for (SearchHit searchHit : hitList) {
					Map<String, Object> e = parseObject(searchHit.getSourceAsString(), searchHit.getId());
					if (e != null) {
						list.add(e);
					}
				}
			}
			results.put(pageList, list);
			return results;
		} catch (IOException e1) {
		}

		return null;

	}

	/**
	 * 执行命中数查询
	 * @param type
	 * @param criterias
	 * @param needSource
	 * @return
	 */
	private static Map<String, Object> executeSearchCounts(String type, Map<String, Object> criterias,
														 boolean needSource) {
		List<String> dates=Arrays.asList(type.split(","));
		String [] datesArray = dates.toArray(new String[dates.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		// 指定查询的库表
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		searchSourceBuilder.trackTotalHits(true);
		if (criterias.get(pageSort) != null) {
			String[] sort = criterias.get(pageSort).toString().split(" ");

			if (sort.length == 2) {
				if ("desc".equals(sort[1])) {// createTime desc
					searchSourceBuilder.sort(sort[0], SortOrder.DESC);
				} else if ("asc".equals(sort[1])) {// createTime asc
					searchSourceBuilder.sort(sort[0], SortOrder.ASC);
				}
			} else if (sort.length == 1) {// createTime
				searchSourceBuilder.sort(sort[0], SortOrder.ASC);
			}
			criterias.remove(pageSort);
		}

		if (!needSource) {
			// searchSourceBuilder.addFields();
		}

		if (criterias != null && !criterias.isEmpty()) {
			// 构建查询条件必须嵌入filter中！
			searchSourceBuilder.query(builder(criterias));
		}
		searchRequest.source(searchSourceBuilder);
		// 请求
		SearchResponse searchResponse;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			// 响应
			SearchHits hits = searchResponse.getHits();
			Map<String, Object> results = new HashMap<String, Object>();
			long total = hits.getTotalHits();
			results.put(pageTotal, total);
			return results;
		} catch (IOException e1) {

		}
		return null;

	}

	/**
	 * 目前日志支持两种模式 1，简单模式：只能进行and条件累加
	 * 2，复杂模式：将条件组成json串传入参数complexQuery，其中每一个obj都是一个条件，如果obj中存在arr则认为这是一个条件组合，
	 * type字段可以省略默认值为and 单一条件样例：{type:"or",key:"type",value:"SMS"}，
	 * 条件组合样例：{arr:[{type:"or",key:"type",value:"SMS"},{type:"or",key:"type",
	 * value:"bills"}]} 按此结构可以任意组合，长度与深度不限制
	 * 
	 * @param criterias
	 * @return
	 */
	public static BoolQueryBuilder builder(Map<String, Object> criterias) {
		// 复杂查询保留字
		String complexQuery = (String) criterias.get("complexQuery");
		if (!StringUtils.isEmpty(complexQuery)) {
			return getArrBuilder(JSONArray.parseArray(complexQuery, Map.class));
		} else {
			return getArrBuilder(criterias);
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static BoolQueryBuilder orbuilder(List<Map> list) {
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		for (Map map : list) {
			res.add(map);
		}

		return orbuilder(null, res);
	}

	public static BoolQueryBuilder getArrBuilder(Map<String, Object> map) {
		List<Map> list = new ArrayList<Map>();
		for (String key : map.keySet()) {
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("key", key);
			temp.put("value", map.get(key));
			temp.put("type", "and");
			list.add(temp);
		}
		return getArrBuilder(list);
	}

	/**
	 * 或查询，目前支持一层or，如果以后需要无限层or可以试着把这个改成递归
	 *
	 * @param list
	 * @return
	 */
	public static BoolQueryBuilder getArrBuilder(List<Map> list) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		for (Map<String, Object> map : list) {

			String key = (String) map.get("key");
			String value = (String) map.get("value");
			String type = (String) map.get("type");

			QueryBuilder queryBuilder = null;
			if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
				queryBuilder = getBuilder(key + "", value);
			} else {
				List<Map> arr = (List<Map>) map.get("arr");
				if (arr != null && arr.size() > 0) {
					queryBuilder = getArrBuilder(arr);
				}
			}

			if (queryBuilder != null) {
				if ("or".equals(type)) {
					boolQueryBuilder.should(queryBuilder);
				} else {// 默认为and
					if (!StringUtils.isEmpty(key) && key.startsWith("!")) {
						boolQueryBuilder.mustNot(queryBuilder);
					} else {
						boolQueryBuilder.must(queryBuilder);
					}
				}
			}
		}
		return boolQueryBuilder;
	}

	public static QueryBuilder getBuilder(String key, String value) {
		// TODO 或条件查询
		QueryBuilder result = null;
		if (!StringUtils.isEmpty(value)) {
			if (key.startsWith("*")) {
				// 模糊
				key = key.substring(1, key.length());
				result = QueryBuilders.wildcardQuery(key, "*" + value + "*");
			} else if (key.startsWith("!")) {
				// 不等于 ==》 此方法必须和mustNot配合使用
				key = key.substring(1, key.length());
				result = QueryBuilders.termQuery(key, value);
			} else if (key.startsWith("<=")) {
				// 小于等于
				key = key.substring(2, key.length());
				result = QueryBuilders.rangeQuery(key).lte(value);

			} else if (key.startsWith("<")) {
				// 小于
				key = key.substring(1, key.length());
				result = QueryBuilders.rangeQuery(key).lt(value);
				
			} else if (key.startsWith(">=")) {
				// 大于等于
				key = key.substring(2, key.length());
				result = QueryBuilders.rangeQuery(key).gte(value);

			} else if (key.startsWith(">")) {
				// 大于
				key = key.substring(1, key.length());
				result = QueryBuilders.rangeQuery(key).gt(value);

			} else {
				// 全等
				// result = QueryBuilders.wildcardQuery(key, value);

				// id只支持全等匹配
				if ("id".equals(key)) {
					result = QueryBuilders.idsQuery().addIds(value);
				} else if ("ids".equals(key)) {
					result = QueryBuilders.idsQuery().addIds(value.split(","));
				}else{
					List<String> vlist = Arrays.asList(value.split(","));
					if (vlist != null && vlist.size() > 1) {
						result = QueryBuilders.termsQuery(key, vlist);
					} else {
						result = QueryBuilders.termQuery(key, value);
					}
					// result = QueryBuilders.termQuery(key, value);
				}

			}
		}

		return result;

	}

	/**
	 * 或查询，目前支持一层or，如果以后需要无限层or可以试着把这个改成递归
	 * 
	 * @param boolQueryBuilder
	 * @param ors
	 * @return
	 */
	public static BoolQueryBuilder orbuilder(BoolQueryBuilder boolQueryBuilder, List<Map<String, Object>> ors) {
		if (boolQueryBuilder == null) {
			boolQueryBuilder = QueryBuilders.boolQuery();
		}
		for (Map<String, Object> map : ors) {
			boolQueryBuilder.should(andbuilder(map));
		}
		return boolQueryBuilder;
	}

	public static BoolQueryBuilder andbuilder(Map<String, Object> criterias) {
		return andbuilder(null, criterias);
	}

	/**
	 * 与查询，目前支持全等，模糊，大于，小于，大于等于，小于等于
	 * 
	 * @param boolQueryBuilder
	 * @param criterias
	 * @return
	 */
	public static BoolQueryBuilder andbuilder(BoolQueryBuilder boolQueryBuilder, Map<String, Object> criterias) {
		// TODO in，不等于，，或条件查询
		if (boolQueryBuilder == null) {
			boolQueryBuilder = QueryBuilders.boolQuery();
		}
		for (Entry<String, Object> entry : criterias.entrySet()) {
			String key = entry.getKey();
			String value = (String) entry.getValue();
			if (!StringUtils.isEmpty(value)) {
				boolQueryBuilder.must(getBuilder(key, value));
			}
		}

		return boolQueryBuilder;

	}
	
	public static void timedTaskDeleteIomPmvPerformanceTable(int days){   	
        try {
        	List<String> retainIndexNames=new ArrayList<String>();
        	Date currDate=new Date();
    		Calendar calendar = Calendar.getInstance();  
            calendar.setTime(currDate);  
            calendar.add(Calendar.DAY_OF_MONTH, -days);  
            Date date = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String time=sdf.format(date);
            retainIndexNames.add("iom_pmv_performance-"+time);
            RestHighLevelClient client = EsTcpClient.getClient();
            GetAliasesRequest request = new GetAliasesRequest();
			org.elasticsearch.client.GetAliasesResponse alias = client.indices().getAlias(request, RequestOptions.DEFAULT);
			Map<String, Set<AliasMetaData>> map = alias.getAliases();
			List<String> indexNames=new ArrayList<String>();
			
			map.forEach((k, v) -> {
	            if (!k.startsWith(".")) {//忽略elasticesearch 默认的
	            	indexNames.add(k);
	            }
	        });
			for(int i=1;i<=days;i++) {
				Calendar calendar1 = Calendar.getInstance();  
	            calendar1.setTime(date);
	            calendar1.add(Calendar.DAY_OF_MONTH, +i);
	            Date dateTime = calendar1.getTime();
	            String time1=sdf.format(dateTime);
	            retainIndexNames.add("iom_pmv_performance-"+time1);
			}
			if(indexNames!=null && indexNames.size()>0) {
				for(String indexName:indexNames) {
					int index=indexName.indexOf("-");
					if(index!=-1) {
						String timeStr=indexName.substring(index+1);
						boolean flag=isDate(timeStr);
						if(flag) {
							boolean flag1=indexName.contains("iom_pmv_performance");
							if(flag1) {
								boolean flag2=retainIndexNames.contains(indexName);
								if(!flag2) {
									Calendar calendar2 = Calendar.getInstance();  
						            calendar2.setTime(currDate);  
						            calendar2.add(Calendar.DAY_OF_MONTH, +1);  
						            Date date2 = calendar2.getTime();
						            String time2=sdf.format(date2);
						            if(!timeStr.equals(time2)) {
						            	deleteIndex(indexName);
						            	System.out.println("---------删除的索引："+indexName);
						            }
									
								}
							}
							
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static boolean isDate(String strDate) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			sdf.parse(strDate);
			return true;
		} catch (Exception e) {
			return false;
		}
    }
	
	public static void timedTaskDeleteSingleIndexDatas(Map<String, Object> criterias,String indexes){
		try {
			BoolQueryBuilder boolQueryBuilder = EsUtil.builder(criterias);
			DeleteByQueryRequest deleteByQueryRequest=new DeleteByQueryRequest(indexes);
			deleteByQueryRequest.setQuery(boolQueryBuilder);
			deleteByQueryRequest.setConflicts("proceed");
			deleteByQueryRequest.setBatchSize(10000);
			//deleteByQueryRequest.setRefresh(true);
			deleteByQueryRequest.setSlices(30);
			//deleteByQueryRequest client = EsTcpClient.getClient();
			client.deleteByQueryAsync(deleteByQueryRequest, RequestOptions.DEFAULT, new ActionListener<BulkByScrollResponse>() { @Override public void onResponse(BulkByScrollResponse response) { } @Override public void onFailure(Exception e) { } });
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}