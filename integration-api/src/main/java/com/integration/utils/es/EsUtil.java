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

	//private static boolean initFlag = false;// ???????????????
	
	private static RestHighLevelClient client = EsTcpClient.getClient();
	
	private static String[] parsePatterns = {"yyyy-MM-dd","yyyy???MM???dd???",
	        "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
	        "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"};

	/**
	 * ?????????es??????????????? ????????????????????????????????????????????????????????????????????????????????? ???????????????????????????????????????????????????
	 * ??????????????????????????????????????????elasticsearch????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
	 * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
	 */
	public static void initIndex() {
		//?????????????????????ES????????????
		String initKey = "initES-" + EsTcpClient.getHost()+"-"+EsTcpClient.getPort();
		/*while (RedisUtils.exists(initKey)) {
			logger.info("???????????????????????????????????????ES???"+initKey+"??????30???");
			try {
				Thread.sleep(1000*30L);
			} catch (InterruptedException e) {
				logger.error("Thread.sleep???????????????");
			}
		}*/
		RedisUtils.set(initKey, "start",1000*60*60*3L);
		
		
		// File f = new File(path.toString());

		// ?????????????????????????????????????????????????????????easearch???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
		// ?????????????????????????????????????????????????????????????????????????????????
		//if (!initFlag) {
			// ?????????
			// ??????????????????????????????????????????
			indexCreate(version);

			// ??????
			try {
				updateType();
			} catch (IOException e) {
				logger.error("??????????????????");
				e.printStackTrace();
			}

			// ??????????????????????????????????????????????????????????????????????????????????????????
			//initFlag = true;
		//}
		//?????????,????????????
		RedisUtils.remove(initKey);

	}

	public static boolean indexCreate(String indexName) {
		if (!existIndex(indexName)) {
			try {
				// ????????????type???????????????type
				InputStream in = EsUtil.class.getClassLoader()
						.getResourceAsStream("types/install/" + indexName + ".json");
				String result = IOUtils.toString(in, "UTF-8");
				result = JSONObject.toJSONString(JSONObject.parseObject(result));
				return indexCreate(indexName, result);
			} catch (IOException e) {
				logger.info("?????????" + indexName + "???????????????");
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
				logger.info("?????????" + indexName + "???????????????");
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
				logger.info("?????????" + indexName + "???????????????");
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
			// ???????????????????????????
			List<Integer> nameList = new ArrayList<Integer>();
			for (Integer key : fileNames) {
				if (old == null || key > old) {
					nameList.add(key);
				}
			}
			
			if (nameList.size() > 0) {
				// ????????????????????????
				Object[] nameArr = nameList.toArray();
				Arrays.sort(nameArr);// ??????????????????

				// ??????????????????????????????
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
					//map.put("name", name.toString());// ?????????
					//map.put("createTime", DateUtils.parseDate(new Date()));// ????????????????????????
					//insert(map, version);// ????????????????????????????????????????????????????????????????????????????????????????????????????????????
				}
			}
			
		}
	}
	
	private static void updateType() throws IOException {
		// ????????????
		List<Integer> fileNames = getFileNames();
		if (fileNames.size() > 0) {
			// ??????????????????
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

			// ???????????????????????????
			List<Integer> nameList = new ArrayList<Integer>();

			for (Integer key : fileNames) {
				if (old == null || key > old) {
					nameList.add(key);
				}
			}

			if (nameList.size() > 0) {
				// ????????????????????????
				Object[] nameArr = nameList.toArray();
				Arrays.sort(nameArr);// ??????????????????

				// ??????????????????????????????
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
					map.put("name", name.toString());// ?????????
					map.put("createTime", DateUtils.parseDate(new Date()));// ????????????????????????
					insert(map, version);// ????????????????????????????????????????????????????????????????????????????????????????????????????????????
				}
			}else {
				for (Integer key : fileNames) {
						nameList.add(key);
				}
				if (nameList.size() > 0) {
					// ????????????????????????
					Object[] nameArr = nameList.toArray();
					Arrays.sort(nameArr);// ??????????????????

					// ??????????????????????????????
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
						map.put("name", name.toString());// ?????????
						map.put("createTime", DateUtils.parseDate(new Date()));// ????????????????????????
						insert(map, version);// ????????????????????????????????????????????????????????????????????????????????????????????????????????????
					}
				}
				
			}
		}
	}

	private static List<Integer> getFileNames() throws IOException {
		List<Integer> nameList = new ArrayList<Integer>();

		URL dirPath = EsUtil.class.getClassLoader().getResource(updateDir);
		logger.info("===================dirPath:" + dirPath.getPath());
		if (dirPath.getPath().indexOf("!/") > -1) {// jar?????????
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
		} else {// ????????????
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
	 * ??????
	 * 
	 * @param doc
	 * @return
	 */
	public static String insert(Map<String, Object> doc, String index) {
		String esId = (String) doc.get("id");// ???id????????????????????????????????????????????????????????????????????????
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
	 * ??????????????????
	 * @param doc
	 * @param index
	 */
	public static void insertList(List doc, String index) {

		BulkRequest request = new BulkRequest();
		for (Object object : doc) {
			request.add(new IndexRequest(index,"_doc").source(JSONObject.toJSONString(object), XContentType.JSON).id(String.valueOf(((Map)object).get("id"))));
		}
         /*
         ??????????????????
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
	 * ??????
	 * 
	 * @param doc
	 * @param type
	 * @return
	 */
	public static boolean replace(Map<String, Object> doc, String type) {
		return update(doc, type);
	}

	/**
	 * ??????
	 * 
	 * @param doc
	 * @return
	 */
	public static boolean update(Map<String, Object> doc, String index) {
		String esId = (String) doc.get("id");// ???id????????????????????????????????????????????????????????????????????????
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
	 * ??????
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
	 * ??????????????????json?????????id??????????????????????????????
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
	 * ??????????????????json?????????ID????????????????????????
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
	 * ??????ID??????
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
	 * ????????????
	 * 
	 * @param type
	 * @param criterias
	 * @return
	 */
	public static List<Map<String, Object>> search(String type, Map<String, Object> criterias) {
		return executeSearch(type, criterias, true);
	}

	/**
	 * ?????????????????????
	 *
	 * @param type
	 * @param criterias
	 * @return
	 */
	public static int searchCount(String type, Map<String, Object> criterias) {
		return executeSearchCount(type, criterias, true);
	}

	/**
	 * ????????????
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
	 * ?????????????????????
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
	 * ??????????????????
	 * 
	 * @param type
	 * @return
	 */
	public static Map<String, Object> searchPage(String type) {
		return executeSearchPage(type, new HashMap<String, Object>(), true);
	}

	/**
	 * ??????????????????
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
	 * ??????????????????
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
		// ?????????????????????
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		int page = criterias.get(pageNum) == null ? 1 : new Integer(criterias.get(pageNum).toString());
		// ???smv??????????????????????????????????????????????????????????????????????????????????????????????????????1000????????????10---2020-04-01??????
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
			// ??????????????????????????????filter??????
			searchSourceBuilder.query(builder(criterias));
		}
		searchRequest.source(searchSourceBuilder);
		// ??????
		SearchResponse searchResponse;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			// ??????
			SearchHits hits = searchResponse.getHits();
			Map<String, Object> results = new HashMap<String, Object>();
			long total = hits.getTotalHits();
			results.put(pageNum, page);
			results.put(pageSize, size);
			results.put(pageTotal, total);
			results.put(pagePages, (total - 1) / size + 1);
			List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
			// ???????????????
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
	 * ?????????????????????
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
		// ?????????????????????
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
			// ??????????????????????????????filter??????
			searchSourceBuilder.query(builder(criterias));
		}
		searchRequest.source(searchSourceBuilder);
		// ??????
		SearchResponse searchResponse;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			// ??????
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
	 * ?????????????????????????????? 1??????????????????????????????and????????????
	 * 2?????????????????????????????????json???????????????complexQuery??????????????????obj???????????????????????????obj?????????arr????????????????????????????????????
	 * type??????????????????????????????and ?????????????????????{type:"or",key:"type",value:"SMS"}???
	 * ?????????????????????{arr:[{type:"or",key:"type",value:"SMS"},{type:"or",key:"type",
	 * value:"bills"}]} ?????????????????????????????????????????????????????????
	 * 
	 * @param criterias
	 * @return
	 */
	public static BoolQueryBuilder builder(Map<String, Object> criterias) {
		// ?????????????????????
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
	 * ??????????????????????????????or??????????????????????????????or?????????????????????????????????
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
				} else {// ?????????and
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
		// TODO ???????????????
		QueryBuilder result = null;
		if (!StringUtils.isEmpty(value)) {
			if (key.startsWith("*")) {
				// ??????
				key = key.substring(1, key.length());
				result = QueryBuilders.wildcardQuery(key, "*" + value + "*");
			} else if (key.startsWith("!")) {
				// ????????? ==??? ??????????????????mustNot????????????
				key = key.substring(1, key.length());
				result = QueryBuilders.termQuery(key, value);
			} else if (key.startsWith("<=")) {
				// ????????????
				key = key.substring(2, key.length());
				result = QueryBuilders.rangeQuery(key).lte(value);

			} else if (key.startsWith("<")) {
				// ??????
				key = key.substring(1, key.length());
				result = QueryBuilders.rangeQuery(key).lt(value);
				
			} else if (key.startsWith(">=")) {
				// ????????????
				key = key.substring(2, key.length());
				result = QueryBuilders.rangeQuery(key).gte(value);

			} else if (key.startsWith(">")) {
				// ??????
				key = key.substring(1, key.length());
				result = QueryBuilders.rangeQuery(key).gt(value);

			} else {
				// ??????
				// result = QueryBuilders.wildcardQuery(key, value);

				// id?????????????????????
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
	 * ??????????????????????????????or??????????????????????????????or?????????????????????????????????
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
	 * ???????????????????????????????????????????????????????????????????????????????????????
	 * 
	 * @param boolQueryBuilder
	 * @param criterias
	 * @return
	 */
	public static BoolQueryBuilder andbuilder(BoolQueryBuilder boolQueryBuilder, Map<String, Object> criterias) {
		// TODO in?????????????????????????????????
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
	            if (!k.startsWith(".")) {//??????elasticesearch ?????????
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
						            	System.out.println("---------??????????????????"+indexName);
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