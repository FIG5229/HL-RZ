package com.integration.utils.es.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Joiner;
import com.integration.entitypmv.PmvPerformanceCurrentInfo;
import com.integration.utils.HumpLineUtil;
import com.integration.utils.es.EsTcpClient;
import com.integration.utils.es.EsUtil;
import com.integration.utils.es.entity.PmvPerformance;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.*;
import org.elasticsearch.search.aggregations.bucket.composite.DateHistogramValuesSourceBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ExtendedBounds;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.avg.ParsedAvg;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.max.ParsedMax;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.min.ParsedMin;
import org.elasticsearch.search.aggregations.metrics.sum.ParsedSum;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.tophits.ParsedTopHits;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 性能关于ES的搜索
 *
 * @author yangxichuan
 */

public class Pmv {

	private static final Logger log = LoggerFactory.getLogger(Pmv.class);

	private static final String iom_pmv_performance = "iom_pmv_performance";
	private static final String occ_time = "occ_time";
	private static final String ci_id = "ci_id";
	private static final String val = "val";
	private static final String ci_name = "ci_name";
	private static final String ciName = "ciName";
	private static final String timeNum = "timeNum";
	private static final String ciId = "ciId";
	private static final String kpi_id = "kpi_id";
	private static final String kpi_name = "kpi_name";
	public static final char UNDERLINE = '_';

//	{
//	    "size": 0,
//	    "aggs": {
//	        "occ_time": {
//	            "date_histogram": {
//	                "field": "occ_time",
//	                "interval": "3d",
//	                "min_doc_count": 0,
//	                "extended_bounds": {
//	                    "min": "2019-11-15 00:00:00",
//	                    "max": "2019-11-18 23:59:59"
//	                }
//	            },
//	            "aggs": {
//	                "ci_id": {
//	                    "terms": {
//	                        "field": "ci_id"
//	                    },
//	                    "aggs": {
//	                        "val": {
//	                            "min": {
//	                                "field": "sum"
//	                            }
//	                        },
//	                        "ci_name": {
//	                            "top_hits": {
//	                                "size": 1
//	                            }
//	                        }
//	                    }
//	                }
//	            }
//	        }
//	    }
//	}
	
	public static List<Map<String, Object>> selectChartDataGroupByCiThreshold(String selectMethod, Long interval,
			String startTime, String endTime, String ciTypeId, String kpiId, Boolean isGroByCi, String ciIds,
			String order,String ciCodes,String kpiName) {
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		interval = interval == null ? 1000 : interval;
		List<Map<String, Object>> res = null;
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		//criterias.put("ci_id", ciIds);
		criterias.put("ci_name", ciCodes);
		criterias.put("kpi_name", kpiName);
		//criterias.put("ci_type_id", ciTypeId);
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		if (StringUtils.isNotEmpty(selectMethod)) {
			selectMethod = selectMethod.trim().toLowerCase();
			RestHighLevelClient client = EsTcpClient.getClient();
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String [] datesArray = tableList.toArray(new String[tableList.size()]);
			SearchRequest searchRequest = new SearchRequest(datesArray);
			// 指定查询的库表
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(EsUtil.builder(criterias));
			searchSourceBuilder.size(0);
			// searchSourceBuilder.sort("cjsj", SortOrder.ASC);
			// 默认平均值
			AbstractAggregationBuilder vals = AggregationBuilders.avg(val).field(val);
			if ("sum".equals(selectMethod)) {
				vals = AggregationBuilders.sum(val).field(val);
			} else if ("max".equals(selectMethod)) {
				vals = AggregationBuilders.max(val).field(val);
			} else if ("min".equals(selectMethod)) {
				vals = AggregationBuilders.min(val).field(val);
			}
			TopHitsAggregationBuilder ciName = AggregationBuilders.topHits(ci_name).size(1);

			DateHistogramAggregationBuilder occTime = AggregationBuilders.dateHistogram(occ_time).field(occ_time)
					.interval(interval).minDocCount(0).extendedBounds(new ExtendedBounds(startTime, endTime));

//            DateHistogramValuesSourceBuilder sendtime = new DateHistogramValuesSourceBuilder("occ_time")
//                         .field("occ_time")
//                         .dateHistogramInterval(DateHistogramInterval.days(1))
//                         .order(SortOrder.DESC)
//                         .missingBucket(false);

			if (isGroByCi != null && isGroByCi) {
				TermsAggregationBuilder ci = AggregationBuilders.terms(ci_id).field(ci_id);
				ci.subAggregation(vals);
				ci.subAggregation(ciName);
				occTime.subAggregation(ci);
			} else {
				occTime.subAggregation(vals);
				occTime.subAggregation(ciName);
			}

			searchSourceBuilder.aggregation(occTime);
			searchRequest.source(searchSourceBuilder);
			try {
				res = timeSearchResponse2List(client.search(searchRequest, RequestOptions.DEFAULT));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			res = new ArrayList<Map<String, Object>>();
			criterias.put("sort", "cjsj");
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String indexes = String.join(",", tableList);
			List<Map<String, Object>> list = EsUtil.search(indexes, criterias);
			if (list != null && list.size() > 0) {
				for (Map<String, Object> map : list) {
					Map<String, Object> one = new HashMap<String, Object>();
					one.put(val, map.get(val));
					one.put(ciId, map.get(ci_id));
					one.put(ciName, map.get(ci_name));
					one.put("cjsj", map.get("cjsj"));
					one.put("occTime", map.get("occ_time"));
					if (map.get(occ_time) != null) {
						try {
							one.put(timeNum, sdf.parse(map.get(occ_time).toString()).getTime());
						} catch (ParseException e) {
						}
					}
					res.add(one);
				}
			}
		}
//		if(StringUtils.isEmpty(selectMethod)) {
//			return res;
//		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		if (res != null && res.size() > 0) {
			for (Map<String, Object> map : res) {
				Object valObject = map.get(val);
				if (valObject != null) {
					String valString = valObject.toString();
					String ciIdStr = "";
					String ciNameStr = "";
					// 循环把res里面的数据中ciId和ciName取出来——start
					for (Map<String, Object> mapRes : res) {
						boolean ciIdFlag = mapRes.containsKey(ciId);
						if (ciIdFlag) {
							ciIdStr = (String) mapRes.get("ciId");
						}
						boolean ciNameFlag = mapRes.containsKey(ciId);
						if (ciNameFlag) {
							ciNameStr = (String) mapRes.get("ciName");
						}
					}
					// 循环把res里面的数据中ciId和ciName取出来——end
					if (StringUtils.isNotEmpty(valString) && !"Infinity".equals(valString)) {
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, valString);
						oneMap.put("ciName", map.get(ciName));
						Object ciIdObject = map.get(ciId);
						if (ciIdObject != null) {
							oneMap.put("ciId", ciIdObject.toString());
						}
						Object timeNumObject = map.get(timeNum);
						if (timeNumObject != null) {
							oneMap.put(timeNum, timeNumObject.toString());
						}
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					} else {
						Object timeNumObject = map.get(timeNum);
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, 0);
						oneMap.put("ciName", ciNameStr);
						oneMap.put("ciId", ciIdStr);
						oneMap.put(timeNum, timeNumObject.toString());
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					}
				}
			}
		}

		return returnList;
	}

	/**
	 * 执行搜索
	 *
	 * @param selectMethod
	 * @param interval
	 * @param startTime
	 * @param endTime
	 * @param ciTypeId
	 * @param kpiId
	 * @param isGroByCi
	 * @param ciIds
	 * @param order
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static List<Map<String, Object>> selectChartDataGroupByCi(String selectMethod, Long interval,
			String startTime, String endTime, String ciTypeId, String kpiId, Boolean isGroByCi, String ciIds,
			String order,String ciCodes,String kpiName) {
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		interval = interval == null ? 1000 : interval;
		List<Map<String, Object>> res = null;
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		//criterias.put("ci_id", ciIds);
		criterias.put("ci_name", ciCodes);
		criterias.put("kpi_name", kpiName);
		//criterias.put("ci_type_id", ciTypeId);
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		if (StringUtils.isNotEmpty(selectMethod)) {
			selectMethod = selectMethod.trim().toLowerCase();
			RestHighLevelClient client = EsTcpClient.getClient();
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String [] datesArray = tableList.toArray(new String[tableList.size()]);
			SearchRequest searchRequest = new SearchRequest(datesArray);
			// 指定查询的库表
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(EsUtil.builder(criterias));
			searchSourceBuilder.size(0);
			// searchSourceBuilder.sort("cjsj", SortOrder.ASC);
			// 默认平均值
			AbstractAggregationBuilder vals = AggregationBuilders.avg(val).field(val);
			if ("sum".equals(selectMethod)) {
				vals = AggregationBuilders.sum(val).field(val);
			} else if ("max".equals(selectMethod)) {
				vals = AggregationBuilders.max(val).field(val);
			} else if ("min".equals(selectMethod)) {
				vals = AggregationBuilders.min(val).field(val);
			}
			TopHitsAggregationBuilder ciName = AggregationBuilders.topHits(ci_name).size(1);

			DateHistogramAggregationBuilder occTime = AggregationBuilders.dateHistogram(occ_time).field(occ_time)
					.interval(interval).minDocCount(0).extendedBounds(new ExtendedBounds(startTime, endTime));

//            DateHistogramValuesSourceBuilder sendtime = new DateHistogramValuesSourceBuilder("occ_time")
//                         .field("occ_time")
//                         .dateHistogramInterval(DateHistogramInterval.days(1))
//                         .order(SortOrder.DESC)
//                         .missingBucket(false);

			if (isGroByCi != null && isGroByCi) {
				TermsAggregationBuilder ci = AggregationBuilders.terms(ci_id).field(ci_id);
				ci.subAggregation(vals);
				ci.subAggregation(ciName);
				occTime.subAggregation(ci);
			} else {
				occTime.subAggregation(vals);
				occTime.subAggregation(ciName);
			}

			searchSourceBuilder.aggregation(occTime);
			searchRequest.source(searchSourceBuilder);
			try {
				res = timeSearchResponse2List(client.search(searchRequest, RequestOptions.DEFAULT));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			res = new ArrayList<Map<String, Object>>();
			criterias.put("sort", "cjsj");
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String indexes = String.join(",", tableList);
			List<Map<String, Object>> list = EsUtil.search(indexes, criterias);
			if (list != null && list.size() > 0) {
				for (Map<String, Object> map : list) {
					Map<String, Object> one = new HashMap<String, Object>();
					one.put(val, map.get(val));
					one.put(ciId, map.get(ci_id));
					one.put(ciName, map.get(ci_name));
					one.put("cjsj", map.get("cjsj"));
					one.put("occTime", map.get("occ_time"));
					if (map.get(occ_time) != null) {
						try {
							one.put(timeNum, sdf.parse(map.get(occ_time).toString()).getTime());
						} catch (ParseException e) {
						}
					}
					res.add(one);
				}
			}
		}
		if(StringUtils.isEmpty(selectMethod)) {
			return res;
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		if (res != null && res.size() > 0) {
			for (Map<String, Object> map : res) {
				Object valObject = map.get(val);
				if (valObject != null) {
					String valString = valObject.toString();
					String ciIdStr = "";
					String ciNameStr = "";
					// 循环把res里面的数据中ciId和ciName取出来——start
					for (Map<String, Object> mapRes : res) {
						boolean ciIdFlag = mapRes.containsKey(ciId);
						if (ciIdFlag) {
							ciIdStr = (String) mapRes.get("ciId");
						}
						boolean ciNameFlag = mapRes.containsKey(ciId);
						if (ciNameFlag) {
							ciNameStr = (String) mapRes.get("ciName");
						}
					}
					// 循环把res里面的数据中ciId和ciName取出来——end
					if (StringUtils.isNotEmpty(valString) && !"Infinity".equals(valString)) {
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, valString);
						oneMap.put("ciName", map.get(ciName));
						Object ciIdObject = map.get(ciId);
						if (ciIdObject != null) {
							oneMap.put("ciId", ciIdObject.toString());
						}
						Object timeNumObject = map.get(timeNum);
						if (timeNumObject != null) {
							oneMap.put(timeNum, timeNumObject.toString());
						}
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					} else {
						Object timeNumObject = map.get(timeNum);
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, 0);
						oneMap.put("ciName", ciNameStr);
						oneMap.put("ciId", ciIdStr);
						oneMap.put(timeNum, timeNumObject.toString());
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					}
				}
			}
		}

		return returnList;
	}
	
    /**
     * 执行搜索(济宁项目)
     *
     * @param selectMethod
     * @param interval
     * @param startTime
     * @param endTime
     * @param ciTypeId
     * @param kpiId
     * @param isGroByCi
     * @param ciIds
     * @param order
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static List<Map<String, Object>> selectChartDataGroupByCiToJniom(String selectMethod, Long interval,
                                                                     String startTime, String endTime, String ciTypeId, String kpiId, Boolean isGroByCi, String ciIds,
                                                                     String order) {
    	List<String> tables=new ArrayList<String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        interval = interval == null ? 1000 : interval;
        List<Map<String, Object>> res = null;
        Map<String, Object> criterias = new HashMap<String, Object>();
        criterias.put(">=occ_time", startTime);
        criterias.put("<=occ_time", endTime);
        criterias.put("ci_id", ciIds);
        criterias.put("kpi_id", kpiId);
        criterias.put("ci_type_id", ciTypeId);
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
        if (StringUtils.isNotEmpty(selectMethod)) {
            selectMethod = selectMethod.trim().toLowerCase();
            RestHighLevelClient client = EsTcpClient.getClient();
            List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
            String [] datesArray = tableList.toArray(new String[tableList.size()]);
            SearchRequest searchRequest = new SearchRequest(datesArray);
            // 指定查询的库表
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(EsUtil.builder(criterias));
            searchSourceBuilder.size(0);
            // searchSourceBuilder.sort("cjsj", SortOrder.ASC);
            // 默认平均值
            AbstractAggregationBuilder vals = AggregationBuilders.avg(val).field(val);
            if ("sum".equals(selectMethod)) {
                vals = AggregationBuilders.sum(val).field(val);
            } else if ("max".equals(selectMethod)) {
                vals = AggregationBuilders.max(val).field(val);
            } else if ("min".equals(selectMethod)) {
                vals = AggregationBuilders.min(val).field(val);
            }
            TopHitsAggregationBuilder ciName = AggregationBuilders.topHits(ci_name).size(1);

            DateHistogramAggregationBuilder occTime = AggregationBuilders.dateHistogram(occ_time).field(occ_time)
                    .interval(interval);
            if (isGroByCi != null && isGroByCi) {
                TermsAggregationBuilder ci = AggregationBuilders.terms(ci_id).field(ci_id);
                ci.subAggregation(vals);
                ci.subAggregation(ciName);
                occTime.subAggregation(ci);
            } else {
                occTime.subAggregation(vals);
                occTime.subAggregation(ciName);
            }

            searchSourceBuilder.aggregation(occTime);

            searchRequest.source(searchSourceBuilder);
            try {
                res = timeSearchResponse2List(client.search(searchRequest, RequestOptions.DEFAULT));
            } catch (IOException e) {
            }
        } else {
            res = new ArrayList<Map<String, Object>>();
            criterias.put("sort", "cjsj");
            List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
            String indexes = String.join(",", tableList);
            List<Map<String, Object>> list = EsUtil.search(indexes, criterias);
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    Map<String, Object> one = new HashMap<String, Object>();
                    one.put(val, map.get(val));
                    one.put(ciId, map.get(ci_id));
                    one.put(ciName, map.get(ci_name));
                    one.put("occTime", map.get("occ_time"));
                    if (map.get(occ_time) != null) {
                        try {
                            one.put(timeNum, simpleDateFormat.parse(map.get(occ_time).toString()).getTime());
                        } catch (ParseException e) {
                        }
                    }
                    res.add(one);
                }
            }
        }
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        if (res != null && res.size() > 0) {
            for (Map<String, Object> map : res) {
                Object valObject = map.get(val);
                if (valObject != null) {
                    String valString = valObject.toString();
                    if (StringUtils.isNotEmpty(valString) && !"Infinity".equals(valString)) {
                        Map<String, Object> oneMap = new HashMap<String, Object>();
                        oneMap.put(val, Double.parseDouble(valObject.toString()));
                        oneMap.put(ciName, map.get(ciName));
                        Object ciIdObject = map.get(ciId);
                        if (ciIdObject != null) {
                            oneMap.put(ciId, ciIdObject.toString());
                            oneMap.put("occTime", map.get("occTime"));
                        }
                        Object timeNumObject = map.get(timeNum);
                        if (timeNumObject != null) {
                            oneMap.put(timeNum, timeNumObject.toString());
                            oneMap.put("occTime", map.get("occTime"));
                        }
                        returnList.add(oneMap);
                    }
                }
            }
        }

        return returnList;
    }
	
	/**
	 * 仪表盘---表盘图
	 *
	 * @param selectMethod
	 * @param interval
	 * @param startTime
	 * @param endTime
	 * @param ciTypeId
	 * @param kpiId
	 * @param isGroByCi
	 * @param ciIds
	 * @param order
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static List<Map<String, Object>> selectDialChartMap(String selectMethod, Long interval,
			String startTime, String endTime, String ciTypeId, String kpiId, Boolean isGroByCi, String ciIds,
			String order,String domainId) {
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		interval = interval == null ? 3600000 : interval;
		List<Map<String, Object>> res = null;
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		criterias.put("ci_id", ciIds);
		criterias.put("kpi_id", kpiId);
		criterias.put("ci_type_id", ciTypeId);
		if(domainId!=null) {
			criterias.put("domain_id", domainId);	
		}
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		if (StringUtils.isNotEmpty(selectMethod)) {
			selectMethod = selectMethod.trim().toLowerCase();
			RestHighLevelClient client = EsTcpClient.getClient();
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String [] datesArray = tableList.toArray(new String[tableList.size()]);
			SearchRequest searchRequest = new SearchRequest(datesArray);
			// 指定查询的库表
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(EsUtil.builder(criterias));
			searchSourceBuilder.size(0);
			// searchSourceBuilder.sort("cjsj", SortOrder.ASC);
			// 默认平均值
			AbstractAggregationBuilder vals = AggregationBuilders.avg(val).field(val);
			if ("sum".equals(selectMethod)) {
				vals = AggregationBuilders.sum(val).field(val);
			} else if ("max".equals(selectMethod)) {
				vals = AggregationBuilders.max(val).field(val);
			} else if ("min".equals(selectMethod)) {
				vals = AggregationBuilders.min(val).field(val);
			}
			TopHitsAggregationBuilder ciName = AggregationBuilders.topHits(ci_name).size(1);

			DateHistogramAggregationBuilder occTime = AggregationBuilders.dateHistogram(occ_time).field(occ_time)
					.interval(interval).minDocCount(0).extendedBounds(new ExtendedBounds(startTime, endTime));

//            DateHistogramValuesSourceBuilder sendtime = new DateHistogramValuesSourceBuilder("occ_time")
//                         .field("occ_time")
//                         .dateHistogramInterval(DateHistogramInterval.days(1))
//                         .order(SortOrder.DESC)
//                         .missingBucket(false);

			if (isGroByCi != null && isGroByCi) {
				TermsAggregationBuilder ci = AggregationBuilders.terms(ci_id).field(ci_id);
				ci.subAggregation(vals);
				ci.subAggregation(ciName);
				occTime.subAggregation(ci);
			} else {
				occTime.subAggregation(vals);
				occTime.subAggregation(ciName);
			}

			searchSourceBuilder.aggregation(occTime);
			searchRequest.source(searchSourceBuilder);
			try {
				res = timeSearchResponse2List(client.search(searchRequest, RequestOptions.DEFAULT));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			res = new ArrayList<Map<String, Object>>();
			criterias.put("sort", "cjsj");
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String indexes = String.join(",", tableList);
			List<Map<String, Object>> list = EsUtil.search(indexes, criterias);
			if (list != null && list.size() > 0) {
				for (Map<String, Object> map : list) {
					Map<String, Object> one = new HashMap<String, Object>();
					one.put(val, map.get(val));
					one.put(ciId, map.get(ci_id));
					one.put(ciName, map.get(ci_name));
					one.put("cjsj", map.get("cjsj"));
					one.put("occTime", map.get("occ_time"));
					if (map.get(occ_time) != null) {
						try {
							one.put(timeNum, sdf.parse(map.get(occ_time).toString()).getTime());
						} catch (ParseException e) {
						}
					}
					res.add(one);
				}
			}
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		if (res != null && res.size() > 0) {
			for (Map<String, Object> map : res) {
				Object valObject = map.get(val);
				if (valObject != null) {
					String valString = valObject.toString();
					String ciIdStr = "";
					String ciNameStr = "";
					// 循环把res里面的数据中ciId和ciName取出来——start
					for (Map<String, Object> mapRes : res) {
						boolean ciIdFlag = mapRes.containsKey(ciId);
						if (ciIdFlag) {
							ciIdStr = (String) mapRes.get("ciId");
						}
						boolean ciNameFlag = mapRes.containsKey(ciId);
						if (ciNameFlag) {
							ciNameStr = (String) mapRes.get("ciName");
						}
					}
					// 循环把res里面的数据中ciId和ciName取出来——end
					if (StringUtils.isNotEmpty(valString) && !"Infinity".equals(valString)) {
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, valString);
						oneMap.put("ciName", map.get(ciName));
						Object ciIdObject = map.get(ciId);
						if (ciIdObject != null) {
							oneMap.put("ciId", ciIdObject.toString());
						}
						Object timeNumObject = map.get(timeNum);
						if (timeNumObject != null) {
							oneMap.put(timeNum, timeNumObject.toString());
						}
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					} else {
						Object timeNumObject = map.get(timeNum);
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, 0);
						oneMap.put("ciName", ciNameStr);
						oneMap.put("ciId", ciIdStr);
						oneMap.put(timeNum, timeNumObject.toString());
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					}
				}
			}
		}

		return returnList;
	}
	
	/**
	 * 仪表盘---热力图
	 *
	 * @param selectMethod
	 * @param interval
	 * @param startTime
	 * @param endTime
	 * @param ciTypeId
	 * @param kpiId
	 * @param isGroByCi
	 * @param ciIds
	 * @param order
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static List<Map<String, Object>> selectThermodynamicChartMap(String selectMethod, Long interval,
			String startTime, String endTime, String ciTypeId, String kpiId, Boolean isGroByCi, String ciIds,
			String order,String domainId,String kpiName) {
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		interval = interval == null ? 1000 : interval;
		List<Map<String, Object>> res = null;
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		criterias.put("ci_id", ciIds);
		//criterias.put("kpi_id", kpiId);
		criterias.put("kpi_name", kpiName);
		criterias.put("ci_type_id", ciTypeId);
		if(domainId!=null) {
			criterias.put("domain_id", domainId);	
		}
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		if (StringUtils.isNotEmpty(selectMethod)) {
			selectMethod = selectMethod.trim().toLowerCase();
			RestHighLevelClient client = EsTcpClient.getClient();
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String [] datesArray = tableList.toArray(new String[tableList.size()]);
			SearchRequest searchRequest = new SearchRequest(datesArray);
			// 指定查询的库表
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(EsUtil.builder(criterias));
			searchSourceBuilder.size(0);
			// searchSourceBuilder.sort("cjsj", SortOrder.ASC);
			// 默认平均值
			AbstractAggregationBuilder vals = AggregationBuilders.avg(val).field(val);
			if ("sum".equals(selectMethod)) {
				vals = AggregationBuilders.sum(val).field(val);
			} else if ("max".equals(selectMethod)) {
				vals = AggregationBuilders.max(val).field(val);
			} else if ("min".equals(selectMethod)) {
				vals = AggregationBuilders.min(val).field(val);
			}
			TopHitsAggregationBuilder ciName = AggregationBuilders.topHits(ci_name).size(1);

			DateHistogramAggregationBuilder occTime = AggregationBuilders.dateHistogram(occ_time).field(occ_time)
					.interval(interval).minDocCount(0).extendedBounds(new ExtendedBounds(startTime, endTime));

//            DateHistogramValuesSourceBuilder sendtime = new DateHistogramValuesSourceBuilder("occ_time")
//                         .field("occ_time")
//                         .dateHistogramInterval(DateHistogramInterval.days(1))
//                         .order(SortOrder.DESC)
//                         .missingBucket(false);

			if (isGroByCi != null && isGroByCi) {
				TermsAggregationBuilder ci = AggregationBuilders.terms(ci_id).field(ci_id);
				ci.subAggregation(vals);
				ci.subAggregation(ciName);
				occTime.subAggregation(ci);
			} else {
				occTime.subAggregation(vals);
				occTime.subAggregation(ciName);
			}

			searchSourceBuilder.aggregation(occTime);
			searchRequest.source(searchSourceBuilder);
			try {
				res = timeSearchResponse2List(client.search(searchRequest, RequestOptions.DEFAULT));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			res = new ArrayList<Map<String, Object>>();
			criterias.put("sort", "cjsj");
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String indexes = String.join(",", tableList);
			List<Map<String, Object>> list = EsUtil.search(indexes, criterias);
			if (list != null && list.size() > 0) {
				for (Map<String, Object> map : list) {
					Map<String, Object> one = new HashMap<String, Object>();
					one.put(val, map.get(val));
					one.put(ciId, map.get(ci_id));
					one.put(ciName, map.get(ci_name));
					one.put("cjsj", map.get("cjsj"));
					one.put("occTime", map.get("occ_time"));
					if (map.get(occ_time) != null) {
						try {
							one.put(timeNum, sdf.parse(map.get(occ_time).toString()).getTime());
						} catch (ParseException e) {
						}
					}
					res.add(one);
				}
			}
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		if (res != null && res.size() > 0) {
			for (Map<String, Object> map : res) {
				Object valObject = map.get(val);
				if (valObject != null) {
					String valString = valObject.toString();
					String ciIdStr = "";
					String ciNameStr = "";
					// 循环把res里面的数据中ciId和ciName取出来——start
					for (Map<String, Object> mapRes : res) {
						boolean ciIdFlag = mapRes.containsKey(ciId);
						if (ciIdFlag) {
							ciIdStr = (String) mapRes.get("ciId");
						}
						boolean ciNameFlag = mapRes.containsKey(ciId);
						if (ciNameFlag) {
							ciNameStr = (String) mapRes.get("ciName");
						}
					}
					// 循环把res里面的数据中ciId和ciName取出来——end
					if (StringUtils.isNotEmpty(valString) && !"Infinity".equals(valString)) {
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, valString);
						oneMap.put("ciName", map.get(ciName));
						Object ciIdObject = map.get(ciId);
						if (ciIdObject != null) {
							oneMap.put("ciId", ciIdObject.toString());
						}
						Object timeNumObject = map.get(timeNum);
						if (timeNumObject != null) {
							oneMap.put(timeNum, timeNumObject.toString());
						}
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					} else {
						Object timeNumObject = map.get(timeNum);
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, 0);
						oneMap.put("ciName", ciNameStr);
						oneMap.put("ciId", ciIdStr);
						oneMap.put(timeNum, timeNumObject.toString());
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					}
				}
			}
		}

		return returnList;
	}
	
	
	/**
	 * 仪表盘---排行图
	 *
	 * @param selectMethod
	 * @param interval
	 * @param startTime
	 * @param endTime
	 * @param ciTypeId
	 * @param kpiId
	 * @param isGroByCi
	 * @param ciIds
	 * @param order
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static List<Map<String, Object>> selectRankChartMap(String selectMethod, Long interval,
			String startTime, String endTime, String ciTypeId, String kpiId, Boolean isGroByCi, String ciIds,
			String order,String domainId,String ciCodes,String kpiName) {
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		interval = interval == null ? 1000 : interval;
		List<Map<String, Object>> res = null;
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		//criterias.put("ci_id", ciIds);
		criterias.put("ci_name", ciCodes);
		//criterias.put("kpi_id", kpiId);
		criterias.put("kpi_name", kpiName);
		criterias.put("ci_type_id", ciTypeId);
		if(domainId!=null) {
			criterias.put("domain_id", domainId);	
		}
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		if (StringUtils.isNotEmpty(selectMethod)) {
			selectMethod = selectMethod.trim().toLowerCase();
			RestHighLevelClient client = EsTcpClient.getClient();
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String [] datesArray = tableList.toArray(new String[tableList.size()]);
			SearchRequest searchRequest = new SearchRequest(datesArray);
			// 指定查询的库表
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(EsUtil.builder(criterias));
			searchSourceBuilder.size(0);
			// searchSourceBuilder.sort("cjsj", SortOrder.ASC);
			// 默认平均值
			AbstractAggregationBuilder vals = AggregationBuilders.avg(val).field(val);
			if ("sum".equals(selectMethod)) {
				vals = AggregationBuilders.sum(val).field(val);
			} else if ("max".equals(selectMethod)) {
				vals = AggregationBuilders.max(val).field(val);
			} else if ("min".equals(selectMethod)) {
				vals = AggregationBuilders.min(val).field(val);
			}
			TopHitsAggregationBuilder ciName = AggregationBuilders.topHits(ci_name).size(1);

			DateHistogramAggregationBuilder occTime = AggregationBuilders.dateHistogram(occ_time).field(occ_time)
					.interval(interval).minDocCount(0).extendedBounds(new ExtendedBounds(startTime, endTime));

//            DateHistogramValuesSourceBuilder sendtime = new DateHistogramValuesSourceBuilder("occ_time")
//                         .field("occ_time")
//                         .dateHistogramInterval(DateHistogramInterval.days(1))
//                         .order(SortOrder.DESC)
//                         .missingBucket(false);

			if (isGroByCi != null && isGroByCi) {
				TermsAggregationBuilder ci = AggregationBuilders.terms(ci_id).field(ci_id);
				ci.subAggregation(vals);
				ci.subAggregation(ciName);
				occTime.subAggregation(ci);
			} else {
				occTime.subAggregation(vals);
				occTime.subAggregation(ciName);
			}

			searchSourceBuilder.aggregation(occTime);
			searchRequest.source(searchSourceBuilder);
			try {
				res = timeSearchResponse2List(client.search(searchRequest, RequestOptions.DEFAULT));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			res = new ArrayList<Map<String, Object>>();
			criterias.put("sort", "cjsj");
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String indexes = String.join(",", tableList);
			List<Map<String, Object>> list = EsUtil.search(indexes, criterias);
			if (list != null && list.size() > 0) {
				for (Map<String, Object> map : list) {
					Map<String, Object> one = new HashMap<String, Object>();
					one.put(val, map.get(val));
					one.put(ciId, map.get(ci_id));
					one.put(ciName, map.get(ci_name));
					one.put("cjsj", map.get("cjsj"));
					one.put("occTime", map.get("occ_time"));
					if (map.get(occ_time) != null) {
						try {
							one.put(timeNum, sdf.parse(map.get(occ_time).toString()).getTime());
						} catch (ParseException e) {
						}
					}
					res.add(one);
				}
			}
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		if (res != null && res.size() > 0) {
			for (Map<String, Object> map : res) {
				Object valObject = map.get(val);
				if (valObject != null) {
					String valString = valObject.toString();
					String ciIdStr = "";
					String ciNameStr = "";
					// 循环把res里面的数据中ciId和ciName取出来——start
					for (Map<String, Object> mapRes : res) {
						boolean ciIdFlag = mapRes.containsKey(ciId);
						if (ciIdFlag) {
							ciIdStr = (String) mapRes.get("ciId");
						}
						boolean ciNameFlag = mapRes.containsKey(ciId);
						if (ciNameFlag) {
							ciNameStr = (String) mapRes.get("ciName");
						}
					}
					// 循环把res里面的数据中ciId和ciName取出来——end
					if (StringUtils.isNotEmpty(valString) && !"Infinity".equals(valString)) {
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, valString);
						oneMap.put("ciName", map.get(ciName));
						Object ciIdObject = map.get(ciId);
						if (ciIdObject != null) {
							oneMap.put("ciId", ciIdObject.toString());
						}
						Object timeNumObject = map.get(timeNum);
						if (timeNumObject != null) {
							oneMap.put(timeNum, timeNumObject.toString());
						}
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					} else {
						Object timeNumObject = map.get(timeNum);
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, 0);
						oneMap.put("ciName", ciNameStr);
						oneMap.put("ciId", ciIdStr);
						oneMap.put(timeNum, timeNumObject.toString());
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					}
				}
			}
		}

		return returnList;
	}
	
	/**
	 * 仪表盘---变化图
	 *
	 * @param selectMethod
	 * @param interval
	 * @param startTime
	 * @param endTime
	 * @param ciTypeId
	 * @param kpiId
	 * @param isGroByCi
	 * @param ciIds
	 * @param order
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static List<Map<String, Object>> selectRankChartMapToVariationDiagram(String selectMethod, Long interval,
			String startTime, String endTime, String ciTypeId, String kpiId, Boolean isGroByCi, String ciIds,
			String order,String domainId,String kpiName) {
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		interval = interval == null ? 3600000 : interval;
		List<Map<String, Object>> res = null;
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		criterias.put("ci_id", ciIds);
		//criterias.put("kpi_id", kpiId);
		criterias.put("kpi_name", kpiName);
		criterias.put("ci_type_id", ciTypeId);
		if(domainId!=null) {
			criterias.put("domain_id", domainId);	
		}
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		if (StringUtils.isNotEmpty(selectMethod)) {
			selectMethod = selectMethod.trim().toLowerCase();
			RestHighLevelClient client = EsTcpClient.getClient();
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String [] datesArray = tableList.toArray(new String[tableList.size()]);
			SearchRequest searchRequest = new SearchRequest(datesArray);
			// 指定查询的库表
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(EsUtil.builder(criterias));
			searchSourceBuilder.size(0);
			// searchSourceBuilder.sort("cjsj", SortOrder.ASC);
			// 默认平均值
			AbstractAggregationBuilder vals = AggregationBuilders.avg(val).field(val);
			if ("sum".equals(selectMethod)) {
				vals = AggregationBuilders.sum(val).field(val);
			} else if ("max".equals(selectMethod)) {
				vals = AggregationBuilders.max(val).field(val);
			} else if ("min".equals(selectMethod)) {
				vals = AggregationBuilders.min(val).field(val);
			}
			TopHitsAggregationBuilder ciName = AggregationBuilders.topHits(ci_name).size(1);

			DateHistogramAggregationBuilder occTime = AggregationBuilders.dateHistogram(occ_time).field(occ_time)
					.interval(interval).minDocCount(0).extendedBounds(new ExtendedBounds(startTime, endTime));

//            DateHistogramValuesSourceBuilder sendtime = new DateHistogramValuesSourceBuilder("occ_time")
//                         .field("occ_time")
//                         .dateHistogramInterval(DateHistogramInterval.days(1))
//                         .order(SortOrder.DESC)
//                         .missingBucket(false);

			if (isGroByCi != null && isGroByCi) {
				TermsAggregationBuilder ci = AggregationBuilders.terms(ci_id).field(ci_id);
				ci.subAggregation(vals);
				ci.subAggregation(ciName);
				occTime.subAggregation(ci);
			} else {
				occTime.subAggregation(vals);
				occTime.subAggregation(ciName);
			}

			searchSourceBuilder.aggregation(occTime);
			searchRequest.source(searchSourceBuilder);
			try {
				res = timeSearchResponse2List(client.search(searchRequest, RequestOptions.DEFAULT));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			res = new ArrayList<Map<String, Object>>();
			criterias.put("sort", "cjsj");
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String indexes = String.join(",", tableList);
			List<Map<String, Object>> list = EsUtil.search(indexes, criterias);
			if (list != null && list.size() > 0) {
				for (Map<String, Object> map : list) {
					Map<String, Object> one = new HashMap<String, Object>();
					one.put(val, map.get(val));
					one.put(ciId, map.get(ci_id));
					one.put(ciName, map.get(ci_name));
					one.put("cjsj", map.get("cjsj"));
					one.put("occTime", map.get("occ_time"));
					if (map.get(occ_time) != null) {
						try {
							one.put(timeNum, sdf.parse(map.get(occ_time).toString()).getTime());
						} catch (ParseException e) {
						}
					}
					res.add(one);
				}
			}
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		if (res != null && res.size() > 0) {
			for (Map<String, Object> map : res) {
				Object valObject = map.get(val);
				if (valObject != null) {
					String valString = valObject.toString();
					String ciIdStr = "";
					String ciNameStr = "";
					// 循环把res里面的数据中ciId和ciName取出来——start
//					for (Map<String, Object> mapRes : res) {
//						boolean ciIdFlag = mapRes.containsKey(ciId);
//						if (ciIdFlag) {
//							ciIdStr = (String) mapRes.get("ciId");
//						}
//						boolean ciNameFlag = mapRes.containsKey(ciId);
//						if (ciNameFlag) {
//							ciNameStr = (String) mapRes.get("ciName");
//						}
//					}
					// 循环把res里面的数据中ciId和ciName取出来——end
					if (StringUtils.isNotEmpty(valString) && !"Infinity".equals(valString)) {
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, valString);
						oneMap.put("ciName", map.get(ciName));
						Object ciIdObject = map.get(ciId);
						if (ciIdObject != null) {
							oneMap.put("ciId", ciIdObject.toString());
						}
						Object timeNumObject = map.get(timeNum);
						if (timeNumObject != null) {
							oneMap.put(timeNum, timeNumObject.toString());
						}
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					} else {
						Object timeNumObject = map.get(timeNum);
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, 0);
						oneMap.put("ciName", ciNameStr);
						oneMap.put("ciId", ciIdStr);
						oneMap.put(timeNum, timeNumObject.toString());
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					}
				}
			}
		}

		return returnList;
	}
	
	/**
	 * 仪表盘---获取分布图
	 *
	 * @param selectMethod
	 * @param interval
	 * @param startTime
	 * @param endTime
	 * @param ciTypeId
	 * @param kpiId
	 * @param isGroByCi
	 * @param ciIds
	 * @param order
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static List<Map<String, Object>> selectDistributeChartMap(String selectMethod, Long interval,
			String startTime, String endTime, String ciTypeId, String kpiId, Boolean isGroByCi, String ciIds,
			String order,String domainId,String ciCodes,String kpiName) {
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		interval = interval == null ? 3600000 : interval;
		List<Map<String, Object>> res = null;
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		//criterias.put("ci_id", ciIds);
		criterias.put("ci_name", ciCodes);
		//criterias.put("kpi_id", kpiId);
		criterias.put("kpi_name", kpiName);
		criterias.put("ci_type_id", ciTypeId);
		if(domainId!=null) {
			criterias.put("domain_id", domainId);	
		}
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		if (StringUtils.isNotEmpty(selectMethod)) {
			selectMethod = selectMethod.trim().toLowerCase();
			RestHighLevelClient client = EsTcpClient.getClient();
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String [] datesArray = tableList.toArray(new String[tableList.size()]);
			SearchRequest searchRequest = new SearchRequest(datesArray);
			// 指定查询的库表
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(EsUtil.builder(criterias));
			searchSourceBuilder.size(0);
			// searchSourceBuilder.sort("cjsj", SortOrder.ASC);
			// 默认平均值
			AbstractAggregationBuilder vals = AggregationBuilders.avg(val).field(val);
			if ("sum".equals(selectMethod)) {
				vals = AggregationBuilders.sum(val).field(val);
			} else if ("max".equals(selectMethod)) {
				vals = AggregationBuilders.max(val).field(val);
			} else if ("min".equals(selectMethod)) {
				vals = AggregationBuilders.min(val).field(val);
			}
			TopHitsAggregationBuilder ciName = AggregationBuilders.topHits(ci_name).size(1);

			DateHistogramAggregationBuilder occTime = AggregationBuilders.dateHistogram(occ_time).field(occ_time)
					.interval(interval).minDocCount(0).extendedBounds(new ExtendedBounds(startTime, endTime));

//            DateHistogramValuesSourceBuilder sendtime = new DateHistogramValuesSourceBuilder("occ_time")
//                         .field("occ_time")
//                         .dateHistogramInterval(DateHistogramInterval.days(1))
//                         .order(SortOrder.DESC)
//                         .missingBucket(false);

			if (isGroByCi != null && isGroByCi) {
				TermsAggregationBuilder ci = AggregationBuilders.terms(ci_id).field(ci_id);
				ci.subAggregation(vals);
				ci.subAggregation(ciName);
				occTime.subAggregation(ci);
			} else {
				occTime.subAggregation(vals);
				occTime.subAggregation(ciName);
			}

			searchSourceBuilder.aggregation(occTime);
			searchRequest.source(searchSourceBuilder);
			try {
				res = timeSearchResponse2List(client.search(searchRequest, RequestOptions.DEFAULT));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			res = new ArrayList<Map<String, Object>>();
			criterias.put("sort", "cjsj");
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String indexes = String.join(",", tableList);
			List<Map<String, Object>> list = EsUtil.search(indexes, criterias);
			if (list != null && list.size() > 0) {
				for (Map<String, Object> map : list) {
					Map<String, Object> one = new HashMap<String, Object>();
					one.put(val, map.get(val));
					one.put(ciId, map.get(ci_id));
					one.put(ciName, map.get(ci_name));
					one.put("cjsj", map.get("cjsj"));
					one.put("occTime", map.get("occ_time"));
					if (map.get(occ_time) != null) {
						try {
							one.put(timeNum, sdf.parse(map.get(occ_time).toString()).getTime());
						} catch (ParseException e) {
						}
					}
					res.add(one);
				}
			}
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		if (res != null && res.size() > 0) {
			for (Map<String, Object> map : res) {
				Object valObject = map.get(val);
				if (valObject != null) {
					String valString = valObject.toString();
					String ciIdStr = "";
					String ciNameStr = "";
					// 循环把res里面的数据中ciId和ciName取出来——start
					for (Map<String, Object> mapRes : res) {
						boolean ciIdFlag = mapRes.containsKey(ciId);
						if (ciIdFlag) {
							ciIdStr = (String) mapRes.get("ciId");
						}
						boolean ciNameFlag = mapRes.containsKey(ciId);
						if (ciNameFlag) {
							ciNameStr = (String) mapRes.get("ciName");
						}
					}
					// 循环把res里面的数据中ciId和ciName取出来——end
					if (StringUtils.isNotEmpty(valString) && !"Infinity".equals(valString)) {
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, valString);
						oneMap.put("ciName", map.get(ciName));
						Object ciIdObject = map.get(ciId);
						if (ciIdObject != null) {
							oneMap.put("ciId", ciIdObject.toString());
						}
						Object timeNumObject = map.get(timeNum);
						if (timeNumObject != null) {
							oneMap.put(timeNum, timeNumObject.toString());
						}
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					} else {
						Object timeNumObject = map.get(timeNum);
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, 0);
						oneMap.put("ciName", ciNameStr);
						oneMap.put("ciId", ciIdStr);
						oneMap.put(timeNum, timeNumObject.toString());
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					}
				}
			}
		}

		return returnList;
	}
	
	/**
	 * 全部故障关联指标
	 *
	 * @param selectMethod
	 * @param interval
	 * @param startTime
	 * @param endTime
	 * @param ciTypeId
	 * @param kpiId
	 * @param isGroByCi
	 * @param ciIds
	 * @param order
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static List<Map<String, Object>> selectAllFaultsRelatedIndicators(String selectMethod, Long interval,
			String startTime, String endTime, String ciTypeId, String kpiId, Boolean isGroByCi, String ciIds,
			String order,String domainId,String ciCode,String kpiName) {
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		interval = interval == null ? 1000 : interval;
		List<Map<String, Object>> res = null;
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		criterias.put("ci_name", ciCode);
		//criterias.put("kpi_id", kpiId);
		criterias.put("kpi_name", kpiName);
		//criterias.put("ci_type_id", ciTypeId);
		if(domainId!=null) {
			criterias.put("domain_id", domainId);	
		}
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		if (StringUtils.isNotEmpty(selectMethod)) {
			selectMethod = selectMethod.trim().toLowerCase();
			RestHighLevelClient client = EsTcpClient.getClient();
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String [] datesArray = tableList.toArray(new String[tableList.size()]);
			SearchRequest searchRequest = new SearchRequest(datesArray);
			// 指定查询的库表
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(EsUtil.builder(criterias));
			searchSourceBuilder.size(0);
			// searchSourceBuilder.sort("cjsj", SortOrder.ASC);
			// 默认平均值
			AbstractAggregationBuilder vals = AggregationBuilders.avg(val).field(val);
			if ("sum".equals(selectMethod)) {
				vals = AggregationBuilders.sum(val).field(val);
			} else if ("max".equals(selectMethod)) {
				vals = AggregationBuilders.max(val).field(val);
			} else if ("min".equals(selectMethod)) {
				vals = AggregationBuilders.min(val).field(val);
			}
			TopHitsAggregationBuilder ciName = AggregationBuilders.topHits(ci_name).size(1);

			DateHistogramAggregationBuilder occTime = AggregationBuilders.dateHistogram(occ_time).field(occ_time)
					.interval(interval).minDocCount(0).extendedBounds(new ExtendedBounds(startTime, endTime));

//            DateHistogramValuesSourceBuilder sendtime = new DateHistogramValuesSourceBuilder("occ_time")
//                         .field("occ_time")
//                         .dateHistogramInterval(DateHistogramInterval.days(1))
//                         .order(SortOrder.DESC)
//                         .missingBucket(false);

			if (isGroByCi != null && isGroByCi) {
				TermsAggregationBuilder ci = AggregationBuilders.terms(ci_name).field(ci_name);
				ci.subAggregation(vals);
				ci.subAggregation(ciName);
				occTime.subAggregation(ci);
			} else {
				occTime.subAggregation(vals);
				occTime.subAggregation(ciName);
			}

			searchSourceBuilder.aggregation(occTime);
			searchRequest.source(searchSourceBuilder);
			try {
				res = timeSearchResponse2List(client.search(searchRequest, RequestOptions.DEFAULT));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			res = new ArrayList<Map<String, Object>>();
			criterias.put("sort", "cjsj");
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String indexes = String.join(",", tableList);
			List<Map<String, Object>> list = EsUtil.search(indexes, criterias);
			if (list != null && list.size() > 0) {
				for (Map<String, Object> map : list) {
					Map<String, Object> one = new HashMap<String, Object>();
					one.put(val, map.get(val));
					one.put(ciId, map.get(ci_id));
					one.put(ciName, map.get(ci_name));
					one.put("cjsj", map.get("cjsj"));
					one.put("occTime", map.get("occ_time"));
					if (map.get(occ_time) != null) {
						try {
							one.put(timeNum, sdf.parse(map.get(occ_time).toString()).getTime());
						} catch (ParseException e) {
						}
					}
					res.add(one);
				}
			}
		}
		if(StringUtils.isEmpty(selectMethod)) {
			return res;
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		if (res != null && res.size() > 0) {
			for (Map<String, Object> map : res) {
				Object valObject = map.get(val);
				if (valObject != null) {
					String valString = valObject.toString();
					String ciIdStr = "";
					String ciNameStr = "";
					// 循环把res里面的数据中ciId和ciName取出来——start
					for (Map<String, Object> mapRes : res) {
						boolean ciIdFlag = mapRes.containsKey(ciId);
						if (ciIdFlag) {
							ciIdStr = (String) mapRes.get("ciId");
						}
						boolean ciNameFlag = mapRes.containsKey(ciId);
						if (ciNameFlag) {
							ciNameStr = (String) mapRes.get("ciName");
						}
					}
					// 循环把res里面的数据中ciId和ciName取出来——end
					if (StringUtils.isNotEmpty(valString) && !"Infinity".equals(valString)) {
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, valString);
						oneMap.put("ciName", map.get(ciName));
						Object ciIdObject = map.get(ciId);
						if (ciIdObject != null) {
							oneMap.put("ciId", ciIdObject.toString());
						}
						Object timeNumObject = map.get(timeNum);
						if (timeNumObject != null) {
							oneMap.put(timeNum, timeNumObject.toString());
						}
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					} else {
						Object timeNumObject = map.get(timeNum);
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, 0);
						oneMap.put("ciName", ciNameStr);
						oneMap.put("ciId", ciIdStr);
						oneMap.put(timeNum, timeNumObject.toString());
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					}
				}
			}
		}

		return returnList;
	}
    //性能查看使用
	public static List<Map<String, Object>> getPerformanceViewLineChart(String selectMethod, Long interval,
			String startTime, String endTime, String ciTypeId, String kpiId, Boolean isGroByCi, String ciIds,
			String order,String domainId,String ciCodes,String kpiName) {
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		interval = interval == null ? 1000 : interval;
		List<Map<String, Object>> res = null;
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		//criterias.put("ci_id", ciIds);
		criterias.put("ci_name", ciCodes);
		//criterias.put("kpi_id", kpiId);
		criterias.put("kpi_name", kpiName);
		//criterias.put("ci_type_id", ciTypeId);
		if(domainId!=null) {
			criterias.put("domain_id", domainId);	
		}
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		
		if (StringUtils.isNotEmpty(selectMethod)) {
			selectMethod = selectMethod.trim().toLowerCase();
			RestHighLevelClient client = EsTcpClient.getClient();
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
		    String [] datesArray = tableList.toArray(new String[tableList.size()]);
			SearchRequest searchRequest = new SearchRequest(datesArray);
            // 指定查询的库表
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(EsUtil.builder(criterias));
			searchSourceBuilder.size(0);
            //searchSourceBuilder.sort("cjsj", SortOrder.ASC);
            // 默认平均值
			AbstractAggregationBuilder vals = AggregationBuilders.avg(val).field(val);
			if ("sum".equals(selectMethod)) {
				vals = AggregationBuilders.sum(val).field(val);
			} else if ("max".equals(selectMethod)) {
				vals = AggregationBuilders.max(val).field(val);
			} else if ("min".equals(selectMethod)) {
				vals = AggregationBuilders.min(val).field(val);
			}
			TopHitsAggregationBuilder ciName = AggregationBuilders.topHits(ci_name).size(1);

			DateHistogramAggregationBuilder occTime = AggregationBuilders.dateHistogram(occ_time).field(occ_time)
					.interval(interval).minDocCount(0).extendedBounds(new ExtendedBounds(startTime, endTime));

           //DateHistogramValuesSourceBuilder sendtime = new DateHistogramValuesSourceBuilder("occ_time")
           //.field("occ_time")
           //.dateHistogramInterval(DateHistogramInterval.days(1))
           //.order(SortOrder.DESC)
           //.missingBucket(false);

			if (isGroByCi != null && isGroByCi) {
				TermsAggregationBuilder ci = AggregationBuilders.terms(ci_id).field(ci_id);
				ci.subAggregation(vals);
				ci.subAggregation(ciName);
				occTime.subAggregation(ci);
			} else {
				occTime.subAggregation(vals);
				occTime.subAggregation(ciName);
			}

			searchSourceBuilder.aggregation(occTime);
			searchRequest.source(searchSourceBuilder);
			try {
				res = timeSearchResponse2List(client.search(searchRequest, RequestOptions.DEFAULT));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {			
				res = new ArrayList<Map<String, Object>>();
				criterias.put("sort", "cjsj");
				List<String> tableList=new ArrayList<String>();
				if(tables!=null && tables.size()>0) {
					for(String tableDate:tables) {
						boolean flag=EsUtil.existIndex(tableDate);
						if(flag) {
							tableList.add(tableDate);
						}
					}
				}
				String indexes = String.join(",", tableList);
				List<Map<String, Object>> list = EsUtil.search(indexes, criterias);
				if (list != null && list.size() > 0) {
					for (Map<String, Object> map : list) {
						Map<String, Object> one = new HashMap<String, Object>();
						one.put(val, map.get(val));
						one.put(ciId, map.get(ci_id));
						one.put(ciName, map.get(ci_name));
						one.put("cjsj", map.get("cjsj"));
						one.put("occTime", map.get("occ_time"));
						if (map.get(occ_time) != null) {
							try {
								one.put(timeNum, sdf.parse(map.get(occ_time).toString()).getTime());
							} catch (ParseException e) {
							}
						}
						res.add(one);
					}
				}
		}
		if(StringUtils.isEmpty(selectMethod)) {
			return res;
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		if (res != null && res.size() > 0) {
			for (Map<String, Object> map : res) {
				Object valObject = map.get(val);
				if (valObject != null) {
					String valString = valObject.toString();
					String ciIdStr = "";
					String ciNameStr = "";
					String occTime="";
					String cjsj="";
                    //循环把res里面的数据中ciId和ciName取出来——start
					for (Map<String, Object> mapRes : res) {
						boolean ciIdFlag = mapRes.containsKey(ciId);
						if (ciIdFlag) {
							ciIdStr = (String) mapRes.get("ciId");
						}
						boolean ciNameFlag = mapRes.containsKey("ciName");
						if (ciNameFlag) {
							ciNameStr = (String) mapRes.get("ciName");
						}
						boolean occTimeFlag = mapRes.containsKey("occTime");
						if (occTimeFlag) {
							occTime = (String) mapRes.get("occTime");
						}
						boolean cjsjFlag = mapRes.containsKey("cjsj");
						if (cjsjFlag) {
							cjsj = (String) mapRes.get("cjsj");
						}
					}
					map.put("ciId", ciIdStr);
					map.put("ciName", ciNameStr);
					map.put("occTime", occTime);
					map.put("cjsj", cjsj);
                    //循环把res里面的数据中ciId和ciName取出来——end
					if (StringUtils.isNotEmpty(valString) && !"Infinity".equals(valString)) {
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, valString);
						oneMap.put("ciName", map.get(ciName));
						Object ciIdObject = map.get(ciId);
						if (ciIdObject != null) {
							oneMap.put("ciId", ciIdObject.toString());
						}
						Object timeNumObject = map.get(timeNum);
						if (timeNumObject != null) {
							oneMap.put(timeNum, timeNumObject.toString());
						}
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					} else {
						Object timeNumObject = map.get(timeNum);
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, 0);
						oneMap.put("ciName", ciNameStr);
						oneMap.put("ciId", ciIdStr);
						oneMap.put(timeNum, timeNumObject.toString());
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					}
				}
			}
		}

		return returnList;
	}
	
	    //场景可视化指标组件
		public static List<Map<String, Object>> getScenarioIndicatorComponent(String selectMethod, Long interval,
				String startTime, String endTime, String ciTypeId, String kpiId, Boolean isGroByCi, String ciIds,
				String order,String domainId,String ciCodes,String kpiName) {
			List<String> tables=new ArrayList<String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			interval = interval == null ? 1000 : interval;
			List<Map<String, Object>> res = null;
			Map<String, Object> criterias = new HashMap<String, Object>();
			criterias.put(">=occ_time", startTime);
			criterias.put("<=occ_time", endTime);
			//criterias.put("ci_id", ciIds);
			criterias.put("ci_name", ciCodes);
			//criterias.put("kpi_id", kpiId);
			criterias.put("kpi_name", kpiName);
			criterias.put("ci_type_id", ciTypeId);
			if(domainId!=null) {
				criterias.put("domain_id", domainId);	
			}
			try {
				//根据startTime和endTime截取日期字段进行ES跨表查询
				long nd = 1000 * 24 * 60 * 60;			
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				Date startTimeDate=sdf1.parse(startTime);
				Date endTimeDate=sdf1.parse(endTime);
				String startTimeStr=sdf2.format(startTimeDate);
				String endTimeStr=sdf2.format(endTimeDate);
				if(startTimeStr.equals(endTimeStr)) {
					String table=iom_pmv_performance+"-"+startTimeStr;
					tables.add(table);
				}else {
					long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
					// 计算差多少天
				    long day = diff / nd;
				    int dayInt=(int)day;
				    for(int i=1;i<=dayInt;i++) {
				    	Calendar calendar = Calendar.getInstance();  
			            calendar.setTime(startTimeDate);  
			            calendar.add(Calendar.DAY_OF_MONTH, +i);  
			            Date date = calendar.getTime();
			            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
			            String time=sdf3.format(date);
			            if(time.equals(endTimeStr)) {
			            	if(i==1) {
			            		tables.add(iom_pmv_performance+"-"+startTimeStr);
				            	tables.add(iom_pmv_performance+"-"+endTimeStr);
				            	break;
			            	}else {
			            		tables.add(iom_pmv_performance+"-"+endTimeStr);
			            		break;
			            	}			            	
			            }else {
			            	if(i==1) {
			            		tables.add(iom_pmv_performance+"-"+startTimeStr);
				            	tables.add(iom_pmv_performance+"-"+time);
			            	}else {
				            	tables.add(iom_pmv_performance+"-"+time);
			            	}
			            	
			            }
				    }
				}
			} catch (Exception e) {
	           e.printStackTrace();
			}
			if (StringUtils.isNotEmpty(selectMethod)) {
				selectMethod = selectMethod.trim().toLowerCase();
				RestHighLevelClient client = EsTcpClient.getClient();
				List<String> tableList=new ArrayList<String>();
				if(tables!=null && tables.size()>0) {
					for(String tableDate:tables) {
						boolean flag=EsUtil.existIndex(tableDate);
						if(flag) {
							tableList.add(tableDate);
						}
					}
				}
				String [] datesArray = tableList.toArray(new String[tableList.size()]);
				SearchRequest searchRequest = new SearchRequest(datesArray);
	            // 指定查询的库表
				SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
				searchSourceBuilder.query(EsUtil.builder(criterias));
				searchSourceBuilder.size(0);
	            //searchSourceBuilder.sort("cjsj", SortOrder.ASC);
	            // 默认平均值
				AbstractAggregationBuilder vals = AggregationBuilders.avg(val).field(val);
				if ("sum".equals(selectMethod)) {
					vals = AggregationBuilders.sum(val).field(val);
				} else if ("max".equals(selectMethod)) {
					vals = AggregationBuilders.max(val).field(val);
				} else if ("min".equals(selectMethod)) {
					vals = AggregationBuilders.min(val).field(val);
				}
				TopHitsAggregationBuilder ciName = AggregationBuilders.topHits(ci_name).size(1);

				DateHistogramAggregationBuilder occTime = AggregationBuilders.dateHistogram(occ_time).field(occ_time)
						.interval(interval).minDocCount(0).extendedBounds(new ExtendedBounds(startTime, endTime));

	           //DateHistogramValuesSourceBuilder sendtime = new DateHistogramValuesSourceBuilder("occ_time")
	           //.field("occ_time")
	           //.dateHistogramInterval(DateHistogramInterval.days(1))
	           //.order(SortOrder.DESC)
	           //.missingBucket(false);

				if (isGroByCi != null && isGroByCi) {
					TermsAggregationBuilder ci = AggregationBuilders.terms(ci_id).field(ci_id);
					ci.subAggregation(vals);
					ci.subAggregation(ciName);
					occTime.subAggregation(ci);
				} else {
					occTime.subAggregation(vals);
					occTime.subAggregation(ciName);
				}

				searchSourceBuilder.aggregation(occTime);
				searchRequest.source(searchSourceBuilder);
				try {
					res = timeSearchResponse2List(client.search(searchRequest, RequestOptions.DEFAULT));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				res = new ArrayList<Map<String, Object>>();
				criterias.put("sort", "cjsj");
				List<String> tableList=new ArrayList<String>();
				if(tables!=null && tables.size()>0) {
					for(String tableDate:tables) {
						boolean flag=EsUtil.existIndex(tableDate);
						if(flag) {
							tableList.add(tableDate);
						}
					}
				}
				String indexes = String.join(",", tableList);
				List<Map<String, Object>> list = EsUtil.search(indexes, criterias);
				if (list != null && list.size() > 0) {
					for (Map<String, Object> map : list) {
						Map<String, Object> one = new HashMap<String, Object>();
						one.put(val, map.get(val));
						one.put(ciId, map.get(ci_id));
						one.put(ciName, map.get(ci_name));
						one.put("cjsj", map.get("cjsj"));
						one.put("occTime", map.get("occ_time"));
						if (map.get(occ_time) != null) {
							try {
								one.put(timeNum, sdf.parse(map.get(occ_time).toString()).getTime());
							} catch (ParseException e) {
							}
						}
						res.add(one);
					}
				}
			}
			if(StringUtils.isEmpty(selectMethod)) {
				return res;
			}
			List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
			if (res != null && res.size() > 0) {
				for (Map<String, Object> map : res) {
					Object valObject = map.get(val);
					if (valObject != null) {
						String valString = valObject.toString();
						String ciIdStr = "";
						String ciNameStr = "";
	                    //循环把res里面的数据中ciId和ciName取出来——start
						for (Map<String, Object> mapRes : res) {
							boolean ciIdFlag = mapRes.containsKey(ciId);
							if (ciIdFlag) {
								ciIdStr = (String) mapRes.get("ciId");
							}
							boolean ciNameFlag = mapRes.containsKey(ciId);
							if (ciNameFlag) {
								ciNameStr = (String) mapRes.get("ciName");
							}
						}
	                    //循环把res里面的数据中ciId和ciName取出来——end
						if (StringUtils.isNotEmpty(valString) && !"Infinity".equals(valString)) {
							Map<String, Object> oneMap = new HashMap<String, Object>();
							oneMap.put(val, valString);
							oneMap.put("ciName", map.get(ciName));
							Object ciIdObject = map.get(ciId);
							if (ciIdObject != null) {
								oneMap.put("ciId", ciIdObject.toString());
							}
							Object timeNumObject = map.get(timeNum);
							if (timeNumObject != null) {
								oneMap.put(timeNum, timeNumObject.toString());
							}
							oneMap.put("cjsj", map.get("cjsj"));
							oneMap.put("occTime", map.get("occTime"));
							returnList.add(oneMap);
						} else {
							Object timeNumObject = map.get(timeNum);
							Map<String, Object> oneMap = new HashMap<String, Object>();
							oneMap.put(val, 0);
							oneMap.put("ciName", ciNameStr);
							oneMap.put("ciId", ciIdStr);
							oneMap.put(timeNum, timeNumObject.toString());
							oneMap.put("cjsj", map.get("cjsj"));
							oneMap.put("occTime", map.get("occTime"));
							returnList.add(oneMap);
						}
					}
				}
			}

			return returnList;
		}
		
		        //仪表盘折线图
				public static List<Map<String, Object>> getDashboardLineChart(String selectMethod, Long interval,
						String startTime, String endTime, String ciTypeId, String kpiId, Boolean isGroByCi, String ciIds,
						String order,String domainId,String ciCodes,String kpiName) {
					List<String> tables=new ArrayList<String>();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					interval = interval == null ? 1000 : interval;
					List<Map<String, Object>> res = null;
					Map<String, Object> criterias = new HashMap<String, Object>();
					criterias.put(">=occ_time", startTime);
					criterias.put("<=occ_time", endTime);
					//criterias.put("ci_id", ciIds);
					criterias.put("ci_name", ciCodes);
					//criterias.put("kpi_id", kpiId);
					criterias.put("kpi_name", kpiName);
					//criterias.put("ci_type_id", ciTypeId);
					if(domainId!=null) {
						criterias.put("domain_id", domainId);	
					}
					try {
						//根据startTime和endTime截取日期字段进行ES跨表查询
						long nd = 1000 * 24 * 60 * 60;			
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
						Date startTimeDate=sdf1.parse(startTime);
						Date endTimeDate=sdf1.parse(endTime);
						String startTimeStr=sdf2.format(startTimeDate);
						String endTimeStr=sdf2.format(endTimeDate);
						if(startTimeStr.equals(endTimeStr)) {
							String table=iom_pmv_performance+"-"+startTimeStr;
							tables.add(table);
						}else {
							long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
							// 计算差多少天
						    long day = diff / nd;
						    int dayInt=(int)day;
						    for(int i=1;i<=dayInt;i++) {
						    	Calendar calendar = Calendar.getInstance();  
					            calendar.setTime(startTimeDate);  
					            calendar.add(Calendar.DAY_OF_MONTH, +i);  
					            Date date = calendar.getTime();
					            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
					            String time=sdf3.format(date);
					            if(time.equals(endTimeStr)) {
					            	if(i==1) {
					            		tables.add(iom_pmv_performance+"-"+startTimeStr);
						            	tables.add(iom_pmv_performance+"-"+endTimeStr);
						            	break;
					            	}else {
					            		tables.add(iom_pmv_performance+"-"+endTimeStr);
					            		break;
					            	}			            	
					            }else {
					            	if(i==1) {
					            		tables.add(iom_pmv_performance+"-"+startTimeStr);
						            	tables.add(iom_pmv_performance+"-"+time);
					            	}else {
						            	tables.add(iom_pmv_performance+"-"+time);
					            	}
					            	
					            }
						    }
						}
					} catch (Exception e) {
			           e.printStackTrace();
					}
					if (StringUtils.isNotEmpty(selectMethod)) {
						selectMethod = selectMethod.trim().toLowerCase();
						RestHighLevelClient client = EsTcpClient.getClient();
						List<String> tableList=new ArrayList<String>();
						if(tables!=null && tables.size()>0) {
							for(String tableDate:tables) {
								boolean flag=EsUtil.existIndex(tableDate);
								if(flag) {
									tableList.add(tableDate);
								}
							}
						}
						String [] datesArray = tableList.toArray(new String[tableList.size()]);
						SearchRequest searchRequest = new SearchRequest(datesArray);
			            // 指定查询的库表
						SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
						searchSourceBuilder.query(EsUtil.builder(criterias));
						searchSourceBuilder.size(0);
			            //searchSourceBuilder.sort("cjsj", SortOrder.ASC);
			            // 默认平均值
						AbstractAggregationBuilder vals = AggregationBuilders.avg(val).field(val);
						if ("sum".equals(selectMethod)) {
							vals = AggregationBuilders.sum(val).field(val);
						} else if ("max".equals(selectMethod)) {
							vals = AggregationBuilders.max(val).field(val);
						} else if ("min".equals(selectMethod)) {
							vals = AggregationBuilders.min(val).field(val);
						}
						TopHitsAggregationBuilder ciName = AggregationBuilders.topHits(ci_name).size(1);

						DateHistogramAggregationBuilder occTime = AggregationBuilders.dateHistogram(occ_time).field(occ_time)
								.interval(interval).minDocCount(0).extendedBounds(new ExtendedBounds(startTime, endTime));

			           //DateHistogramValuesSourceBuilder sendtime = new DateHistogramValuesSourceBuilder("occ_time")
			           //.field("occ_time")
			           //.dateHistogramInterval(DateHistogramInterval.days(1))
			           //.order(SortOrder.DESC)
			           //.missingBucket(false);

						if (isGroByCi != null && isGroByCi) {
							TermsAggregationBuilder ci = AggregationBuilders.terms(ci_id).field(ci_id);
							ci.subAggregation(vals);
							ci.subAggregation(ciName);
							occTime.subAggregation(ci);
						} else {
							occTime.subAggregation(vals);
							occTime.subAggregation(ciName);
						}

						searchSourceBuilder.aggregation(occTime);
						searchRequest.source(searchSourceBuilder);
						try {
							res = timeSearchResponse2List(client.search(searchRequest, RequestOptions.DEFAULT));
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						res = new ArrayList<Map<String, Object>>();
						criterias.put("sort", "cjsj");
						List<String> tableList=new ArrayList<String>();
						if(tables!=null && tables.size()>0) {
							for(String tableDate:tables) {
								boolean flag=EsUtil.existIndex(tableDate);
								if(flag) {
									tableList.add(tableDate);
								}
							}
						}
						String indexes = String.join(",", tableList);
						List<Map<String, Object>> list = EsUtil.search(indexes, criterias);
						if (list != null && list.size() > 0) {
							for (Map<String, Object> map : list) {
								Map<String, Object> one = new HashMap<String, Object>();
								one.put(val, map.get(val));
								one.put(ciId, map.get(ci_id));
								one.put(ciName, map.get(ci_name));
								one.put("cjsj", map.get("cjsj"));
								one.put("occTime", map.get("occ_time"));
								if (map.get(occ_time) != null) {
									try {
										one.put(timeNum, sdf.parse(map.get(occ_time).toString()).getTime());
									} catch (ParseException e) {
									}
								}
								res.add(one);
							}
						}
					}
					if(StringUtils.isEmpty(selectMethod)) {
						return res;
					}
					List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
					if (res != null && res.size() > 0) {
						for (Map<String, Object> map : res) {
							Object valObject = map.get(val);
							if (valObject != null) {
								String valString = valObject.toString();
								String ciIdStr = "";
								String ciNameStr = "";
								String occTime="";
								String cjsj="";
			                    //循环把res里面的数据中ciId和ciName取出来——start
								for (Map<String, Object> mapRes : res) {
									boolean ciIdFlag = mapRes.containsKey(ciId);
									if (ciIdFlag) {
										ciIdStr = (String) mapRes.get("ciId");
									}
									boolean ciNameFlag = mapRes.containsKey("ciName");
									if (ciNameFlag) {
										ciNameStr = (String) mapRes.get("ciName");
									}
									boolean occTimeFlag = mapRes.containsKey("occTime");
									if (occTimeFlag) {
										occTime = (String) mapRes.get("occTime");
									}
									boolean cjsjFlag = mapRes.containsKey("cjsj");
									if (cjsjFlag) {
										cjsj = (String) mapRes.get("cjsj");
									}
								}
								map.put("ciId", ciIdStr);
								map.put("ciName", ciNameStr);
								map.put("occTime", occTime);
								map.put("cjsj", cjsj);
			                    //循环把res里面的数据中ciId和ciName取出来——end
								if (StringUtils.isNotEmpty(valString) && !"Infinity".equals(valString)) {
									Map<String, Object> oneMap = new HashMap<String, Object>();
									oneMap.put(val, valString);
									oneMap.put("ciName", map.get(ciName));
									Object ciIdObject = map.get(ciId);
									if (ciIdObject != null) {
										oneMap.put("ciId", ciIdObject.toString());
									}
									Object timeNumObject = map.get(timeNum);
									if (timeNumObject != null) {
										oneMap.put(timeNum, timeNumObject.toString());
									}
									oneMap.put("cjsj", map.get("cjsj"));
									oneMap.put("occTime", map.get("occTime"));
									returnList.add(oneMap);
								} else {
									Object timeNumObject = map.get(timeNum);
									Map<String, Object> oneMap = new HashMap<String, Object>();
									oneMap.put(val, 0);
									oneMap.put("ciName", ciNameStr);
									oneMap.put("ciId", ciIdStr);
									oneMap.put(timeNum, timeNumObject.toString());
									oneMap.put("cjsj", map.get("cjsj"));
									oneMap.put("occTime", map.get("occTime"));
									returnList.add(oneMap);
								}
							}
						}
					}

					return returnList;
				}
				

	/**
	 * 执行搜索---(仅供配置查询性能查询使用)
	 *
	 * @param selectMethod
	 * @param interval
	 * @param startTime
	 * @param endTime
	 * @param ciTypeId
	 * @param kpiId
	 * @param isGroByCi
	 * @param ciIds
	 * @param order
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static List<Map<String, Object>> selectChartDataGroupByCiToConfigurationQuery(String selectMethod,
			Long interval, String startTime, String endTime, String ciTypeId, String kpiId, Boolean isGroByCi,
			String ciIds, String order,String domainId,String ciCode,String kpiName) {
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		interval = interval == null ? 1000 : interval;
		List<Map<String, Object>> res = null;
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		criterias.put("ci_name", ciCode);
		criterias.put("kpi_name", kpiName);
		//criterias.put("kpi_id", kpiId);
		//criterias.put("ci_type_id", ciTypeId);
		if(domainId!=null) {
			criterias.put("domain_id", domainId);	
		}
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		if (StringUtils.isNotEmpty(selectMethod)) {
			selectMethod = selectMethod.trim().toLowerCase();
			RestHighLevelClient client = EsTcpClient.getClient();
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String [] datesArray = tableList.toArray(new String[tableList.size()]);
			SearchRequest searchRequest = new SearchRequest(datesArray);
			// 指定查询的库表
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(EsUtil.builder(criterias));
			searchSourceBuilder.size(0);
			// searchSourceBuilder.sort("cjsj", SortOrder.ASC);
			// 默认平均值
			AbstractAggregationBuilder vals = AggregationBuilders.avg(val).field(val);
			if ("sum".equals(selectMethod)) {
				vals = AggregationBuilders.sum(val).field(val);
			} else if ("max".equals(selectMethod)) {
				vals = AggregationBuilders.max(val).field(val);
			} else if ("min".equals(selectMethod)) {
				vals = AggregationBuilders.min(val).field(val);
			}
			TopHitsAggregationBuilder ciName = AggregationBuilders.topHits(ci_name).size(1);

			DateHistogramAggregationBuilder occTime = AggregationBuilders.dateHistogram(occ_time).field(occ_time)
					.interval(interval).minDocCount(0).extendedBounds(new ExtendedBounds(startTime, endTime));

//            DateHistogramValuesSourceBuilder sendtime = new DateHistogramValuesSourceBuilder("occ_time")
//                         .field("occ_time")
//                         .dateHistogramInterval(DateHistogramInterval.days(1))
//                         .order(SortOrder.DESC)
//                         .missingBucket(false);

			if (isGroByCi != null && isGroByCi) {
				TermsAggregationBuilder ci = AggregationBuilders.terms(ci_name).field(ci_name);
				ci.subAggregation(vals);
				ci.subAggregation(ciName);
				occTime.subAggregation(ci);
			} else {
				occTime.subAggregation(vals);
				occTime.subAggregation(ciName);
			}

			searchSourceBuilder.aggregation(occTime);
			searchRequest.source(searchSourceBuilder);
			try {
				res = timeSearchResponse2List(client.search(searchRequest, RequestOptions.DEFAULT));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			res = new ArrayList<Map<String, Object>>();
			criterias.put("sort", "cjsj");
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String indexes = String.join(",", tableList);
			List<Map<String, Object>> list = EsUtil.search(indexes, criterias);
			if (list != null && list.size() > 0) {
				for (Map<String, Object> map : list) {
					Map<String, Object> one = new HashMap<String, Object>();
					one.put(val, map.get(val));
					one.put(ciId, map.get(ci_id));
					one.put(ciName, map.get(ci_name));
					one.put("cjsj", map.get("cjsj"));
					one.put("occTime", map.get("occ_time"));
					if (map.get(occ_time) != null) {
						try {
							one.put(timeNum, sdf.parse(map.get(occ_time).toString()).getTime());
						} catch (ParseException e) {
						}
					}
					res.add(one);
				}
			}
		}
		if(StringUtils.isEmpty(selectMethod)) {
			return res;
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		if (res != null && res.size() > 0) {
			for (Map<String, Object> map : res) {
				Object valObject = map.get(val);
				if (valObject != null) {
					String valString = valObject.toString();
					String ciIdStr = "";
					String ciNameStr = "";
					// 循环把res里面的数据中ciId和ciName取出来——start
					for (Map<String, Object> mapRes : res) {
						boolean ciIdFlag = mapRes.containsKey(ciId);
						if (ciIdFlag) {
							ciIdStr = (String) mapRes.get("ciId");
						}
						boolean ciNameFlag = mapRes.containsKey(ciId);
						if (ciNameFlag) {
							ciNameStr = (String) mapRes.get("ciName");
						}
					}
					// 循环把res里面的数据中ciId和ciName取出来——end
					if (StringUtils.isNotEmpty(valString) && !"Infinity".equals(valString)) {
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, valString);
						oneMap.put("ciName", map.get(ciName));
						Object ciIdObject = map.get(ciId);
						if (ciIdObject != null) {
							oneMap.put("ciId", ciIdObject.toString());
						}
						Object timeNumObject = map.get(timeNum);
						if (timeNumObject != null) {
							oneMap.put(timeNum, timeNumObject.toString());
						}
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					} else {
						Object timeNumObject = map.get(timeNum);
						Map<String, Object> oneMap = new HashMap<String, Object>();
						oneMap.put(val, 0);
						oneMap.put("ciName", ciNameStr);
						oneMap.put("ciId", ciIdStr);
						oneMap.put(timeNum, timeNumObject.toString());
						oneMap.put("cjsj", map.get("cjsj"));
						oneMap.put("occTime", map.get("occTime"));
						returnList.add(oneMap);
					}
				}
			}
		}

		return returnList;
	}

	/**
	 * 按时间分组后的数据转成List
	 *
	 * @param searchResponse
	 * @return
	 * @throws ParseException
	 */
	private static List<Map<String, Object>> timeSearchResponse2List(SearchResponse searchResponse) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		// 请求
		// 响应
		Map<String, Aggregation> aggrMap = searchResponse.getAggregations().asMap();

		ParsedDateHistogram occTime = (ParsedDateHistogram) aggrMap.get(occ_time);

		@SuppressWarnings("unchecked")
		List<ParsedDateHistogram.ParsedBucket> buckets = (List<ParsedDateHistogram.ParsedBucket>) occTime.getBuckets();
		for (ParsedDateHistogram.ParsedBucket parsedBucket : buckets) {
			long timeNum = -1l;
			try {
				timeNum = sdf.parse(parsedBucket.getKeyAsString()).getTime();
			} catch (ParseException e) {
			}
			Map<String, Aggregation> ciIdMap = parsedBucket.getAggregations().asMap();
			ParsedStringTerms ciId = (ParsedStringTerms) ciIdMap.get(ci_id);
			if (ciId != null) {
				@SuppressWarnings("unchecked")
				List<ParsedStringTerms.ParsedBucket> buckets2 = (List<ParsedStringTerms.ParsedBucket>) ciId
						.getBuckets();
				for (ParsedStringTerms.ParsedBucket parsedBucket2 : buckets2) {
					res.add(getOneMap(parsedBucket2.getAggregations(), timeNum));
				}
			} else {
				res.add(getOneMap(parsedBucket.getAggregations(), timeNum));
			}
		}

		return res;
	}

	private static Map<String, Object> getOneMap(Aggregations aggregations, long timeNumV) {
		Map<String, Object> one = new HashMap<String, Object>();
		Aggregation valAgg = aggregations.get(val);
		ParsedTopHits parsedTopHits = aggregations.get(ci_name);
		if (valAgg.getClass().equals(ParsedSum.class)) {
			one.put(val, ((ParsedSum) valAgg).getValue());
		} else if (valAgg.getClass().equals(ParsedMax.class)) {
			one.put(val, ((ParsedMax) valAgg).getValue());
		} else if (valAgg.getClass().equals(ParsedMin.class)) {
			one.put(val, ((ParsedMin) valAgg).getValue());
		} else if (valAgg.getClass().equals(ParsedAvg.class)) {
			one.put(val, ((ParsedAvg) valAgg).getValue());
		}
		Object obj = one.get(val);
		if (obj instanceof Double) {
			Double objDou = (Double) obj;
			String valStr = String.valueOf(objDou);
			if (!"Infinity".equals(valStr) && !"-Infinity".equals(valStr)) {
				DecimalFormat a = new DecimalFormat("###0.00");
				String frmStr = a.format(objDou);
				one.put("val", frmStr);
			} else {
				one.put("val", valStr);
			}
		}
		one.put(timeNum, timeNumV);
		if (parsedTopHits != null) {
			SearchHits searchHits1 = parsedTopHits.getHits();
			if (searchHits1 != null) {
				SearchHit[] searchHits = searchHits1.getHits();
				if (searchHits != null && searchHits.length > 0) {
					Map<String, Object> sMap = searchHits[0].getSourceAsMap();
					if (sMap != null) {
						one.put(ciName, sMap.get(ci_name));
						one.put(ciId, sMap.get(ci_id));
						one.put("cjsj", sMap.get("cjsj"));
						one.put("occTime", sMap.get("occ_time"));
						one.put("kpiName", sMap.get("kpi_name"));
					}
				}
			}
		}
		// 如果求最大值时，时间颗粒度范围没有值则赋值为0
		String val = (String) one.get("val");
		// String valStr=String.valueOf(val);
		if ("-Infinity".equals(val)) {
			one.put("val", 0);
		}
		return one;
	}
	
	private static Map<String, Object> getOneMapPerViewLineChart(Aggregations aggregations, long timeNumV,Map<String,Object> mapValUnit) {
		Map<String, Object> one = new HashMap<String, Object>();
		Aggregation valAgg = aggregations.get(val);
		ParsedTopHits parsedTopHits = aggregations.get(ci_name);
		if (valAgg.getClass().equals(ParsedSum.class)) {
			one.put(val, ((ParsedSum) valAgg).getValue());
		} else if (valAgg.getClass().equals(ParsedMax.class)) {
			one.put(val, ((ParsedMax) valAgg).getValue());
		} else if (valAgg.getClass().equals(ParsedMin.class)) {
			one.put(val, ((ParsedMin) valAgg).getValue());
		} else if (valAgg.getClass().equals(ParsedAvg.class)) {
			one.put(val, ((ParsedAvg) valAgg).getValue());
		}
		Object obj = one.get(val);
		if (obj instanceof Double) {
			Double objDou = (Double) obj;
			String valStr = String.valueOf(objDou);
			if (!"Infinity".equals(valStr) && !"-Infinity".equals(valStr)) {
				DecimalFormat a = new DecimalFormat("###0.00");
				String frmStr = a.format(objDou);
				one.put("val", frmStr);
			} else {
				one.put("val", valStr);
			}
		}
		one.put(timeNum, timeNumV);
		if (parsedTopHits != null) {
			SearchHits searchHits1 = parsedTopHits.getHits();
			if (searchHits1 != null) {
				SearchHit[] searchHits = searchHits1.getHits();
				if (searchHits != null && searchHits.length > 0) {
					Map<String, Object> sMap = searchHits[0].getSourceAsMap();
					if (sMap != null) {
						one.put(ciName, sMap.get(ci_name));
						one.put(ciId, sMap.get(ci_id));
						String kpiName=(String) sMap.get("kpi_name");
						one.put("cjsj", sMap.get("cjsj"));
						one.put("occTime", sMap.get("occ_time"));
						one.put("kpiName", sMap.get("kpi_name"));
						String valUnit=(String) mapValUnit.get(kpiName);
						one.put("valUnit", valUnit);
					}
				}
			}
		}
		// 如果求最大值时，时间颗粒度范围没有值则赋值为0
		String val = (String) one.get("val");
		// String valStr=String.valueOf(val);
		if ("-Infinity".equals(val)) {
			one.put("val", 0);
		}
		return one;
	}
	
	private static Map<String, Object> getOneMapThermodynamicDiagram(Aggregations aggregations, long timeNumV,String valUnit) {
		Map<String, Object> one = new HashMap<String, Object>();
		Aggregation valAgg = aggregations.get(val);
		ParsedTopHits parsedTopHits = aggregations.get(ci_name);
		if (valAgg.getClass().equals(ParsedSum.class)) {
			one.put(val, ((ParsedSum) valAgg).getValue());
		} else if (valAgg.getClass().equals(ParsedMax.class)) {
			one.put(val, ((ParsedMax) valAgg).getValue());
		} else if (valAgg.getClass().equals(ParsedMin.class)) {
			one.put(val, ((ParsedMin) valAgg).getValue());
		} else if (valAgg.getClass().equals(ParsedAvg.class)) {
			one.put(val, ((ParsedAvg) valAgg).getValue());
		}
		Object obj = one.get(val);
		if (obj instanceof Double) {
			Double objDou = (Double) obj;
			String valStr = String.valueOf(objDou);
			if (!"Infinity".equals(valStr) && !"-Infinity".equals(valStr)) {
				DecimalFormat a = new DecimalFormat("###0.00");
				String frmStr = a.format(objDou);
				one.put("val", frmStr);
			} else {
				one.put("val", valStr);
			}
		}
		one.put(timeNum, timeNumV);
		one.put("valUnit", valUnit);
		if (parsedTopHits != null) {
			SearchHits searchHits1 = parsedTopHits.getHits();
			if (searchHits1 != null) {
				SearchHit[] searchHits = searchHits1.getHits();
				if (searchHits != null && searchHits.length > 0) {
					Map<String, Object> sMap = searchHits[0].getSourceAsMap();
					if (sMap != null) {
						one.put(ciName, sMap.get(ci_name));
						one.put(ciId, sMap.get(ci_id));
						one.put("cjsj", sMap.get("cjsj"));
						one.put("occTime", sMap.get("occ_time"));
						one.put("kpiName", sMap.get("kpi_name"));
					}
				}
			}
		}
		// 如果求最大值时，时间颗粒度范围没有值则赋值为0
		String val = (String) one.get("val");
		// String valStr=String.valueOf(val);
		if ("-Infinity".equals(val)) {
			one.put("val", 0);
		}
		return one;
	}

	/**
	 * 单个的性能插入
	 *
	 * @param doc
	 * @return
	 */
	public static String insertIomPmvPerformance(Object obj) {
		if (obj != null) {
			Map<String, Object> doc = JSON.parseObject(JSON.toJSONString(obj), Map.class);
			return EsUtil.insert(HumpLineUtil.humpToLineMap(doc), iom_pmv_performance);
		}
		return null;
	}

	/**
	 * 单个的性能插入--- 增加domainId
	 *
	 * @param doc
	 * @return
	 */
	public static String insertIomPmvPerformanceToDomainId(Object obj, String domainId,String dateStr) {
		if (obj != null) {
			Map<String, Object> doc = JSON.parseObject(JSON.toJSONString(obj), Map.class);
			doc.put("domainId", domainId);
			return EsUtil.insert(HumpLineUtil.humpToLineMap(doc), iom_pmv_performance+"-"+dateStr);
		}
		return null;
	}
//	public static String insertIomPmvPerformance(PmvPerformance pmvPerformance) {
//		if (pmvPerformance != null) {
//			return EsUtil.insert(pmvPerformance.toLineMap(), iom_pmv_performance);
//		}
//		return null;
//	}

	public static List<PmvPerformanceCurrentInfo> getListPer(String startDate, String endDate, String jsons,String ciCodes,
			String kpiName) {
		List<String> tables=new ArrayList<String>();
		// 获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s = sdf.format(new Date());
		if (StringUtils.isEmpty(startDate)) {
			startDate = s + " 00:00:00";
		}
		if (StringUtils.isEmpty(endDate)) {
			endDate = s + " 23:59:59";
		}

		List<PmvPerformanceCurrentInfo> pmvPerformanceCurrentInfos = null;
		try {
			// 初始化查询条件
			Map<String, Object> criterias = new HashMap<String, Object>();
//            if (!StringUtils.isEmpty(startDate)) {
			criterias.put(">=occ_time", startDate);
			criterias.put("<=occ_time", endDate);
//            } else {
//                //获取当前时间
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
//                String s = sdf.format(new Date());
//                criterias.put(">=occ_time", s + " 00:00:00");
//                criterias.put("<=occ_time", s + " 23:59:59");
//            }
			//criterias.put("ci_id", jsons);
			criterias.put("ci_name", ciCodes);
			criterias.put("kpi_name", kpiName);
			// 初始化一个高级客户端
//            RestHighLevelClient client = new RestHighLevelClient(
//                    RestClient.builder(
//                            new HttpHost("45.125.46.128", 9200, "http")));
			try {
				//根据startTime和endTime截取日期字段进行ES跨表查询
				long nd = 1000 * 24 * 60 * 60;			
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				Date startTimeDate=sdf1.parse(startDate);
				Date endTimeDate=sdf1.parse(endDate);
				String startTimeStr=sdf2.format(startTimeDate);
				String endTimeStr=sdf2.format(endTimeDate);
				if(startTimeStr.equals(endTimeStr)) {
					String table=iom_pmv_performance+"-"+startTimeStr;
					tables.add(table);
				}else {
					long diff = sdf2.parse(endDate).getTime() - sdf2.parse(startDate).getTime();
					// 计算差多少天
				    long day = diff / nd;
				    int dayInt=(int)day;
				    for(int i=1;i<=dayInt;i++) {
				    	Calendar calendar = Calendar.getInstance();  
			            calendar.setTime(startTimeDate);  
			            calendar.add(Calendar.DAY_OF_MONTH, +i);  
			            Date date = calendar.getTime();
			            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
			            String time=sdf3.format(date);
			            if(time.equals(endTimeStr)) {
			            	if(i==1) {
			            		tables.add(iom_pmv_performance+"-"+startTimeStr);
				            	tables.add(iom_pmv_performance+"-"+endTimeStr);
				            	break;
			            	}else {
			            		tables.add(iom_pmv_performance+"-"+endTimeStr);
			            		break;
			            	}			            	
			            }else {
			            	if(i==1) {
			            		tables.add(iom_pmv_performance+"-"+startTimeStr);
				            	tables.add(iom_pmv_performance+"-"+time);
			            	}else {
				            	tables.add(iom_pmv_performance+"-"+time);
			            	}
			            	
			            }
				    }
				}
			} catch (Exception e) {
	           e.printStackTrace();
			}
			RestHighLevelClient client = EsTcpClient.getClient();
			// 查询方式
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String [] datesArray = tableList.toArray(new String[tableList.size()]);
			SearchRequest searchRequest = new SearchRequest(datesArray);
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.size(300);
			searchSourceBuilder.sort("occ_time", SortOrder.DESC);
			// 设置超时时间
			searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
			searchSourceBuilder.query(EsUtil.builder(criterias));
			searchRequest.source(searchSourceBuilder);
			// 获取返回值
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			// 解析返回值
			SearchHits searchHits = searchResponse.getHits();
			SearchHit[] searchHits1 = searchHits.getHits();
			if (searchHits1.length > 0) {
				pmvPerformanceCurrentInfos = new ArrayList<>();
				for (SearchHit hit : searchHits1) {
					Map<String, Object> sourceAsMap = hit.getSourceAsMap();
					// 转为性能实体类
					PmvPerformanceCurrentInfo pmvPerformanceCurrentInfo = new PmvPerformanceCurrentInfo();
					pmvPerformanceCurrentInfo.setVal(sourceAsMap.get("val").toString());
					pmvPerformanceCurrentInfo.setOcc_time(sourceAsMap.get("occ_time").toString());
					pmvPerformanceCurrentInfo.setKpi_name(sourceAsMap.get("kpi_name").toString());
					pmvPerformanceCurrentInfo.setCi_id(sourceAsMap.get("ci_name").toString());

					pmvPerformanceCurrentInfos.add(pmvPerformanceCurrentInfo);
				}
				
				Collections.reverse(pmvPerformanceCurrentInfos);
			}
			// 关闭客户端
			// client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pmvPerformanceCurrentInfos;
	}

	/**
	 * 性能最新数据查询查询
	 *
	 * @param ciIds
	 * @param kpiNames
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, Object>> queryPmvDataByEs(String ciIds, String ciCodes,String kpiNames, String startTime,
			String endTime) throws IOException {
		return queryPmvDataByEs(ciIds, ciCodes,kpiNames, startTime, endTime, true, true, true);
	}

	/**
	 * 性能最新数据查询查询
	 *
	 * @param ciIds
	 * @param kpiNames
	 * @param startTime
	 * @param endTime
	 * @param max       最大值
	 * @param min       最小值
	 * @param avg       平均值
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, Object>> queryPmvDataByEs(String ciIds,String ciCodes,String kpiNames, String startTime,
			String endTime, Boolean max, Boolean min, Boolean avg) throws IOException {

		return queryPmvDataByEs(ciIds, ciCodes, null,kpiNames, startTime, endTime, max, min, avg);
	}

	public static List<Map<String, Object>> queryPmvDataByEs(String kpiIds, String kpiNames,String startTime, String endTime)
			throws IOException {
		return queryPmvDataByEs(null, null,kpiIds, kpiNames,startTime, endTime, null, null, null);
	}

	/**
	 * 性能最新数据查询查询
	 *
	 * @param ciIds
	 * @param kpiIds
	 * @param kpiNames
	 * @param startTime
	 * @param endTime
	 * @param max       最大值
	 * @param min       最小值
	 * @param avg       平均值
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, Object>> queryPmvDataByEs(String ciIds, String ciCodes,String kpiIds, String kpiNames,
			String startTime, String endTime, Boolean max, Boolean min, Boolean avg) throws IOException {
		List<String> tables=new ArrayList<String>();
		if (max == null)
			max = true;
		if (min == null)
			min = true;
		if (avg == null)
			avg = true;

		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		//criterias.put("ci_id", ciIds);
		criterias.put("ci_name", ciCodes);
		criterias.put("kpi_name", kpiNames);
		//criterias.put("kpi_id", kpiIds);
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		// 指定查询的库表
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		TermsAggregationBuilder ciIdAgg = AggregationBuilders.terms("ci_name").field("ci_name").size(2147483647);
		TermsAggregationBuilder kpiIdAgg = AggregationBuilders.terms("kpi_name").field("kpi_name").size(2147483647);
		if (max) {
			kpiIdAgg.subAggregation(AggregationBuilders.max("max_val").field("val"));
		}
		if (min) {
			kpiIdAgg.subAggregation(AggregationBuilders.min("min_val").field("val"));
		}

		if (avg) {
			kpiIdAgg.subAggregation(AggregationBuilders.avg("avg_val").field("val"));
		}
		kpiIdAgg.subAggregation(AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC));
		ciIdAgg.subAggregation(kpiIdAgg);
		searchSourceBuilder.query(EsUtil.builder(criterias)).size(0).aggregation(ciIdAgg);

		searchRequest.source(searchSourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		List<ParsedStringTerms.ParsedBucket> ci_ids = (List<ParsedStringTerms.ParsedBucket>) ((ParsedStringTerms) response
				.getAggregations().asMap().get("ci_name")).getBuckets();
		for (ParsedStringTerms.ParsedBucket ci_id : ci_ids) {
			Map<String, Aggregation> aggrMap = ci_id.getAggregations().getAsMap();
			List<ParsedStringTerms.ParsedBucket> kpi_ids = (List<ParsedStringTerms.ParsedBucket>) ((ParsedStringTerms) aggrMap
					.get("kpi_name")).getBuckets();
			for (ParsedStringTerms.ParsedBucket kpi_id : kpi_ids) {
				Aggregations aggs = kpi_id.getAggregations();
				TopHits topHits = aggs.get("current_val");
				SearchHits searchHits = topHits.getHits();
				SearchHit[] searchHits1 = searchHits.getHits();
				if (searchHits1 != null && searchHits1.length > 0) {
					Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
					Map<String, Object> hitNap = formatHumpName(sMap);

					Max maxAgg = aggs.get("max_val");
					if (maxAgg != null) {
						hitNap.put("maxVal", maxAgg.getValue());
					}
					Min minAgg = aggs.get("min_val");
					if (minAgg != null) {
						hitNap.put("minVal", minAgg.getValue());
					}
					Avg avgAgg = aggs.get("avg_val");
					if (avgAgg != null) {
						hitNap.put("avgVal", avgAgg.getValue());
					}
					returnList.add(hitNap);
				}
			}

		}
		return returnList;
	}

	public static List<PmvPerformance> queryPerformance(String ciIds, String ciCodes,String kpiNames, String startTime, String endTime,
			Boolean max, Boolean min, Boolean avg) throws IOException {
		List<Map<String, Object>> returnList = queryPmvDataByEs(ciIds, ciCodes,kpiNames, startTime, endTime, max, min, avg);
		return PmvPerformance.parse(returnList);
	}

	// pmv展示告警查询性能曲线
	public static List<Map<String, Object>> findPerformanceByKpiIdRectification(Map<Object, Object> params)
			throws IOException {
		List<String> tables=new ArrayList<String>();
		//String kpiIds = (String) params.get("kpiIds");
		String kpiNames = (String) params.get("kpiNames");
		String startTime = (String) params.get("startTime");
		String endTime = (String) params.get("endTime");
		String ciCode = (String) params.get("ciCode");
		//String ciTypeId = (String) params.get("ciTypeId");
		String domainId=(String) params.get("domainId");
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		criterias.put("ci_name", ciCode);
		//criterias.put("ci_type_id", ciTypeId);
		//criterias.put("kpi_id", kpiIds);
		criterias.put("kpi_name", kpiNames);
		if(domainId!=null) {
			criterias.put("domain_id", domainId);	
		}
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		if ("".equals(startTime) && "".equals(endTime)) {
			searchSourceBuilder.query(EsUtil.builder(criterias)).size(1).sort("occ_time", SortOrder.DESC);
		} else {
			searchSourceBuilder.query(EsUtil.builder(criterias)).size(10000).sort("occ_time", SortOrder.ASC);
		}
		searchRequest.source(searchSourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHit[] searchHits = response.getHits().getHits();

		for (SearchHit searchHit : searchHits) {
			Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
			Map<String, Object> map = formatHumpName(sourceAsMap);
			returnList.add(map);
		}

		return returnList;
	}
	
	// pmv展示告警查询性能曲线
	public static List<Map<String, Object>> findPerformanceByKpiIdRectificationDesc(Map<Object, Object> params)
			throws IOException {
		List<String> tables=new ArrayList<String>();
		//String kpiIds = (String) params.get("kpiIds");
		String kpiNames = (String) params.get("kpiNames");
		String startTime = (String) params.get("startTime");
		String endTime = (String) params.get("endTime");
		String ciCode = (String) params.get("ciCode");
		//String ciTypeId = (String) params.get("ciTypeId");
		String domainId=(String) params.get("domainId");
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		criterias.put("ci_name", ciCode);
		//criterias.put("ci_type_id", ciTypeId);
		//criterias.put("kpi_id", kpiIds);
		criterias.put("kpi_name", kpiNames);
		if(domainId!=null) {
			criterias.put("domain_id", domainId);	
		}
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		if ("".equals(startTime) && "".equals(endTime)) {
			searchSourceBuilder.query(EsUtil.builder(criterias)).size(1).sort("occ_time", SortOrder.DESC);
		} else {
			searchSourceBuilder.query(EsUtil.builder(criterias)).size(10000).sort("occ_time", SortOrder.DESC);
		}
		searchRequest.source(searchSourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHit[] searchHits = response.getHits().getHits();

		for (SearchHit searchHit : searchHits) {
			Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
			Map<String, Object> map = formatHumpName(sourceAsMap);
			returnList.add(map);
		}

		return returnList;
	}
	
	    //性能监控源统计，默认显示所有监控源及最后接收到性能数据时间
		public static List<Map<String, Object>> getPerformanceMonitorByDataSource(Map<Object, Object> params)
				throws IOException {
			List<String> tables=new ArrayList<String>();
			String startTime = (String) params.get("startTime");
			String endTime = (String) params.get("endTime");
			String dataDource = (String) params.get("dataDource");
			String domainId = (String) params.get("domainId");
			Map<String, Object> criterias = new HashMap<String, Object>();
			criterias.put(">=occ_time", startTime);
			criterias.put("<=occ_time", endTime);
			criterias.put("data_dource", dataDource);
			if(domainId!=null) {
				criterias.put("domain_id", domainId);
			}
			try {
				//根据startTime和endTime截取日期字段进行ES跨表查询
				long nd = 1000 * 24 * 60 * 60;			
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				Date startTimeDate=sdf1.parse(startTime);
				Date endTimeDate=sdf1.parse(endTime);
				String startTimeStr=sdf2.format(startTimeDate);
				String endTimeStr=sdf2.format(endTimeDate);
				if(startTimeStr.equals(endTimeStr)) {
					String table=iom_pmv_performance+"-"+startTimeStr;
					tables.add(table);
				}else {
					long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
					// 计算差多少天
				    long day = diff / nd;
				    int dayInt=(int)day;
				    for(int i=1;i<=dayInt;i++) {
				    	Calendar calendar = Calendar.getInstance();  
			            calendar.setTime(startTimeDate);  
			            calendar.add(Calendar.DAY_OF_MONTH, +i);  
			            Date date = calendar.getTime();
			            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
			            String time=sdf3.format(date);
			            if(time.equals(endTimeStr)) {
			            	if(i==1) {
			            		tables.add(iom_pmv_performance+"-"+startTimeStr);
				            	tables.add(iom_pmv_performance+"-"+endTimeStr);
				            	break;
			            	}else {
			            		tables.add(iom_pmv_performance+"-"+endTimeStr);
			            		break;
			            	}			            	
			            }else {
			            	if(i==1) {
			            		tables.add(iom_pmv_performance+"-"+startTimeStr);
				            	tables.add(iom_pmv_performance+"-"+time);
			            	}else {
				            	tables.add(iom_pmv_performance+"-"+time);
			            	}
			            	
			            }
				    }
				}
			} catch (Exception e) {
	           e.printStackTrace();
			}
			List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
			RestHighLevelClient client = EsTcpClient.getClient();
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String [] datesArray = tableList.toArray(new String[tableList.size()]);
			SearchRequest searchRequest = new SearchRequest(datesArray);
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
					.aggregation(AggregationBuilders.terms("groupBy").field("data_dource").subAggregation(AggregationBuilders.topHits("current_data").size(1).sort("occ_time", SortOrder.DESC)));
			searchRequest.source(searchSourceBuilder);
			SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
			Map<String, Aggregation> aggrMap = response.getAggregations().asMap();
			ParsedStringTerms terms = (ParsedStringTerms) aggrMap.get("groupBy");
			List<ParsedStringTerms.ParsedBucket> buckets = (List<ParsedStringTerms.ParsedBucket>) terms.getBuckets();
			for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
				TopHits topHits = parsedBucket.getAggregations().get("current_data");
				SearchHits searchHits = topHits.getHits();
				SearchHit[] searchHits1 = searchHits.getHits();
				if (searchHits1 != null && searchHits1.length > 0) {
					Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
					Map<String, Object> hitNap = formatHumpName(sMap);
					returnList.add(hitNap);
				}
			}
			return returnList;
		}

	// 获取KpiWeiDu
	public static List<Map<String, Object>> getCiIdByKpiId(Map<String, Object> itemMap) throws IOException {
		List<String> tables=new ArrayList<String>();
		//String[] kpiIds = (String[]) itemMap.get("kpiIdList");
		String[] kpiNames = (String[]) itemMap.get("kpiNameList");
		String startTime = (String) itemMap.get("startTime");
		String endTime = (String) itemMap.get("endTime");
		List<String> listKpiNames = Arrays.asList(kpiNames);
		String kpiNameString = "";
		if (listKpiNames != null && listKpiNames.size() > 0) {
			kpiNameString = Joiner.on(",").join(listKpiNames);
		}
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put("kpi_name", kpiNameString);
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(EsUtil.builder(criterias)).size(10000).sort("ci_id", SortOrder.DESC);
		searchRequest.source(searchSourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHit[] searchHits = response.getHits().getHits();
		Set<String> set = new HashSet<String>();
		for (SearchHit searchHit : searchHits) {
			Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
			String ciId = (String) sourceAsMap.get("ci_id");
			String ciName = (String) sourceAsMap.get("ci_name");
			set.add(ciName);
		}
		if (set != null && set.size() > 0) {
			for (String str : set) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("CI_NAME", str);
				returnList.add(map);
			}

		}
		return returnList;
	}

	/**
	 * 字段改为驼峰
	 *
	 * @param map
	 * @return
	 */
	public static Map<String, Object> formatHumpName(Map<String, Object> map) {
		Map<String, Object> newMap = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = underlineToCamel(key);
			newMap.put(newKey, entry.getValue());
		}
		return newMap;
	}

	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(Character.toLowerCase(param.charAt(i)));
			}
		}
		return sb.toString();
	}

//    public static List<Map<String, Object>> selectRankChartbBykpiAndCI(Map<String,Object> map) throws IOException {
//        //startTime="2020-01-19 00:00:00";
//        //endTime="2020-01-19 23:59:59";
//        Map<String, Object> criterias = new HashMap<String, Object>();
//        criterias.put(">=occ_time", map.get("endTime"));
//        criterias.put("<=occ_time", map.get("startTime"));
//       // criterias.put("ci_id", map.get("ciIds"));
//        criterias.put("kpi_id", map.get("kpiId"));
//        criterias.put("ci_type_id", map.get("ciTypeId"));
//        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
//        RestHighLevelClient client = EsTcpClient.getClient();
//        SearchRequest searchRequest = new SearchRequest(iom_pmv_performance);
//        // 指定查询的库表
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(EsUtil.builder(criterias)).size(0);
//        /*.size(10)
//                .aggregation(AggregationBuilders.terms("ciId_kpiId")
//                        .script(new Script("doc['ci_id']"))
//                        .size(2147483647)
//                        .subAggregation(AggregationBuilders
//                                .max("max_val")
//                                .field("val")
//                        ));*/
//        //最大值
//        MaxAggregationBuilder aggregation =AggregationBuilders.max("agg_max").field("val");
//        searchSourceBuilder.aggregation(aggregation);
//        //分组
//        AggregationBuilder groupByType = AggregationBuilders.terms("group_ci").field("ci_id");
//        searchSourceBuilder.aggregation(groupByType);
//        TopHitsAggregationBuilder topHitsAggregationBuilder = AggregationBuilders.topHits("group_ci");
//        searchSourceBuilder.aggregation(topHitsAggregationBuilder);
//        //searchSourceBuilder.aggregation(AggregationBuilders.topHits("current_val").size(1));
//
//        //searchSourceBuilder.sort(new FieldSortBuilder("_uid").order(SortOrder.ASC));
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
//        //解析返回值
//        Map<String, Aggregation> aggrMap = response.getAggregations().asMap();
//        ParsedStringTerms terms = (ParsedStringTerms) aggrMap.get("ciId_kpiId");
//        List<ParsedStringTerms.ParsedBucket> buckets = (List<ParsedStringTerms.ParsedBucket>) terms.getBuckets();
//        for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
//            Max max = parsedBucket.getAggregations().get("max_val");
//            Double maxVal = max.getValue();
//            Min min = parsedBucket.getAggregations().get("min_val");
//            Double minVal = min.getValue();
//            TopHits topHits = parsedBucket.getAggregations().get("current_val");
//            SearchHits searchHits = topHits.getHits();
//            SearchHit[] searchHits1 = searchHits.getHits();
//            if (searchHits1 != null && searchHits1.length > 0) {
//                Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
//                Map<String, Object> hitNap = formatHumpName(sMap);
//                hitNap.put("maxVal", maxVal);
//                hitNap.put("minVal", minVal);
//                returnList.add(hitNap);
//            }
//        }
//        return returnList;
//    }

	public static List<Map<String, Object>> selectRankChartbBykpiAndCI(Map<String, Object> map) throws IOException {
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
//		if(startTime==null || "".equals(startTime) || endTime==null || "".equals(endTime)) {
//			Calendar calendar = Calendar.getInstance();
//            calendar.setTime(new Date());
//            calendar.set(Calendar.HOUR_OF_DAY, 0);
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.SECOND, 0);
//            Date zero = calendar.getTime();
//            String zeroStr=sdf.format(zero);
//            startTime=zeroStr;
//            endTime=sdf.format(new Date());
//		}
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		//criterias.put("ci_id", map.get("ciIds"));
		criterias.put("ci_name", map.get("ciCodes"));
		//criterias.put("kpi_id", map.get("kpiId"));
		criterias.put("kpi_name", map.get("kpiName"));
		//criterias.put("ci_type_id", map.get("ciTypeId"));
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
						.order(BucketOrder.aggregation("max_val", false)).size(2147483647)
						.subAggregation(AggregationBuilders.max("max_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC)));
		searchRequest.source(searchSourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		Map<String, Aggregation> aggrMap = response.getAggregations().asMap();
		ParsedStringTerms terms = (ParsedStringTerms) aggrMap.get("groupBy");
		List<ParsedStringTerms.ParsedBucket> buckets = (List<ParsedStringTerms.ParsedBucket>) terms.getBuckets();
		for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
			Max max = parsedBucket.getAggregations().get("max_val");
			Double maxVal = max.getValue();
			TopHits topHits = parsedBucket.getAggregations().get("current_val");
			SearchHits searchHits = topHits.getHits();
			SearchHit[] searchHits1 = searchHits.getHits();
			if (searchHits1 != null && searchHits1.length > 0) {
				Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
				String valStr = String.valueOf(maxVal);
				if (!"Infinity".equals(valStr) && !"-Infinity".equals(valStr)) {
					BigDecimal bd = new BigDecimal(valStr);
					bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
					sMap.put("val", bd.toString());
				} else {
					sMap.put("val", "");
				}
				// sMap.put("val", maxVal);
				returnList.add(sMap);
			}
		}
		return returnList;
	}
	
	//集群策略使用
	public static List<Map<String, Object>> selectClusterStrategy(Map<String, Object> map) throws IOException {
		List<String> tables=new ArrayList<String>();
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		criterias.put("ci_name", map.get("ciCodes"));
		criterias.put("kpi_name", map.get("kpiName"));
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
						.order(BucketOrder.aggregation("max_val", false)).size(2147483647)
						.subAggregation(AggregationBuilders.max("max_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC)));
		searchRequest.source(searchSourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		Map<String, Aggregation> aggrMap = response.getAggregations().asMap();
		ParsedStringTerms terms = (ParsedStringTerms) aggrMap.get("groupBy");
		List<ParsedStringTerms.ParsedBucket> buckets = (List<ParsedStringTerms.ParsedBucket>) terms.getBuckets();
		for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
			Max max = parsedBucket.getAggregations().get("max_val");
			Double maxVal = max.getValue();
			TopHits topHits = parsedBucket.getAggregations().get("current_val");
			SearchHits searchHits = topHits.getHits();
			SearchHit[] searchHits1 = searchHits.getHits();
			if (searchHits1 != null && searchHits1.length > 0) {
				Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
//				String valStr = String.valueOf(maxVal);
//				if (!"Infinity".equals(valStr) && !"-Infinity".equals(valStr)) {
//					BigDecimal bd = new BigDecimal(valStr);
//					bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
//					sMap.put("val", bd.toString());
//				} else {
//					sMap.put("val", "");
//				}
				returnList.add(sMap);
			}
		}
		return returnList;
	}
	
	//变化图(优化)
	public static List<Map<String, Object>> getChangeChartRectification(Map<String, Object> map) throws IOException {
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		String ciCodes=(String) map.get("ciCodes");
		String kpiName=(String) map.get("kpiName");
		String selectMethod=(String) map.get("selectMethod");
//		String textAlign=(String) map.get("textAlign");
//		String customUnit=(String) map.get("customUnit");
//		String showPoint=(String) map.get("showPoint");
		
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		criterias.put("ci_name", ciCodes);
		criterias.put("kpi_name", kpiName);
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		if("max".equals(selectMethod)) {
				searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
						.order(BucketOrder.aggregation("max_val", false)).size(2147483647)
						.subAggregation(AggregationBuilders.max("max_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1)));
		         searchRequest.source(searchSourceBuilder);		
		}else if("min".equals(selectMethod)) {
				searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
						.order(BucketOrder.aggregation("min_val", false)).size(2147483647)
						.subAggregation(AggregationBuilders.min("min_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1)));
		         searchRequest.source(searchSourceBuilder);							
		}else if("sum".equals(selectMethod)) {	
				searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
						.order(BucketOrder.aggregation("sum_val", false)).size(2147483647)
						.subAggregation(AggregationBuilders.sum("sum_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1)));
		         searchRequest.source(searchSourceBuilder);						
		}else if("avg".equals(selectMethod)) {			
				searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
						.order(BucketOrder.aggregation("avg_val", false)).size(2147483647)
						.subAggregation(AggregationBuilders.avg("avg_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1)));
		         searchRequest.source(searchSourceBuilder);	
		}
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		Map<String, Aggregation> aggrMap = response.getAggregations().asMap();
		ParsedStringTerms terms = (ParsedStringTerms) aggrMap.get("groupBy");
		List<ParsedStringTerms.ParsedBucket> buckets = (List<ParsedStringTerms.ParsedBucket>) terms.getBuckets();
		if("max".equals(selectMethod)) {
			for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
				Max max = parsedBucket.getAggregations().get("max_val");
				Double maxVal = max.getValue();
				TopHits topHits = parsedBucket.getAggregations().get("current_val");
				SearchHits searchHits = topHits.getHits();
				SearchHit[] searchHits1 = searchHits.getHits();
				if (searchHits1 != null && searchHits1.length > 0) {
					Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
					String valStr = String.valueOf(maxVal);
					if (!"Infinity".equals(valStr) && !"-Infinity".equals(valStr)) {
						BigDecimal bd = new BigDecimal(valStr);
						bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
						sMap.put("val", bd.toString());
					} else {
						sMap.put("val", "");
					}
//					if(returnList.size()==Integer.valueOf(showPoint)) {
//						return returnList;
//					}
					returnList.add(sMap);
				}
			}
		}else if("min".equals(selectMethod)) {
			for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
				Min min = parsedBucket.getAggregations().get("min_val");
				Double minVal = min.getValue();
				TopHits topHits = parsedBucket.getAggregations().get("current_val");
				SearchHits searchHits = topHits.getHits();
				SearchHit[] searchHits1 = searchHits.getHits();
				if (searchHits1 != null && searchHits1.length > 0) {
					Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
					String valStr = String.valueOf(minVal);
					if (!"Infinity".equals(valStr) && !"-Infinity".equals(valStr)) {
						BigDecimal bd = new BigDecimal(valStr);
						bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
						sMap.put("val", bd.toString());
					} else {
						sMap.put("val", "");
					}
//					if(returnList.size()==Integer.valueOf(showPoint)) {
//						return returnList;
//					}
					returnList.add(sMap);
				}
			}
		}else if("sum".equals(selectMethod)) {
			for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
				Sum sum = parsedBucket.getAggregations().get("sum_val");
				Double sumVal = sum.getValue();
				TopHits topHits = parsedBucket.getAggregations().get("current_val");
				SearchHits searchHits = topHits.getHits();
				SearchHit[] searchHits1 = searchHits.getHits();
				if (searchHits1 != null && searchHits1.length > 0) {
					Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
					String valStr = String.valueOf(sumVal);
					if (!"Infinity".equals(valStr) && !"-Infinity".equals(valStr)) {
						BigDecimal bd = new BigDecimal(valStr);
						bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
						sMap.put("val", bd.toString());
					} else {
						sMap.put("val", "");
					}
//					if(returnList.size()==Integer.valueOf(showPoint)) {
//						return returnList;
//					}
					returnList.add(sMap);
				}
			}
		}else if("avg".equals(selectMethod)) {
			for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
				Avg avg = parsedBucket.getAggregations().get("avg_val");
				Double avgVal = avg.getValue();
				TopHits topHits = parsedBucket.getAggregations().get("current_val");
				SearchHits searchHits = topHits.getHits();
				SearchHit[] searchHits1 = searchHits.getHits();
				if (searchHits1 != null && searchHits1.length > 0) {
					Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
					String valStr = String.valueOf(avgVal);
					if (!"Infinity".equals(valStr) && !"-Infinity".equals(valStr)) {
						BigDecimal bd = new BigDecimal(valStr);
						bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
						sMap.put("val", bd.toString());
					} else {
						sMap.put("val", "");
					}
//					if(returnList.size()==Integer.valueOf(showPoint)) {
//						return returnList;
//					}
					returnList.add(sMap);
				}
			}
		}
		return returnList;
	}
	
	//分布图(优化)
	public static List<Map<String,Object>> selectDistributionMap(Map<String, Object> map)throws IOException{
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		String ciCodes=(String) map.get("ciCodes");
		String kpiName=(String) map.get("kpiName");
		String selectMethod=(String) map.get("selectMethod");
		String domainId=(String) map.get("domainId");
		String valUnit=(String) map.get("valUnit");
		if(startTime==null || "".equals(startTime) || endTime==null || "".equals(endTime)) {
			Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date zero = calendar.getTime();
            String zeroStr=sdf.format(zero);
            startTime=zeroStr;
            endTime=sdf.format(new Date());
		}
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		criterias.put("ci_name", ciCodes);
		criterias.put("kpi_name", kpiName);
		if(domainId!=null) {
			criterias.put("domain_id", domainId);
		}
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		if("max".equals(selectMethod)) {
				searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
						.order(BucketOrder.aggregation("max_val", false)).size(2147483647)
						.subAggregation(AggregationBuilders.max("max_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1)));
		         searchRequest.source(searchSourceBuilder);						
		}else if("min".equals(selectMethod)) {
				searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
						.order(BucketOrder.aggregation("min_val", false)).size(2147483647)
						.subAggregation(AggregationBuilders.min("min_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1)));
		         searchRequest.source(searchSourceBuilder);			
		}else if("sum".equals(selectMethod)) {
				searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
						.order(BucketOrder.aggregation("sum_val", false)).size(2147483647)
						.subAggregation(AggregationBuilders.sum("sum_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1)));
		         searchRequest.source(searchSourceBuilder);	
		}else if("avg".equals(selectMethod)) {
				searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
						.order(BucketOrder.aggregation("avg_val", false)).size(2147483647)
						.subAggregation(AggregationBuilders.avg("avg_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1)));
		         searchRequest.source(searchSourceBuilder);		
		}
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		Map<String, Aggregation> aggrMap = response.getAggregations().asMap();
		ParsedStringTerms terms = (ParsedStringTerms) aggrMap.get("groupBy");
		List<ParsedStringTerms.ParsedBucket> buckets = (List<ParsedStringTerms.ParsedBucket>) terms.getBuckets();
		if("max".equals(selectMethod)) {
			for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
				Max max = parsedBucket.getAggregations().get("max_val");
				Double maxVal = max.getValue();
				TopHits topHits = parsedBucket.getAggregations().get("current_val");
				SearchHits searchHits = topHits.getHits();
				SearchHit[] searchHits1 = searchHits.getHits();
				if (searchHits1 != null && searchHits1.length > 0) {
					Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
					String valStr = String.valueOf(maxVal);
					if (!"Infinity".equals(valStr) && !"-Infinity".equals(valStr)) {
						BigDecimal bd = new BigDecimal(valStr);
						bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
						sMap.put("val", bd.toString());
					} else {
						sMap.put("val", "");
					}
					Map<String, Object> hitNap = formatHumpName(sMap);
					boolean flag=hitNap.containsKey("valUnit");
					if(flag) {
						String valUnitStr=(String) hitNap.get("valUnit");
						if(valUnitStr==null || "".equals(valUnitStr)) {
							hitNap.put("valUnit", valUnit);
						}
					}else {
						hitNap.put("valUnit", valUnit);
					}
					returnList.add(hitNap);
				}
			}
		}else if("min".equals(selectMethod)) {
			for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
				Min min = parsedBucket.getAggregations().get("min_val");
				Double minVal = min.getValue();
				TopHits topHits = parsedBucket.getAggregations().get("current_val");
				SearchHits searchHits = topHits.getHits();
				SearchHit[] searchHits1 = searchHits.getHits();
				if (searchHits1 != null && searchHits1.length > 0) {
					Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
					String valStr = String.valueOf(minVal);
					if (!"Infinity".equals(valStr) && !"-Infinity".equals(valStr)) {
						BigDecimal bd = new BigDecimal(valStr);
						bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
						sMap.put("val", bd.toString());
					} else {
						sMap.put("val", "");
					}
					Map<String, Object> hitNap = formatHumpName(sMap);
					boolean flag=hitNap.containsKey("valUnit");
					if(flag) {
						String valUnitStr=(String) hitNap.get("valUnit");
						if(valUnitStr==null || "".equals(valUnitStr)) {
							hitNap.put("valUnit", valUnit);
						}
					}else {
						hitNap.put("valUnit", valUnit);
					}
					returnList.add(hitNap);
				}
			}
		}else if("sum".equals(selectMethod)) {
			for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
				Sum sum = parsedBucket.getAggregations().get("sum_val");
				Double sumVal = sum.getValue();
				TopHits topHits = parsedBucket.getAggregations().get("current_val");
				SearchHits searchHits = topHits.getHits();
				SearchHit[] searchHits1 = searchHits.getHits();
				if (searchHits1 != null && searchHits1.length > 0) {
					Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
					String valStr = String.valueOf(sumVal);
					if (!"Infinity".equals(valStr) && !"-Infinity".equals(valStr)) {
						BigDecimal bd = new BigDecimal(valStr);
						bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
						sMap.put("val", bd.toString());
					} else {
						sMap.put("val", "");
					}
					Map<String, Object> hitNap = formatHumpName(sMap);
					boolean flag=hitNap.containsKey("valUnit");
					if(flag) {
						String valUnitStr=(String) hitNap.get("valUnit");
						if(valUnitStr==null || "".equals(valUnitStr)) {
							hitNap.put("valUnit", valUnit);
						}
					}else {
						hitNap.put("valUnit", valUnit);
					}
					returnList.add(hitNap);
				}
			}
		}else if("avg".equals(selectMethod)) {
			for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
				Avg avg = parsedBucket.getAggregations().get("avg_val");
				Double avgVal = avg.getValue();
				TopHits topHits = parsedBucket.getAggregations().get("current_val");
				SearchHits searchHits = topHits.getHits();
				SearchHit[] searchHits1 = searchHits.getHits();
				if (searchHits1 != null && searchHits1.length > 0) {
					Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
					String valStr = String.valueOf(avgVal);
					if (!"Infinity".equals(valStr) && !"-Infinity".equals(valStr)) {
						BigDecimal bd = new BigDecimal(valStr);
						bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
						sMap.put("val", bd.toString());
					} else {
						sMap.put("val", "");
					}
					Map<String, Object> hitNap = formatHumpName(sMap);
					boolean flag=hitNap.containsKey("valUnit");
					if(flag) {
						String valUnitStr=(String) hitNap.get("valUnit");
						if(valUnitStr==null || "".equals(valUnitStr)) {
							hitNap.put("valUnit", valUnit);
						}
					}else {
						hitNap.put("valUnit", valUnit);
					}
					returnList.add(hitNap);
				}
			}
		}
		return returnList;
	}
	
	//排行图(优化)
	public static List<Map<String, Object>> getRankingChartRectification(Map<String, Object> map) throws IOException {
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		String ciCode=(String) map.get("ciCode");
		String kpiName=(String) map.get("kpiName");
		String textAlign=(String) map.get("textAlign");
		String customUnit=(String) map.get("customUnit");
		String showPoint=(String) map.get("showPoint");
		if(startTime==null || "".equals(startTime) || endTime==null || "".equals(endTime)) {
			Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date zero = calendar.getTime();
            String zeroStr=sdf.format(zero);
            startTime=zeroStr;
            endTime=sdf.format(new Date());
		}
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		criterias.put("ci_name", ciCode);
		criterias.put("kpi_name", kpiName);
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		if("max".equals(textAlign)) {
			if("desc".equals(customUnit)) {
				searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
						.order(BucketOrder.aggregation("max_val", false)).size(2147483647)
						.subAggregation(AggregationBuilders.max("max_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1)));
		         searchRequest.source(searchSourceBuilder);
			}else if("asc".equals(customUnit)) {
				searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
						.order(BucketOrder.aggregation("max_val", true)).size(2147483647)
						.subAggregation(AggregationBuilders.max("max_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1)));
		         searchRequest.source(searchSourceBuilder);
			}
			
		}else if("min".equals(textAlign)) {
			if("desc".equals(customUnit)) {
				searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
						.order(BucketOrder.aggregation("min_val", false)).size(2147483647)
						.subAggregation(AggregationBuilders.min("min_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1)));
		         searchRequest.source(searchSourceBuilder);
			}else if("asc".equals(customUnit)) {
				searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
						.order(BucketOrder.aggregation("min_val", true)).size(2147483647)
						.subAggregation(AggregationBuilders.min("min_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1)));
		         searchRequest.source(searchSourceBuilder);
			}
		}else if("sum".equals(textAlign)) {
			if("desc".equals(customUnit)) {
				searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
						.order(BucketOrder.aggregation("sum_val", false)).size(2147483647)
						.subAggregation(AggregationBuilders.sum("sum_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1)));
		         searchRequest.source(searchSourceBuilder);
			}else if("asc".equals(customUnit)) {
				searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
						.order(BucketOrder.aggregation("sum_val", true)).size(2147483647)
						.subAggregation(AggregationBuilders.sum("sum_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1)));
		         searchRequest.source(searchSourceBuilder);
			}
		}else if("avg".equals(textAlign)) {
			if("desc".equals(customUnit)) {
				searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
						.order(BucketOrder.aggregation("avg_val", false)).size(2147483647)
						.subAggregation(AggregationBuilders.avg("avg_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1)));
		         searchRequest.source(searchSourceBuilder);
			}else if("asc".equals(customUnit)) {
				searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
						.order(BucketOrder.aggregation("avg_val", true)).size(2147483647)
						.subAggregation(AggregationBuilders.avg("avg_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1)));
		         searchRequest.source(searchSourceBuilder);
			}
		}
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		Map<String, Aggregation> aggrMap = response.getAggregations().asMap();
		ParsedStringTerms terms = (ParsedStringTerms) aggrMap.get("groupBy");
		List<ParsedStringTerms.ParsedBucket> buckets = (List<ParsedStringTerms.ParsedBucket>) terms.getBuckets();
		if("max".equals(textAlign)) {
			for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
				Max max = parsedBucket.getAggregations().get("max_val");
				Double maxVal = max.getValue();
				TopHits topHits = parsedBucket.getAggregations().get("current_val");
				SearchHits searchHits = topHits.getHits();
				SearchHit[] searchHits1 = searchHits.getHits();
				if (searchHits1 != null && searchHits1.length > 0) {
					Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
					String valStr = String.valueOf(maxVal);
					if (!"Infinity".equals(valStr) && !"-Infinity".equals(valStr)) {
						BigDecimal bd = new BigDecimal(valStr);
						bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
						sMap.put("val", bd.toString());
					} else {
						sMap.put("val", "");
					}
					if(returnList.size()==Integer.valueOf(showPoint)) {
						return returnList;
					}
					returnList.add(sMap);
				}
			}
		}else if("min".equals(textAlign)) {
			for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
				Min min = parsedBucket.getAggregations().get("min_val");
				Double minVal = min.getValue();
				TopHits topHits = parsedBucket.getAggregations().get("current_val");
				SearchHits searchHits = topHits.getHits();
				SearchHit[] searchHits1 = searchHits.getHits();
				if (searchHits1 != null && searchHits1.length > 0) {
					Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
					String valStr = String.valueOf(minVal);
					if (!"Infinity".equals(valStr) && !"-Infinity".equals(valStr)) {
						BigDecimal bd = new BigDecimal(valStr);
						bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
						sMap.put("val", bd.toString());
					} else {
						sMap.put("val", "");
					}
					if(returnList.size()==Integer.valueOf(showPoint)) {
						return returnList;
					}
					returnList.add(sMap);
				}
			}
		}else if("sum".equals(textAlign)) {
			for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
				Sum sum = parsedBucket.getAggregations().get("sum_val");
				Double sumVal = sum.getValue();
				TopHits topHits = parsedBucket.getAggregations().get("current_val");
				SearchHits searchHits = topHits.getHits();
				SearchHit[] searchHits1 = searchHits.getHits();
				if (searchHits1 != null && searchHits1.length > 0) {
					Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
					String valStr = String.valueOf(sumVal);
					if (!"Infinity".equals(valStr) && !"-Infinity".equals(valStr)) {
						BigDecimal bd = new BigDecimal(valStr);
						bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
						sMap.put("val", bd.toString());
					} else {
						sMap.put("val", "");
					}
					if(returnList.size()==Integer.valueOf(showPoint)) {
						return returnList;
					}
					returnList.add(sMap);
				}
			}
		}else if("avg".equals(textAlign)) {
			for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
				Avg avg = parsedBucket.getAggregations().get("avg_val");
				Double avgVal = avg.getValue();
				TopHits topHits = parsedBucket.getAggregations().get("current_val");
				SearchHits searchHits = topHits.getHits();
				SearchHit[] searchHits1 = searchHits.getHits();
				if (searchHits1 != null && searchHits1.length > 0) {
					Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
					String valStr = String.valueOf(avgVal);
					if (!"Infinity".equals(valStr) && !"-Infinity".equals(valStr)) {
						BigDecimal bd = new BigDecimal(valStr);
						bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
						sMap.put("val", bd.toString());
					} else {
						sMap.put("val", "");
					}
					if(returnList.size()==Integer.valueOf(showPoint)) {
						return returnList;
					}
					returnList.add(sMap);
				}
			}
		}

		return returnList;
	}
	
	  /**
                * 性能查看（优化）
     *
     * @param map
     * @return
     * @throws ParseException
     */
	public static List<Map<String, Object>> getPerViewLineChartOptimization(Map<String, Object> map) throws IOException {		
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		String ciCodes=(String) map.get("ciCodes");
		String kpiNames=(String) map.get("kpiNames");
		String domainId=(String) map.get("domainId");
		Long duartion=(Long) map.get("duartion");
		String selectMethod=(String) map.get("selectMethod");
		Map<String,Object> mapValUnit=(Map<String, Object>) map.get("mapValUnit");
		String valUnit=(String) map.get("valUnit");
		List<String> ciCodeList=Arrays.asList(ciCodes.split(","));
		List<String> kpiNameList=Arrays.asList(kpiNames.split(","));
		if(startTime==null || "".equals(startTime) || endTime==null || "".equals(endTime)) {
			Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date zero = calendar.getTime();
            String zeroStr=sdf.format(zero);
            startTime=zeroStr;
            endTime=sdf.format(new Date());
		}
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		criterias.put("ci_name", ciCodes);
		criterias.put("kpi_name", kpiNames);
		if(domainId!=null) {
			criterias.put("domain_id", domainId);
		}
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		if(selectMethod==null || "".equals(selectMethod)) {
			criterias.put("sort", "occ_time");
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String indexes = String.join(",", tableList);
			List<Map<String, Object>> list = EsUtil.search(indexes, criterias);
			for(Map<String,Object> mapData:list) {
				Map<String,Object> humpMap=formatHumpName(mapData);
				res.add(humpMap);
			}
			return res;
		}
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(EsUtil.builder(criterias));
		searchSourceBuilder.size(0);
		DateHistogramAggregationBuilder occTime = AggregationBuilders.dateHistogram(occ_time).field(occ_time)
				.interval(duartion).minDocCount(0).extendedBounds(new ExtendedBounds(startTime, endTime));
		if("max".equals(selectMethod)) {
			    AbstractAggregationBuilder vals = AggregationBuilders.max("val").field("val");
			    TopHitsAggregationBuilder ciName = AggregationBuilders.topHits("ci_name").size(1);
			    //TermsAggregationBuilder ci = AggregationBuilders.terms("groupBy").field("ci_name");
			    TermsAggregationBuilder ciCodeTerm = AggregationBuilders.terms("ci_name").field("ci_name").size(2147483647);
				TermsAggregationBuilder kpiNameTerm = AggregationBuilders.terms("kpi_name").field("kpi_name").size(2147483647);
				kpiNameTerm.subAggregation(AggregationBuilders.max("val").field("val"));
				kpiNameTerm.subAggregation(AggregationBuilders.topHits("ci_name").size(1).sort("occ_time", SortOrder.DESC));
				ciCodeTerm.subAggregation(kpiNameTerm);
			    //ci.subAggregation(vals);
			    //ci.subAggregation(ciName);
			    occTime.subAggregation(ciCodeTerm); 
				searchSourceBuilder.aggregation(occTime);
				searchRequest.source(searchSourceBuilder);
		}else if("min".equals(selectMethod)) {		
		    AbstractAggregationBuilder vals = AggregationBuilders.min("val").field("val");
		    TopHitsAggregationBuilder ciName = AggregationBuilders.topHits("ci_name").size(1);
		    //TermsAggregationBuilder ci = AggregationBuilders.terms("groupBy").field("ci_name");
		    TermsAggregationBuilder ciCodeTerm = AggregationBuilders.terms("ci_name").field("ci_name").size(2147483647);
			TermsAggregationBuilder kpiNameTerm = AggregationBuilders.terms("kpi_name").field("kpi_name").size(2147483647);
			kpiNameTerm.subAggregation(AggregationBuilders.min("val").field("val"));
			kpiNameTerm.subAggregation(AggregationBuilders.topHits("ci_name").size(1).sort("occ_time", SortOrder.DESC));
			ciCodeTerm.subAggregation(kpiNameTerm);
		    //ci.subAggregation(vals);
		    //ci.subAggregation(ciName);
		    occTime.subAggregation(ciCodeTerm); 
			searchSourceBuilder.aggregation(occTime);
			searchRequest.source(searchSourceBuilder);	
		}else if("sum".equals(selectMethod)) {
			 AbstractAggregationBuilder vals = AggregationBuilders.sum("val").field("val");
			    TopHitsAggregationBuilder ciName = AggregationBuilders.topHits("ci_name").size(1);
			    //TermsAggregationBuilder ci = AggregationBuilders.terms("groupBy").field("ci_name");
			    TermsAggregationBuilder ciCodeTerm = AggregationBuilders.terms("ci_name").field("ci_name").size(2147483647);
				TermsAggregationBuilder kpiNameTerm = AggregationBuilders.terms("kpi_name").field("kpi_name").size(2147483647);
				kpiNameTerm.subAggregation(AggregationBuilders.sum("val").field("val"));
				kpiNameTerm.subAggregation(AggregationBuilders.topHits("ci_name").size(1).sort("occ_time", SortOrder.DESC));
				ciCodeTerm.subAggregation(kpiNameTerm);
			    //ci.subAggregation(vals);
			    //ci.subAggregation(ciName);
			    occTime.subAggregation(ciCodeTerm); 
				searchSourceBuilder.aggregation(occTime);
				searchRequest.source(searchSourceBuilder);
		}else if("avg".equals(selectMethod)) {
			AbstractAggregationBuilder vals = AggregationBuilders.avg("val").field("val");
		    TopHitsAggregationBuilder ciName = AggregationBuilders.topHits("ci_name").size(1);
		    //TermsAggregationBuilder ci = AggregationBuilders.terms("groupBy").field("ci_name");
		    TermsAggregationBuilder ciCodeTerm = AggregationBuilders.terms("ci_name").field("ci_name").size(2147483647);
			TermsAggregationBuilder kpiNameTerm = AggregationBuilders.terms("kpi_name").field("kpi_name").size(2147483647);
			kpiNameTerm.subAggregation(AggregationBuilders.avg("val").field("val"));
			kpiNameTerm.subAggregation(AggregationBuilders.topHits("ci_name").size(1).sort("occ_time", SortOrder.DESC));
			ciCodeTerm.subAggregation(kpiNameTerm);
		    //ci.subAggregation(vals);
		    //ci.subAggregation(ciName);
		    occTime.subAggregation(ciCodeTerm); 
			searchSourceBuilder.aggregation(occTime);
			searchRequest.source(searchSourceBuilder);
		}
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		Map<String, Aggregation> aggrMap = response.getAggregations().asMap();
		ParsedDateHistogram occTime1 = (ParsedDateHistogram) aggrMap.get(occ_time);
		//ParsedStringTerms terms = (ParsedStringTerms) aggrMap.get("groupBy");
		List<ParsedDateHistogram.ParsedBucket> buckets = (List<ParsedDateHistogram.ParsedBucket>) occTime1.getBuckets();
		
		if("max".equals(selectMethod)) {			
			for (ParsedDateHistogram.ParsedBucket parsedBucket : buckets) {
				long timeNum = -1l;
				try {
					timeNum = sdf.parse(parsedBucket.getKeyAsString()).getTime();
				} catch (ParseException e) {
				}
				Map<String, Aggregation> ciIdMap = parsedBucket.getAggregations().asMap();
				ParsedStringTerms groupCiName = (ParsedStringTerms) ciIdMap.get("ci_name");
				List<ParsedStringTerms.ParsedBucket> groupCiNameBuckets = (List<ParsedStringTerms.ParsedBucket>) groupCiName.getBuckets();
				if(groupCiNameBuckets==null || groupCiNameBuckets.size()==0) {
					for(String ciCode:ciCodeList) {
					   for(String kpiName:kpiNameList) {
						  Map<String,Object> mapData=new HashMap<String,Object>();
						  mapData.put("ciName", ciCode);
						  mapData.put("kpiName", kpiName);
						  mapData.put("val", "0");
						  mapData.put("timeNum", timeNum);
						  String valUnitStr=(String) mapValUnit.get(kpiName);
						  mapData.put("valUnit", valUnitStr);
						  res.add(mapData);
					   }					
				    }
				}
				if(groupCiNameBuckets.size()<ciCodeList.size() && groupCiNameBuckets.size()!=0) {
					Set<String> unCiCodeList=new HashSet<>();
					for (ParsedStringTerms.ParsedBucket ci_name : groupCiNameBuckets) {
						Map<String, Aggregation> aggreMap = ci_name.getAggregations().getAsMap();
						List<ParsedStringTerms.ParsedBucket> groupKpiNameBuckets = (List<ParsedStringTerms.ParsedBucket>) ((ParsedStringTerms) aggreMap.get("kpi_name")).getBuckets();
						if(groupKpiNameBuckets.size()<kpiNameList.size() && groupKpiNameBuckets.size()!=0) {
							List<String> unKpiNameList=new ArrayList<>();
							String ciCode="";
							for (ParsedStringTerms.ParsedBucket kpi_name : groupKpiNameBuckets) {
								Map<String,Object> mapData=getOneMapPerViewLineChart(kpi_name.getAggregations(), timeNum,mapValUnit);
								res.add(mapData);
								String kpiName=(String) mapData.get("kpiName");
								ciCode=(String) mapData.get("ciName");
								unKpiNameList.add(kpiName);
							}
							unCiCodeList.add(ciCode);
							for(String kpiName:kpiNameList) {
								boolean flag=unKpiNameList.contains(kpiName);
								if(!flag) {
									 Map<String,Object> mapData=new HashMap<String,Object>();
									  mapData.put("ciName", ciCode);
									  mapData.put("kpiName", kpiName);
									  mapData.put("val", "0");
									  mapData.put("timeNum", timeNum);
									  String valUnitStr=(String) mapValUnit.get(kpiName);
									  mapData.put("valUnit", valUnitStr);
									  res.add(mapData);
								}								
							}
						}else {
							for (ParsedStringTerms.ParsedBucket kpi_name : groupKpiNameBuckets) {
								List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
								Map<String,Object> mapData=getOneMapPerViewLineChart(kpi_name.getAggregations(), timeNum,mapValUnit);
								String ciCode=(String) mapData.get("ciName");
								unCiCodeList.add(ciCode);
								res.add(mapData);
							}
						}	
					}
					for(String ciCode:ciCodeList) {
						boolean flag=unCiCodeList.contains(ciCode);
						if(!flag) {
							for(String kpiName:kpiNameList) {
								  Map<String,Object> mapData=new HashMap<String,Object>();
								  mapData.put("ciName", ciCode);
								  mapData.put("kpiName", kpiName);
								  mapData.put("val", "0");
								  mapData.put("timeNum", timeNum);
								  String valUnitStr=(String) mapValUnit.get(kpiName);
								  mapData.put("valUnit", valUnitStr);
								  res.add(mapData);
							  }
						}
					}					
				}else {
					for (ParsedStringTerms.ParsedBucket ci_name : groupCiNameBuckets) {
						Map<String, Aggregation> aggreMap = ci_name.getAggregations().getAsMap();
						List<ParsedStringTerms.ParsedBucket> groupKpiNameBuckets = (List<ParsedStringTerms.ParsedBucket>) ((ParsedStringTerms) aggreMap.get("kpi_name")).getBuckets();
						if(groupKpiNameBuckets.size()<kpiNameList.size()) {
							List<String> unKpiNameList=new ArrayList<>();
							String ciCode="";
							for (ParsedStringTerms.ParsedBucket kpi_name : groupKpiNameBuckets) {
								Map<String,Object> mapData=getOneMapPerViewLineChart(kpi_name.getAggregations(), timeNum,mapValUnit);
								res.add(mapData);
								String kpiName=(String) mapData.get("kpiName");
								ciCode=(String) mapData.get("ciName");
								unKpiNameList.add(kpiName);
							}
							for(String kpiName:kpiNameList) {
								for(String unKpiName:unKpiNameList) {
									if(!kpiName.equals(unKpiName)) {
										  Map<String,Object> mapData=new HashMap<String,Object>();
										  mapData.put("ciName", ciCode);
										  mapData.put("kpiName", kpiName);
										  mapData.put("val", "0");
										  mapData.put("timeNum", timeNum);
										  String valUnitStr=(String) mapValUnit.get(kpiName);
										  mapData.put("valUnit", valUnitStr);
										  res.add(mapData);
									}
								}
							}
						}else {
							for (ParsedStringTerms.ParsedBucket kpi_name : groupKpiNameBuckets) {
								List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
								Map<String,Object> mapData=getOneMapPerViewLineChart(kpi_name.getAggregations(), timeNum,mapValUnit);
								res.add(mapData);
							}
						}
						
					}
				}
			}
		}else if("min".equals(selectMethod)) {
			for (ParsedDateHistogram.ParsedBucket parsedBucket : buckets) {
				long timeNum = -1l;
				try {
					timeNum = sdf.parse(parsedBucket.getKeyAsString()).getTime();
				} catch (ParseException e) {
				}
				Map<String, Aggregation> ciIdMap = parsedBucket.getAggregations().asMap();
				ParsedStringTerms groupCiName = (ParsedStringTerms) ciIdMap.get("ci_name");
				List<ParsedStringTerms.ParsedBucket> groupCiNameBuckets = (List<ParsedStringTerms.ParsedBucket>) groupCiName.getBuckets();
				if(groupCiNameBuckets==null || groupCiNameBuckets.size()==0) {
					for(String ciCode:ciCodeList) {
					   for(String kpiName:kpiNameList) {
						  Map<String,Object> mapData=new HashMap<String,Object>();
						  mapData.put("ciName", ciCode);
						  mapData.put("kpiName", kpiName);
						  mapData.put("val", "0");
						  mapData.put("timeNum", timeNum);
						  mapData.put("valUnit", valUnit);
						  res.add(mapData);
					   }					
				    }
				}
				if(groupCiNameBuckets.size()<ciCodeList.size() && groupCiNameBuckets.size()!=0) {
					Set<String> unCiCodeList=new HashSet<>();
					for (ParsedStringTerms.ParsedBucket ci_name : groupCiNameBuckets) {
						Map<String, Aggregation> aggreMap = ci_name.getAggregations().getAsMap();
						List<ParsedStringTerms.ParsedBucket> groupKpiNameBuckets = (List<ParsedStringTerms.ParsedBucket>) ((ParsedStringTerms) aggreMap.get("kpi_name")).getBuckets();
						if(groupKpiNameBuckets.size()<kpiNameList.size() && groupKpiNameBuckets.size()!=0) {
							List<String> unKpiNameList=new ArrayList<>();
							String ciCode="";
							for (ParsedStringTerms.ParsedBucket kpi_name : groupKpiNameBuckets) {
								Map<String,Object> mapData=getOneMapPerViewLineChart(kpi_name.getAggregations(), timeNum,mapValUnit);
								res.add(mapData);
								String kpiName=(String) mapData.get("kpiName");
								ciCode=(String) mapData.get("ciName");
								unKpiNameList.add(kpiName);
							}
							unCiCodeList.add(ciCode);
							for(String kpiName:kpiNameList) {
								boolean flag=unKpiNameList.contains(kpiName);
								if(!flag) {
									 Map<String,Object> mapData=new HashMap<String,Object>();
									  mapData.put("ciName", ciCode);
									  mapData.put("kpiName", kpiName);
									  mapData.put("val", "0");
									  mapData.put("timeNum", timeNum);
									  String valUnitStr=(String) mapValUnit.get(kpiName);
									  mapData.put("valUnit", valUnitStr);
									  res.add(mapData);
								}								
							}
						}else {
							for (ParsedStringTerms.ParsedBucket kpi_name : groupKpiNameBuckets) {
								List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
								Map<String,Object> mapData=getOneMapPerViewLineChart(kpi_name.getAggregations(), timeNum,mapValUnit);
								String ciCode=(String) mapData.get("ciName");
								unCiCodeList.add(ciCode);
								res.add(mapData);
							}
						}	
					}
					for(String ciCode:ciCodeList) {
						boolean flag=unCiCodeList.contains(ciCode);
						if(!flag) {
							for(String kpiName:kpiNameList) {
								  Map<String,Object> mapData=new HashMap<String,Object>();
								  mapData.put("ciName", ciCode);
								  mapData.put("kpiName", kpiName);
								  mapData.put("val", "0");
								  mapData.put("timeNum", timeNum);
								  String valUnitStr=(String) mapValUnit.get(kpiName);
								  mapData.put("valUnit", valUnitStr);
								  res.add(mapData);
							  }
						}
					}					
				}else {
					for (ParsedStringTerms.ParsedBucket ci_name : groupCiNameBuckets) {
						Map<String, Aggregation> aggreMap = ci_name.getAggregations().getAsMap();
						List<ParsedStringTerms.ParsedBucket> groupKpiNameBuckets = (List<ParsedStringTerms.ParsedBucket>) ((ParsedStringTerms) aggreMap.get("kpi_name")).getBuckets();
						if(groupKpiNameBuckets.size()<kpiNameList.size() && groupKpiNameBuckets.size()!=0) {
							List<String> unKpiNameList=new ArrayList<>();
							String ciCode="";
							for (ParsedStringTerms.ParsedBucket kpi_name : groupKpiNameBuckets) {
								Map<String,Object> mapData=getOneMapPerViewLineChart(kpi_name.getAggregations(), timeNum,mapValUnit);
								res.add(mapData);
								String kpiName=(String) mapData.get("kpiName");
								ciCode=(String) mapData.get("ciName");
								unKpiNameList.add(kpiName);
							}
							for(String kpiName:kpiNameList) {
								boolean flag=unKpiNameList.contains(kpiName);
								if(!flag) {
									Map<String,Object> mapData=new HashMap<String,Object>();
									  mapData.put("ciName", ciCode);
									  mapData.put("kpiName", kpiName);
									  mapData.put("val", "0");
									  mapData.put("timeNum", timeNum);
									  String valUnitStr=(String) mapValUnit.get(kpiName);
									  mapData.put("valUnit", valUnitStr);
									  res.add(mapData);
								}						
							}
						}else {
							for (ParsedStringTerms.ParsedBucket kpi_name : groupKpiNameBuckets) {
								List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
								Map<String,Object> mapData=getOneMapPerViewLineChart(kpi_name.getAggregations(), timeNum,mapValUnit);
								res.add(mapData);
							}
						}
					}
				}

			}
		}else if("sum".equals(selectMethod)) {
			for (ParsedDateHistogram.ParsedBucket parsedBucket : buckets) {
				long timeNum = -1l;
				try {
					timeNum = sdf.parse(parsedBucket.getKeyAsString()).getTime();
				} catch (ParseException e) {
				}
				Map<String, Aggregation> ciIdMap = parsedBucket.getAggregations().asMap();
				ParsedStringTerms groupCiName = (ParsedStringTerms) ciIdMap.get("ci_name");
				List<ParsedStringTerms.ParsedBucket> groupCiNameBuckets = (List<ParsedStringTerms.ParsedBucket>) groupCiName.getBuckets();
				if(groupCiNameBuckets==null || groupCiNameBuckets.size()==0) {
					for(String ciCode:ciCodeList) {
					   for(String kpiName:kpiNameList) {
						  Map<String,Object> mapData=new HashMap<String,Object>();
						  mapData.put("ciName", ciCode);
						  mapData.put("kpiName", kpiName);
						  mapData.put("val", "0");
						  mapData.put("timeNum", timeNum);
						  mapData.put("valUnit", valUnit);
						  res.add(mapData);
					   }					
				    }
				}
				if(groupCiNameBuckets.size()<ciCodeList.size() && groupCiNameBuckets.size()!=0) {
					Set<String> unCiCodeList=new HashSet<>();
					for (ParsedStringTerms.ParsedBucket ci_name : groupCiNameBuckets) {
						Map<String, Aggregation> aggreMap = ci_name.getAggregations().getAsMap();
						List<ParsedStringTerms.ParsedBucket> groupKpiNameBuckets = (List<ParsedStringTerms.ParsedBucket>) ((ParsedStringTerms) aggreMap.get("kpi_name")).getBuckets();
						if(groupKpiNameBuckets.size()<kpiNameList.size() && groupKpiNameBuckets.size()!=0) {
							List<String> unKpiNameList=new ArrayList<>();
							String ciCode="";
							for (ParsedStringTerms.ParsedBucket kpi_name : groupKpiNameBuckets) {
								Map<String,Object> mapData=getOneMapPerViewLineChart(kpi_name.getAggregations(), timeNum,mapValUnit);
								res.add(mapData);
								String kpiName=(String) mapData.get("kpiName");
								ciCode=(String) mapData.get("ciName");
								unKpiNameList.add(kpiName);
							}
							unCiCodeList.add(ciCode);
							for(String kpiName:kpiNameList) {
								boolean flag=unKpiNameList.contains(kpiName);
								if(!flag) {
									 Map<String,Object> mapData=new HashMap<String,Object>();
									  mapData.put("ciName", ciCode);
									  mapData.put("kpiName", kpiName);
									  mapData.put("val", "0");
									  mapData.put("timeNum", timeNum);
									  String valUnitStr=(String) mapValUnit.get(kpiName);
									  mapData.put("valUnit", valUnitStr);
									  res.add(mapData);
								}								
							}
						}else {
							for (ParsedStringTerms.ParsedBucket kpi_name : groupKpiNameBuckets) {
								List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
								Map<String,Object> mapData=getOneMapPerViewLineChart(kpi_name.getAggregations(), timeNum,mapValUnit);
								String ciCode=(String) mapData.get("ciName");
								unCiCodeList.add(ciCode);
								res.add(mapData);
							}
						}	
					}
					for(String ciCode:ciCodeList) {
						boolean flag=unCiCodeList.contains(ciCode);
						if(!flag) {
							for(String kpiName:kpiNameList) {
								  Map<String,Object> mapData=new HashMap<String,Object>();
								  mapData.put("ciName", ciCode);
								  mapData.put("kpiName", kpiName);
								  mapData.put("val", "0");
								  mapData.put("timeNum", timeNum);
								  String valUnitStr=(String) mapValUnit.get(kpiName);
								  mapData.put("valUnit", valUnitStr);
								  res.add(mapData);
							  }
						}
					}					
				}else {
					for (ParsedStringTerms.ParsedBucket ci_name : groupCiNameBuckets) {
						Map<String, Aggregation> aggreMap = ci_name.getAggregations().getAsMap();
						List<ParsedStringTerms.ParsedBucket> groupKpiNameBuckets = (List<ParsedStringTerms.ParsedBucket>) ((ParsedStringTerms) aggreMap.get("kpi_name")).getBuckets();
						if(groupKpiNameBuckets.size()<kpiNameList.size() && groupKpiNameBuckets.size()!=0) {
							List<String> unKpiNameList=new ArrayList<>();
							String ciCode="";
							for (ParsedStringTerms.ParsedBucket kpi_name : groupKpiNameBuckets) {
								Map<String,Object> mapData=getOneMapPerViewLineChart(kpi_name.getAggregations(), timeNum,mapValUnit);
								res.add(mapData);
								String kpiName=(String) mapData.get("kpiName");
								ciCode=(String) mapData.get("ciName");
								unKpiNameList.add(kpiName);
							}
							for(String kpiName:kpiNameList) {
								boolean flag=unKpiNameList.contains(kpiName);
								if(!flag) {
									Map<String,Object> mapData=new HashMap<String,Object>();
									  mapData.put("ciName", ciCode);
									  mapData.put("kpiName", kpiName);
									  mapData.put("val", "0");
									  mapData.put("timeNum", timeNum);
									  String valUnitStr=(String) mapValUnit.get(kpiName);
									  mapData.put("valUnit", valUnitStr);
									  res.add(mapData);
								}							
							}
						}else {
							for (ParsedStringTerms.ParsedBucket kpi_name : groupKpiNameBuckets) {
								List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
								Map<String,Object> mapData=getOneMapPerViewLineChart(kpi_name.getAggregations(), timeNum,mapValUnit);
								res.add(mapData);
							}
						}
					}
				}
	
			}
		}else if("avg".equals(selectMethod)) {
			for (ParsedDateHistogram.ParsedBucket parsedBucket : buckets) {
				long timeNum = -1l;
				try {
					timeNum = sdf.parse(parsedBucket.getKeyAsString()).getTime();
				} catch (ParseException e) {
				}
				Map<String, Aggregation> ciIdMap = parsedBucket.getAggregations().asMap();
				ParsedStringTerms groupCiName = (ParsedStringTerms) ciIdMap.get("ci_name");
				List<ParsedStringTerms.ParsedBucket> groupCiNameBuckets = (List<ParsedStringTerms.ParsedBucket>) groupCiName.getBuckets();
				if(groupCiNameBuckets==null || groupCiNameBuckets.size()==0) {
					for(String ciCode:ciCodeList) {
					   for(String kpiName:kpiNameList) {
						  Map<String,Object> mapData=new HashMap<String,Object>();
						  mapData.put("ciName", ciCode);
						  mapData.put("kpiName", kpiName);
						  mapData.put("val", "0");
						  mapData.put("timeNum", timeNum);
						  mapData.put("valUnit", valUnit);
						  res.add(mapData);
					   }					
				    }
				}
				if(groupCiNameBuckets.size()<ciCodeList.size() && groupCiNameBuckets.size()!=0) {
					Set<String> unCiCodeList=new HashSet<>();
					for (ParsedStringTerms.ParsedBucket ci_name : groupCiNameBuckets) {
						Map<String, Aggregation> aggreMap = ci_name.getAggregations().getAsMap();
						List<ParsedStringTerms.ParsedBucket> groupKpiNameBuckets = (List<ParsedStringTerms.ParsedBucket>) ((ParsedStringTerms) aggreMap.get("kpi_name")).getBuckets();
						if(groupKpiNameBuckets.size()<kpiNameList.size() && groupKpiNameBuckets.size()!=0) {
							List<String> unKpiNameList=new ArrayList<>();
							String ciCode="";
							for (ParsedStringTerms.ParsedBucket kpi_name : groupKpiNameBuckets) {
								Map<String,Object> mapData=getOneMapPerViewLineChart(kpi_name.getAggregations(), timeNum,mapValUnit);
								res.add(mapData);
								String kpiName=(String) mapData.get("kpiName");
								ciCode=(String) mapData.get("ciName");
								unKpiNameList.add(kpiName);
							}
							unCiCodeList.add(ciCode);
							for(String kpiName:kpiNameList) {
								boolean flag=unKpiNameList.contains(kpiName);
								if(!flag) {
									 Map<String,Object> mapData=new HashMap<String,Object>();
									  mapData.put("ciName", ciCode);
									  mapData.put("kpiName", kpiName);
									  mapData.put("val", "0");
									  mapData.put("timeNum", timeNum);
									  String valUnitStr=(String) mapValUnit.get(kpiName);
									  mapData.put("valUnit", valUnitStr);
									  res.add(mapData);
								}								
							}
						}else {
							for (ParsedStringTerms.ParsedBucket kpi_name : groupKpiNameBuckets) {
								List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
								Map<String,Object> mapData=getOneMapPerViewLineChart(kpi_name.getAggregations(), timeNum,mapValUnit);
								String ciCode=(String) mapData.get("ciName");
								unCiCodeList.add(ciCode);
								res.add(mapData);
							}
						}	
					}
					for(String ciCode:ciCodeList) {
						boolean flag=unCiCodeList.contains(ciCode);
						if(!flag) {
							for(String kpiName:kpiNameList) {
								  Map<String,Object> mapData=new HashMap<String,Object>();
								  mapData.put("ciName", ciCode);
								  mapData.put("kpiName", kpiName);
								  mapData.put("val", "0");
								  mapData.put("timeNum", timeNum);
								  String valUnitStr=(String) mapValUnit.get(kpiName);
								  mapData.put("valUnit", valUnitStr);
								  res.add(mapData);
							  }
						}
					}					
				}else {
					for (ParsedStringTerms.ParsedBucket ci_name : groupCiNameBuckets) {
						Map<String, Aggregation> aggreMap = ci_name.getAggregations().getAsMap();
						List<ParsedStringTerms.ParsedBucket> groupKpiNameBuckets = (List<ParsedStringTerms.ParsedBucket>) ((ParsedStringTerms) aggreMap.get("kpi_name")).getBuckets();
						if(groupKpiNameBuckets.size()<kpiNameList.size() && groupKpiNameBuckets.size()!=0) {
							List<String> unKpiNameList=new ArrayList<>();
							String ciCode="";
							for (ParsedStringTerms.ParsedBucket kpi_name : groupKpiNameBuckets) {
								Map<String,Object> mapData=getOneMapPerViewLineChart(kpi_name.getAggregations(), timeNum,mapValUnit);
								res.add(mapData);
								String kpiName=(String) mapData.get("kpiName");
								ciCode=(String) mapData.get("ciName");
								unKpiNameList.add(kpiName);
							}
							for(String kpiName:kpiNameList) {
								boolean flag=unKpiNameList.contains(kpiName);
								if(!flag) {
									Map<String,Object> mapData=new HashMap<String,Object>();
									  mapData.put("ciName", ciCode);
									  mapData.put("kpiName", kpiName);
									  mapData.put("val", "0");
									  mapData.put("timeNum", timeNum);
									  String valUnitStr=(String) mapValUnit.get(kpiName);
									  mapData.put("valUnit", valUnitStr);
									  res.add(mapData);
								}
							}
						}else {
							for (ParsedStringTerms.ParsedBucket kpi_name : groupKpiNameBuckets) {
								List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
								Map<String,Object> mapData=getOneMapPerViewLineChart(kpi_name.getAggregations(), timeNum,mapValUnit);
								res.add(mapData);
							}
						}
					}
				}

			}
		}
		return res;
	}
	
	//热力图(优化)
	public static List<Map<String, Object>> selectThermodynamicDiagram(Map<String, Object> map) throws IOException {		
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		String ciCodes=(String) map.get("ciCodes");
		String kpiName=(String) map.get("kpiName");
		String domainId=(String) map.get("domainId");
		Long duartion=(Long) map.get("duartion");
		String selectMethod=(String) map.get("selectMethod");
		String valUnit=(String) map.get("valUnit");
		List<String> ciCodeList=Arrays.asList(ciCodes.split(","));
		if(startTime==null || "".equals(startTime) || endTime==null || "".equals(endTime)) {
			Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date zero = calendar.getTime();
            String zeroStr=sdf.format(zero);
            startTime=zeroStr;
            endTime=sdf.format(new Date());
		}
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		criterias.put("ci_name", ciCodes);
		criterias.put("kpi_name", kpiName);
		if(domainId!=null) {
			criterias.put("domain_id", domainId);
		}
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(EsUtil.builder(criterias));
		searchSourceBuilder.size(0);
		DateTimeZone aaabbb=DateTimeZone.forID("Asia/Shanghai");
		DateHistogramAggregationBuilder occTime = AggregationBuilders.dateHistogram(occ_time).field(occ_time)
				.interval(duartion).minDocCount(0).extendedBounds(new ExtendedBounds(startTime, endTime));
		long aaa=1623170145000L;
		System.out.println(Math.floor((aaa) / 1800000) * 1800000 + 0);
		if("max".equals(selectMethod)) {
//				searchSourceBuilder.query(EsUtil.builder(criterias)).size(0).aggregation(occTime)
//				.aggregation(AggregationBuilders.terms("groupBy").field("ci_name")
//						.order(BucketOrder.aggregation("max_val", false)).size(2147483647)
//						.subAggregation(AggregationBuilders.max("max_val").field("val")).subAggregation(
//								AggregationBuilders.topHits("current_val").size(1)));
//		         searchRequest.source(searchSourceBuilder);
			    AbstractAggregationBuilder vals = AggregationBuilders.max("val").field("val");
			    TopHitsAggregationBuilder ciName = AggregationBuilders.topHits("ci_name").size(1);
			    TermsAggregationBuilder ci = AggregationBuilders.terms("groupBy").field("ci_name");
			    ci.subAggregation(vals);
			    ci.subAggregation(ciName);
			    occTime.subAggregation(ci); 
				searchSourceBuilder.aggregation(occTime);
				searchRequest.source(searchSourceBuilder);
		}else if("min".equals(selectMethod)) {		
		    AbstractAggregationBuilder vals = AggregationBuilders.min("val").field("val");
		    TopHitsAggregationBuilder ciName = AggregationBuilders.topHits("ci_name").size(1);
		    TermsAggregationBuilder ci = AggregationBuilders.terms("groupBy").field("ci_name");
		    ci.subAggregation(vals);
		    ci.subAggregation(ciName);
		    occTime.subAggregation(ci); 
			searchSourceBuilder.aggregation(occTime);
			searchRequest.source(searchSourceBuilder);	
		}else if("sum".equals(selectMethod)) {
			 AbstractAggregationBuilder vals = AggregationBuilders.sum("val").field("val");
			    TopHitsAggregationBuilder ciName = AggregationBuilders.topHits("ci_name").size(1);
			    TermsAggregationBuilder ci = AggregationBuilders.terms("groupBy").field("ci_name");
			    ci.subAggregation(vals);
			    ci.subAggregation(ciName);
			    occTime.subAggregation(ci); 
				searchSourceBuilder.aggregation(occTime);
				searchRequest.source(searchSourceBuilder);
		}else if("avg".equals(selectMethod)) {
			AbstractAggregationBuilder vals = AggregationBuilders.avg("val").field("val");
		    TopHitsAggregationBuilder ciName = AggregationBuilders.topHits("ci_name").size(1);
		    TermsAggregationBuilder ci = AggregationBuilders.terms("groupBy").field("ci_name");
		    ci.subAggregation(vals);
		    ci.subAggregation(ciName);
		    occTime.subAggregation(ci); 
			searchSourceBuilder.aggregation(occTime);
			searchRequest.source(searchSourceBuilder);
		}
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		Map<String, Aggregation> aggrMap = response.getAggregations().asMap();
		ParsedDateHistogram occTime1 = (ParsedDateHistogram) aggrMap.get(occ_time);
		//ParsedStringTerms terms = (ParsedStringTerms) aggrMap.get("groupBy");
		List<ParsedDateHistogram.ParsedBucket> buckets = (List<ParsedDateHistogram.ParsedBucket>) occTime1.getBuckets();
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		if("max".equals(selectMethod)) {			
			for (ParsedDateHistogram.ParsedBucket parsedBucket : buckets) {
				long timeNum = -1l;
				try {
					timeNum = sdf.parse(parsedBucket.getKeyAsString()).getTime();
				} catch (ParseException e) {
				}
				Map<String, Aggregation> ciIdMap = parsedBucket.getAggregations().asMap();
				ParsedStringTerms groupBy = (ParsedStringTerms) ciIdMap.get("groupBy");
				if (groupBy != null) {
					@SuppressWarnings("unchecked")
					List<ParsedStringTerms.ParsedBucket> buckets2 = (List<ParsedStringTerms.ParsedBucket>) groupBy
							.getBuckets();
					if(buckets2==null || buckets2.size()==0) {
						for(String ciCode:ciCodeList) {
							Map<String,Object> mapData=new HashMap<String,Object>();
							mapData.put("ciName", ciCode);
							mapData.put("kpiName", kpiName);
							mapData.put("val", "0");
							mapData.put("timeNum", timeNum);
							mapData.put("valUnit", valUnit);
							res.add(mapData);
						}
					}else {
						List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
						for (ParsedStringTerms.ParsedBucket parsedBucket2 : buckets2) {
							Map<String,Object> mapData=getOneMapThermodynamicDiagram(parsedBucket2.getAggregations(), timeNum,valUnit);
							listData.add(mapData);
						}
						if(ciCodeList.size()!=listData.size()) {
							    List<String> listCiCodes=new ArrayList<String>();
								for(Map<String,Object> mapData:listData) {
									String ciName=(String) mapData.get("ciName");
									boolean flag=ciCodeList.contains(ciName);
									if(flag) {
										res.add(mapData);
										listCiCodes.add(ciName);
									}
//									else {
//										Map<String,Object> mapData1=new HashMap<String,Object>();
//										mapData1.put("ciName", ciName);
//										mapData1.put("kpiName", kpiName);
//										mapData1.put("val", "0");
//										mapData1.put("timeNum", timeNum);
//										res.add(mapData);
//									}
								}
								for(String ciCode:ciCodeList) {
									boolean flag=listCiCodes.contains(ciCode);
									if(!flag) {
										Map<String,Object> mapData=new HashMap<String,Object>();
										mapData.put("ciName", ciCode);
										mapData.put("kpiName", kpiName);
										mapData.put("val", "0");
										mapData.put("timeNum", timeNum);
										mapData.put("valUnit", valUnit);
										res.add(mapData);
									}
								}
						}else {
							for(Map<String,Object> mapData:listData) {
								res.add(mapData);
							}
						}
					}

				} else {
					res.add(getOneMap(parsedBucket.getAggregations(), timeNum));
				}
				
//				Max max = parsedBucket.getAggregations().get("max_val");
//				Double maxVal = max.getValue();
//				TopHits topHits = parsedBucket.getAggregations().get("current_val");
//				SearchHits searchHits = topHits.getHits();
//				SearchHit[] searchHits1 = searchHits.getHits();
//				if (searchHits1 != null && searchHits1.length > 0) {
//					Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
//					String valStr = String.valueOf(maxVal);
//					if (!"Infinity".equals(valStr) && !"-Infinity".equals(valStr)) {
//						BigDecimal bd = new BigDecimal(valStr);
//						bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
//						sMap.put("val", bd.toString());
//					} else {
//						sMap.put("val", "");
//					}					
//					returnList.add(sMap);
//				}
			}
		}else if("min".equals(selectMethod)) {
			for (ParsedDateHistogram.ParsedBucket parsedBucket : buckets) {
				long timeNum = -1l;
				try {
					timeNum = sdf.parse(parsedBucket.getKeyAsString()).getTime();
				} catch (ParseException e) {
				}
				Map<String, Aggregation> ciIdMap = parsedBucket.getAggregations().asMap();
				ParsedStringTerms groupBy = (ParsedStringTerms) ciIdMap.get("groupBy");
				if (groupBy != null) {
					@SuppressWarnings("unchecked")
					List<ParsedStringTerms.ParsedBucket> buckets2 = (List<ParsedStringTerms.ParsedBucket>) groupBy
							.getBuckets();
					if(buckets2==null || buckets2.size()==0) {
						for(String ciCode:ciCodeList) {
							Map<String,Object> mapData=new HashMap<String,Object>();
							mapData.put("ciName", ciCode);
							mapData.put("kpiName", kpiName);
							mapData.put("val", "0");
							mapData.put("timeNum", timeNum);
							mapData.put("valUnit", valUnit);
							res.add(mapData);
						}
					}else {
						List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
						for (ParsedStringTerms.ParsedBucket parsedBucket2 : buckets2) {
							Map<String,Object> mapData=getOneMapThermodynamicDiagram(parsedBucket2.getAggregations(), timeNum,valUnit);
							listData.add(mapData);
						}
						if(ciCodeList.size()!=listData.size()) {
						    List<String> listCiCodes=new ArrayList<String>();
							for(Map<String,Object> mapData:listData) {
								String ciName=(String) mapData.get("ciName");
								boolean flag=ciCodeList.contains(ciName);
								if(flag) {
									res.add(mapData);
									listCiCodes.add(ciName);
								}
							}
							for(String ciCode:ciCodeList) {
								boolean flag=listCiCodes.contains(ciCode);
								if(!flag) {
									Map<String,Object> mapData=new HashMap<String,Object>();
									mapData.put("ciName", ciCode);
									mapData.put("kpiName", kpiName);
									mapData.put("val", "0");
									mapData.put("timeNum", timeNum);
									mapData.put("valUnit", valUnit);
									res.add(mapData);
								}
							}
						}else {
							for(Map<String,Object> mapData:listData) {
								res.add(mapData);
							}
						}
					}
					
				} else {
					res.add(getOneMap(parsedBucket.getAggregations(), timeNum));
				}
			}
		}else if("sum".equals(selectMethod)) {
			for (ParsedDateHistogram.ParsedBucket parsedBucket : buckets) {
				long timeNum = -1l;
				try {
					timeNum = sdf.parse(parsedBucket.getKeyAsString()).getTime();
				} catch (ParseException e) {
				}
				Map<String, Aggregation> ciIdMap = parsedBucket.getAggregations().asMap();
				ParsedStringTerms groupBy = (ParsedStringTerms) ciIdMap.get("groupBy");
				if (groupBy != null) {
					@SuppressWarnings("unchecked")
					List<ParsedStringTerms.ParsedBucket> buckets2 = (List<ParsedStringTerms.ParsedBucket>) groupBy
							.getBuckets();
					if(buckets2==null || buckets2.size()==0) {
						for(String ciCode:ciCodeList) {
							Map<String,Object> mapData=new HashMap<String,Object>();
							mapData.put("ciName", ciCode);
							mapData.put("kpiName", kpiName);
							mapData.put("val", "0");
							mapData.put("timeNum", timeNum);
							mapData.put("valUnit", valUnit);
							res.add(mapData);
						}
					}else {
						List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
						for (ParsedStringTerms.ParsedBucket parsedBucket2 : buckets2) {
							Map<String,Object> mapData=getOneMapThermodynamicDiagram(parsedBucket2.getAggregations(), timeNum,valUnit);
							listData.add(mapData);
						}
						if(ciCodeList.size()!=listData.size()) {
						    List<String> listCiCodes=new ArrayList<String>();
							for(Map<String,Object> mapData:listData) {
								String ciName=(String) mapData.get("ciName");
								boolean flag=ciCodeList.contains(ciName);
								if(flag) {
									res.add(mapData);
									listCiCodes.add(ciName);
								}
							}
							for(String ciCode:ciCodeList) {
								boolean flag=listCiCodes.contains(ciCode);
								if(!flag) {
									Map<String,Object> mapData=new HashMap<String,Object>();
									mapData.put("ciName", ciCode);
									mapData.put("kpiName", kpiName);
									mapData.put("val", "0");
									mapData.put("timeNum", timeNum);
									mapData.put("valUnit", valUnit);
									res.add(mapData);
								}
							}
						}else {
							for(Map<String,Object> mapData:listData) {
								res.add(mapData);
							}
						}
					}
					
				} else {
					res.add(getOneMap(parsedBucket.getAggregations(), timeNum));
				}
			}
		}else if("avg".equals(selectMethod)) {
			for (ParsedDateHistogram.ParsedBucket parsedBucket : buckets) {
				long timeNum = -1l;
				try {
					timeNum = sdf.parse(parsedBucket.getKeyAsString()).getTime();
				} catch (ParseException e) {
				}
				Map<String, Aggregation> ciIdMap = parsedBucket.getAggregations().asMap();
				ParsedStringTerms groupBy = (ParsedStringTerms) ciIdMap.get("groupBy");
				if (groupBy != null) {
					@SuppressWarnings("unchecked")
					List<ParsedStringTerms.ParsedBucket> buckets2 = (List<ParsedStringTerms.ParsedBucket>) groupBy
							.getBuckets();
					if(buckets2==null || buckets2.size()==0) {
						for(String ciCode:ciCodeList) {
							Map<String,Object> mapData=new HashMap<String,Object>();
							mapData.put("ciName", ciCode);
							mapData.put("kpiName", kpiName);
							mapData.put("val", "0");
							mapData.put("timeNum", timeNum);
							mapData.put("valUnit", valUnit);
							res.add(mapData);
						}
					}else {
						List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
						for (ParsedStringTerms.ParsedBucket parsedBucket2 : buckets2) {
							Map<String,Object> mapData=getOneMapThermodynamicDiagram(parsedBucket2.getAggregations(), timeNum,valUnit);
							listData.add(mapData);
						}
						if(ciCodeList.size()!=listData.size()) {
						    List<String> listCiCodes=new ArrayList<String>();
							for(Map<String,Object> mapData:listData) {
								String ciName=(String) mapData.get("ciName");
								boolean flag=ciCodeList.contains(ciName);
								if(flag) {
									res.add(mapData);
									listCiCodes.add(ciName);
								}
							}
							for(String ciCode:ciCodeList) {
								boolean flag=listCiCodes.contains(ciCode);
								if(!flag) {
									Map<String,Object> mapData=new HashMap<String,Object>();
									mapData.put("ciName", ciCode);
									mapData.put("kpiName", kpiName);
									mapData.put("val", "0");
									mapData.put("timeNum", timeNum);
									mapData.put("valUnit", valUnit);
									res.add(mapData);
								}
							}
						}else {
							for(Map<String,Object> mapData:listData) {
								res.add(mapData);
							}
						}
					}
				} else {
					res.add(getOneMap(parsedBucket.getAggregations(), timeNum));
				}
			}
		}

		return res;
	}

	public static List<Map<String, Object>> selectPmvEsByCjsj(String startTime, String endTime) throws IOException {
		List<String> tables=new ArrayList<String>();
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=cjsj", startTime);
		criterias.put("<=cjsj", endTime);
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("ciTypeId_kpiId")
						.script(new Script("doc['ci_type_id'] +'#'+doc['kpi_id']")).size(2147483647)
						.subAggregation(AggregationBuilders.max("max_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1).sort("kpi_id", SortOrder.DESC)));
		searchRequest.source(searchSourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		Map<String, Aggregation> aggrMap = response.getAggregations().asMap();
		ParsedStringTerms terms = (ParsedStringTerms) aggrMap.get("ciTypeId_kpiId");
		List<ParsedStringTerms.ParsedBucket> buckets = (List<ParsedStringTerms.ParsedBucket>) terms.getBuckets();
		for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
			TopHits topHits = parsedBucket.getAggregations().get("current_val");
			SearchHits searchHits = topHits.getHits();
			SearchHit[] searchHits1 = searchHits.getHits();
			if (searchHits1 != null && searchHits1.length > 0) {
				Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
				Map<String, Object> hitNap = formatHumpName(sMap);
				returnList.add(hitNap);
			}
		}
		return returnList;
	}

	// pmv展示告警查询性能曲线
	public static List<Map<String, Object>> findPerformanceByKpiId(Map<Object, Object> params) throws IOException {
		List<String> tables=new ArrayList<String>();
		String kpiIds = (String) params.get("kpiIdList");
		String startTime = (String) params.get("startTime");
		String endTime = (String) params.get("endTime");
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		criterias.put("kpi_id", kpiIds);
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(EsUtil.builder(criterias)).size(10000).aggregation(AggregationBuilders
				.terms("groupBy").field("val").subAggregation(AggregationBuilders.count("cVal").field("val"))
		/*
		 * (AggregationBuilders.dateHistogram("kpiV")
		 * .dateHistogramInterval(DateHistogramInterval.HOUR)//设置间隔时间
		 * .field("ci_id")//指定时间字段 .minDocCount(0)//返回空桶 .format("HH:mm")
		 * .extendedBounds(new ExtendedBounds(startTime, endTime))//设定范围
		 */
		);
		searchRequest.source(searchSourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		Aggregations aggregations = response.getAggregations();
		Map<String, Aggregation> stringAggregationMap = aggregations.asMap();
		ParsedLongTerms terms = (ParsedLongTerms) stringAggregationMap.get("groupBy");
		List<ParsedLongTerms.ParsedBucket> buckets = (List<ParsedLongTerms.ParsedBucket>) terms.getBuckets();
		for (ParsedLongTerms.ParsedBucket searchHit : buckets) {
			Map<String, Object> map = new HashMap<>();
			map.put("name", searchHit.getKey());
			map.put("value", searchHit.getDocCount());
			returnList.add(map);
		}

		return returnList;
	}
	
	//pmv展示告警查询性能曲线
    public static List<Map<String, Object>> findPerformanceByKpiIdToJniom(Map<Object, Object> params) throws IOException {
    	List<String> tables=new ArrayList<String>();
        String kpiIds = (String) params.get("kpiIdList");
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        Map<String, Object> criterias = new HashMap<String, Object>();
        criterias.put(">=occ_time", startTime);
        criterias.put("<=occ_time", endTime);
        criterias.put("kpi_id", kpiIds);
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        RestHighLevelClient client = EsTcpClient.getClient();
        List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
        String [] datesArray = tableList.toArray(new String[tableList.size()]);
        SearchRequest searchRequest = new SearchRequest(datesArray);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(EsUtil.builder(criterias)).size(10000)
                .aggregation(AggregationBuilders.terms("groupBy").field("val")
                                .subAggregation(AggregationBuilders.count("cVal").field("val")
                                )
                        /*(AggregationBuilders.dateHistogram("kpiV")
                                .dateHistogramInterval(DateHistogramInterval.HOUR)//设置间隔时间
                                .field("ci_id")//指定时间字段
                                .minDocCount(0)//返回空桶
                                .format("HH:mm")
                                .extendedBounds(new ExtendedBounds(startTime, endTime))//设定范围*/
                );
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations aggregations = response.getAggregations();
        Map<String, Aggregation> stringAggregationMap = aggregations.asMap();
        ParsedLongTerms terms = (ParsedLongTerms) stringAggregationMap.get("groupBy");
        List<ParsedLongTerms.ParsedBucket> buckets = (List<ParsedLongTerms.ParsedBucket>) terms.getBuckets();
        for (ParsedLongTerms.ParsedBucket searchHit : buckets) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", searchHit.getKey());
            map.put("value", searchHit.getDocCount());
            returnList.add(map);
        }

        return returnList;
    }

	public static List<PmvPerformanceCurrentInfo> getPerLastTime(String startDate, String endDate, String jsons) {
		List<String> tables=new ArrayList<String>();
		// 获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s = sdf.format(new Date());
		if (StringUtils.isEmpty(startDate)) {
			startDate = s + " 00:00:00";
		}
		if (StringUtils.isEmpty(endDate)) {
			endDate = s + " 23:59:59";
		}

		List<PmvPerformanceCurrentInfo> pmvPerformanceCurrentInfos = null;
		try {
			// 初始化查询条件
			Map<String, Object> criterias = new HashMap<String, Object>();
//            if (!StringUtils.isEmpty(startDate)) {
			criterias.put(">=occ_time", startDate);
			criterias.put("<=occ_time", endDate);
//            } else {
//                //获取当前时间
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
//                String s = sdf.format(new Date());
//                criterias.put(">=occ_time", s + " 00:00:00");
//                criterias.put("<=occ_time", s + " 23:59:59");
//            }
			criterias.put("kpi_id", jsons);
			// 初始化一个高级客户端
//            RestHighLevelClient client = new RestHighLevelClient(
//                    RestClient.builder(
//                            new HttpHost("45.125.46.128", 9200, "http")));
			try {
				//根据startTime和endTime截取日期字段进行ES跨表查询
				long nd = 1000 * 24 * 60 * 60;			
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				Date startTimeDate=sdf1.parse(startDate);
				Date endTimeDate=sdf1.parse(endDate);
				String startTimeStr=sdf2.format(startTimeDate);
				String endTimeStr=sdf2.format(endTimeDate);
				if(startTimeStr.equals(endTimeStr)) {
					String table=iom_pmv_performance+"-"+startTimeStr;
					tables.add(table);
				}else {
					long diff = sdf2.parse(endDate).getTime() - sdf2.parse(startDate).getTime();
					// 计算差多少天
				    long day = diff / nd;
				    int dayInt=(int)day;
				    for(int i=1;i<=dayInt;i++) {
				    	Calendar calendar = Calendar.getInstance();  
			            calendar.setTime(startTimeDate);  
			            calendar.add(Calendar.DAY_OF_MONTH, +i);  
			            Date date = calendar.getTime();
			            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
			            String time=sdf3.format(date);
			            if(time.equals(endTimeStr)) {
			            	if(i==1) {
			            		tables.add(iom_pmv_performance+"-"+startTimeStr);
				            	tables.add(iom_pmv_performance+"-"+endTimeStr);
				            	break;
			            	}else {
			            		tables.add(iom_pmv_performance+"-"+endTimeStr);
			            		break;
			            	}			            	
			            }else {
			            	if(i==1) {
			            		tables.add(iom_pmv_performance+"-"+startTimeStr);
				            	tables.add(iom_pmv_performance+"-"+time);
			            	}else {
				            	tables.add(iom_pmv_performance+"-"+time);
			            	}
			            	
			            }
				    }
				}
			} catch (Exception e) {
	           e.printStackTrace();
			}
			RestHighLevelClient client = EsTcpClient.getClient();
			// 查询方式
			List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String [] datesArray = tableList.toArray(new String[tableList.size()]);
			SearchRequest searchRequest = new SearchRequest(datesArray);
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.size(1);
			searchSourceBuilder.sort("occ_time", SortOrder.DESC);
			// 设置超时时间
			searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
			searchSourceBuilder.query(EsUtil.builder(criterias));
			searchRequest.source(searchSourceBuilder);
			// 获取返回值
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			// 解析返回值
			SearchHits searchHits = searchResponse.getHits();
			SearchHit[] searchHits1 = searchHits.getHits();
			if (searchHits1.length > 0) {
				pmvPerformanceCurrentInfos = new ArrayList<>();
				for (SearchHit hit : searchHits1) {
					Map<String, Object> sourceAsMap = hit.getSourceAsMap();
					// 转为性能实体类
					PmvPerformanceCurrentInfo pmvPerformanceCurrentInfo = new PmvPerformanceCurrentInfo();
					pmvPerformanceCurrentInfo.setVal(sourceAsMap.get("val").toString());
					pmvPerformanceCurrentInfo.setOcc_time(sourceAsMap.get("occ_time").toString());
					pmvPerformanceCurrentInfo.setKpi_name(sourceAsMap.get("kpi_name").toString());
					pmvPerformanceCurrentInfo.setCi_id(sourceAsMap.get("ci_id").toString());

					pmvPerformanceCurrentInfos.add(pmvPerformanceCurrentInfo);
				}
			}
			// 关闭客户端
			// client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pmvPerformanceCurrentInfos;
	}

	public static List<Map<String, Object>> queryPmvDataByEsByName(String kpiNames, String startTime,String endTime) throws IOException {
		List<String> tables=new ArrayList<String>();
               Map<String, Object> criterias = new HashMap<String, Object>();
               criterias.put(">=occ_time", startTime);
               criterias.put("<=occ_time", endTime);
               criterias.put("kpi_name", kpiNames);
               try {
       			//根据startTime和endTime截取日期字段进行ES跨表查询
       			long nd = 1000 * 24 * 60 * 60;			
       			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
       			Date startTimeDate=sdf1.parse(startTime);
       			Date endTimeDate=sdf1.parse(endTime);
       			String startTimeStr=sdf2.format(startTimeDate);
       			String endTimeStr=sdf2.format(endTimeDate);
       			if(startTimeStr.equals(endTimeStr)) {
       				String table=iom_pmv_performance+"-"+startTimeStr;
       				tables.add(table);
       			}else {
       				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
       				// 计算差多少天
       			    long day = diff / nd;
       			    int dayInt=(int)day;
       			    for(int i=1;i<=dayInt;i++) {
       			    	Calendar calendar = Calendar.getInstance();  
       		            calendar.setTime(startTimeDate);  
       		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
       		            Date date = calendar.getTime();
       		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
       		            String time=sdf3.format(date);
       		            if(time.equals(endTimeStr)) {
       		            	if(i==1) {
       		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
       			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
       			            	break;
       		            	}else {
       		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
       		            		break;
       		            	}			            	
       		            }else {
       		            	if(i==1) {
       		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
       			            	tables.add(iom_pmv_performance+"-"+time);
       		            	}else {
       			            	tables.add(iom_pmv_performance+"-"+time);
       		            	}
       		            	
       		            }
       			    }
       			}
       		} catch (Exception e) {
                  e.printStackTrace();
       		}
               RestHighLevelClient client = EsTcpClient.getClient();
               List<String> tableList=new ArrayList<String>();
   			if(tables!=null && tables.size()>0) {
   				for(String tableDate:tables) {
   					boolean flag=EsUtil.existIndex(tableDate);
   					if(flag) {
   						tableList.add(tableDate);
   					}
   				}
   			}
               String [] datesArray = tableList.toArray(new String[tableList.size()]);
               SearchRequest searchRequest = new SearchRequest(datesArray);
               // 指定查询的库表
               SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
               TermsAggregationBuilder kpiIdAgg = AggregationBuilders.terms("kpi_id").field("kpi_id").size(2147483647);
               kpiIdAgg.subAggregation(AggregationBuilders.avg("avg_val").field("val"));
               kpiIdAgg.subAggregation(AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC));
               kpiIdAgg.subAggregation(AggregationBuilders.topHits("max_val").size(1).sort("val", SortOrder.DESC));
               kpiIdAgg.subAggregation(AggregationBuilders.topHits("min_val").size(1).sort("val", SortOrder.ASC));
               searchSourceBuilder.query(EsUtil.builder(criterias)).size(0).aggregation(kpiIdAgg);
               searchRequest.source(searchSourceBuilder);
               SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
               Aggregations aggregations = response.getAggregations();
               List maps = aggregations.asList();
               ParsedStringTerms parsedStringTerms = (ParsedStringTerms) maps.get(0);
               List buckets = parsedStringTerms.getBuckets();
               if (buckets == null || buckets.size() == 0) {
                   return null;
               }
               ParsedStringTerms.ParsedBucket parsedBucket = (ParsedStringTerms.ParsedBucket) buckets.get(0);
               Aggregations aggregations1 = parsedBucket.getAggregations();
               List<Object> aggregations2 = (List) aggregations1.asList();
               List<Map<String, Object>> mapList = new ArrayList<>();
               for (Object o : aggregations2) {
                    Map<String, Object> map = new HashMap<>();
                    if ((o instanceof ParsedAvg)) {
                        ParsedAvg parsedAvg = (ParsedAvg) o;
                        map.put("name", parsedAvg.getName());
                        map.put("val", parsedAvg.getValue());
                        map.put("occ_time", null);
                    } else {
                        ParsedTopHits parsedTopHits = (ParsedTopHits) o;
                        SearchHits hits = parsedTopHits.getHits();
                        SearchHit[] hits1 = hits.getHits();
                        SearchHit documentFields = hits1[0];
                        Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
                        map = sourceAsMap;
                        map.put("name", parsedTopHits.getName());
                    }
                    mapList.add(map);
                }
                return mapList;
       }

	public static List<Map<String, Object>> queryPerformanceValByCiIds(String ciIds, String kpiIds, String startTime,
			String endTime,String ciCodes) throws IOException {
		List<String> tables=new ArrayList<String>();
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=cjsj", startTime);
		criterias.put("<=cjsj", endTime);
		//criterias.put("ci_id", ciIds);
		criterias.put("ci_name", ciCodes);
		criterias.put("kpi_name", kpiIds);
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		// 指定查询的库表
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
				.aggregation(AggregationBuilders.terms("ciName_kpiName")
						.script(new Script("doc['ci_name'] +'#'+doc['kpi_name']")).size(2147483647)
						.subAggregation(AggregationBuilders.max("max_val").field("val")).subAggregation(
								AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC)));
		searchRequest.source(searchSourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		Map<String, Aggregation> aggrMap = response.getAggregations().asMap();
		ParsedStringTerms terms = (ParsedStringTerms) aggrMap.get("ciName_kpiName");
		List<ParsedStringTerms.ParsedBucket> buckets = (List<ParsedStringTerms.ParsedBucket>) terms.getBuckets();
		for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
			Max max = parsedBucket.getAggregations().get("max_val");
			Double maxVal = max.getValue();
			TopHits topHits = parsedBucket.getAggregations().get("current_val");
			SearchHits searchHits = topHits.getHits();
			SearchHit[] searchHits1 = searchHits.getHits();
			if (searchHits1 != null && searchHits1.length > 0) {
				Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
				Map<String, Object> hitNap = formatHumpName(sMap);
				hitNap.put("maxVal", maxVal);
				returnList.add(hitNap);
			}
		}
		return returnList;
	}
	
	public static List<Map<String, Object>> queryPerformancesInfoByCiCodes(String startTime,
			String endTime,String ciCodes,String kpiNames) throws IOException {
		List<String> tables=new ArrayList<String>();
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		//criterias.put("ci_id", ciIds);
		criterias.put("ci_name", ciCodes);
		criterias.put("kpi_name", kpiNames);
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
			if(tables!=null && tables.size()>0) {
				for(String tableDate:tables) {
					boolean flag=EsUtil.existIndex(tableDate);
					if(flag) {
						tableList.add(tableDate);
					}
				}
			}
			String [] datesArray = tableList.toArray(new String[tableList.size()]);
			SearchRequest searchRequest = new SearchRequest(datesArray);
//			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//			if (!"".equals(startTime) && !"".equals(endTime)) {
//				searchSourceBuilder.query(EsUtil.builder(criterias)).size(1).sort("occ_time", SortOrder.DESC);
//			} 
//			searchRequest.source(searchSourceBuilder);
//			SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
//			SearchHit[] searchHits = response.getHits().getHits();
			
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
	        .aggregation(AggregationBuilders.terms("ci_name").field("ci_name").size(2147483647)
	           .subAggregation(AggregationBuilders.max("max_val").field("val"))
	           .subAggregation(AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC)));
			searchRequest.source(searchSourceBuilder);
			SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
			Map<String, Aggregation> aggrMap = response.getAggregations().asMap();
			ParsedStringTerms terms = (ParsedStringTerms) aggrMap.get("ci_name");
			List<ParsedStringTerms.ParsedBucket> buckets = (List<ParsedStringTerms.ParsedBucket>) terms.getBuckets();
			for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
				Aggregations aggs = parsedBucket.getAggregations();
				TopHits topHits = aggs.get("current_val");
				SearchHits searchHits = topHits.getHits();
				SearchHit[] searchHits1 = searchHits.getHits();
				if (searchHits1 != null && searchHits1.length > 0) {
					Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
					Map<String, Object> hitNap = formatHumpName(sMap);
					returnList.add(hitNap);
				}
			}
		    return returnList;
	}

	public static List<Map<String, Object>> getPerformanceDataByAllConditions(String kpiName, String kpiIds,
			String ciIds, String ciName, String ciTypeIds, String ciTypeName, String startTime, String endTime,
			String pageNum, String pageSize) throws IOException {
		List<String> tables=new ArrayList<String>();
		Map<String, Object> criterias = new HashMap<String, Object>();
		if ((kpiName == null || "".equals(kpiName)) && (kpiIds == null || "".equals(kpiIds))
				&& (ciIds == null || "".equals(ciIds)) && (ciName == null || "".equals(ciName))
				&& (ciTypeIds == null || "".equals(ciTypeIds)) && (ciTypeName == null || "".equals(ciTypeName))
				&& (startTime == null || "".equals(startTime)) && (endTime == null || "".equals(endTime))) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			endTime = sdf.format(date);
			Calendar cal = Calendar.getInstance();
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			Date beginOfDate = cal.getTime();
			startTime = sdf.format(beginOfDate);
			criterias.put(">=cjsj", startTime);
			criterias.put("<=cjsj", endTime);
		} else {
			criterias.put(">=cjsj", startTime);
			criterias.put("<=cjsj", endTime);
			criterias.put("ci_id", ciIds);
			criterias.put("kpi_id", kpiIds);
			criterias.put("ci_type_id", ciTypeIds);
			criterias.put("kpi_name", kpiName);
			criterias.put("ci_name", ciName);
			criterias.put("ci_type_name", ciTypeName);
		}
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.trackTotalHits(true);
		searchSourceBuilder.query(EsUtil.builder(criterias)).sort("cjsj", SortOrder.DESC);
		if (pageNum != null && !"".equals(pageNum) && pageSize != null && !"".equals(pageSize)) {
			int size = Integer.valueOf(pageSize);
			int from = (Integer.valueOf(pageNum) - 1) * size;
			searchSourceBuilder.size(size);
			searchSourceBuilder.from(from);
		}
		searchRequest.source(searchSourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHit[] searchHits = response.getHits().getHits();
		for (SearchHit searchHit : searchHits) {
			Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
			returnList.add(sourceAsMap);
		}
		return returnList;
	}

	/**
	 * 执行搜索
	 *
	 * @param selectMethod
	 * @param interval
	 * @param startTime
	 * @param endTime
	 * @param ciTypeId
	 * @param kpiId
	 * @param isGroByCi
	 * @param ciIds
	 * @param order
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static List<List<Map<String, Object>>> getLineChartToOtherProjects(String selectMethod, Long interval,
			String startTime, String endTime, String ciTypeId, String kpiId, String type, String ciIds) {
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> res = null;
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		criterias.put("ci_id", ciIds);
		criterias.put("kpi_id", kpiId);
		criterias.put("ci_type_id", ciTypeId);
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		// 指定查询的库表
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(EsUtil.builder(criterias));
		searchSourceBuilder.size(0);
		List<List<Map<String, Object>>> returnList = new ArrayList<List<Map<String, Object>>>();
		List<String> ciIdsList = Arrays.asList(ciIds.split(","));
		List<String> kpiIdsList = Arrays.asList(kpiId.split(","));
		if (StringUtils.isNotEmpty(selectMethod)) {
			selectMethod = selectMethod.trim().toLowerCase();
			// searchSourceBuilder.sort("cjsj", SortOrder.ASC);
			// 默认平均值
			AbstractAggregationBuilder vals = AggregationBuilders.avg(val).field(val);
			if ("sum".equals(selectMethod)) {
				vals = AggregationBuilders.sum(val).field(val);
			} else if ("max".equals(selectMethod)) {
				vals = AggregationBuilders.max(val).field(val);
			} else if ("min".equals(selectMethod)) {
				vals = AggregationBuilders.min(val).field(val);
			}
			TopHitsAggregationBuilder ciName = AggregationBuilders.topHits(ci_name).size(1);
			DateHistogramAggregationBuilder occTime = AggregationBuilders.dateHistogram(occ_time).field(occ_time)
					.format("yyyy-MM-dd HH:mm:ss").interval(interval).minDocCount(0)
					.extendedBounds(new ExtendedBounds(startTime, endTime));

			if ("ci".equals(type)) {
				TermsAggregationBuilder ci = AggregationBuilders.terms(ci_name).field(ci_name);
				ci.subAggregation(vals);
				ci.subAggregation(ciName);
				occTime.subAggregation(ci);
			} else if ("kpi".equals(type)) {
				TermsAggregationBuilder kpi = AggregationBuilders.terms(kpi_name).field(kpi_name);
				kpi.subAggregation(vals);
				kpi.subAggregation(ciName);
				occTime.subAggregation(kpi);
			}
			searchSourceBuilder.aggregation(occTime);
			searchRequest.source(searchSourceBuilder);
			try {
				SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
				if ("ci".equals(type)) {
					for (String ciIdStr : ciIdsList) {
						List<Map<String, Object>> ciList = new ArrayList<Map<String, Object>>();
						Map<String, Aggregation> aggrMap = searchResponse.getAggregations().asMap();
						ParsedDateHistogram occTimeData = (ParsedDateHistogram) aggrMap.get(occ_time);
						@SuppressWarnings("unchecked")
						List<ParsedDateHistogram.ParsedBucket> buckets = (List<ParsedDateHistogram.ParsedBucket>) occTimeData
								.getBuckets();
						for (ParsedDateHistogram.ParsedBucket parsedBucket : buckets) {
							Map<String, Object> map = new HashMap<String, Object>();
							String timeSlot = parsedBucket.getKeyAsString();
							Map<String, Aggregation> ciIdMap = parsedBucket.getAggregations().asMap();
							ParsedStringTerms ciName1 = (ParsedStringTerms) ciIdMap.get(ci_name);
							List<ParsedStringTerms.ParsedBucket> buckets2 = (List<ParsedStringTerms.ParsedBucket>) ciName1
									.getBuckets();
							if (buckets2 != null && buckets2.size() > 0) {
								for (ParsedStringTerms.ParsedBucket parsedBucket2 : buckets2) {
									Aggregations aggregations = parsedBucket2.getAggregations();
									Aggregation valAgg = aggregations.get(val);
									ParsedTopHits parsedTopHits = aggregations.get(ci_name);
									if (parsedTopHits != null) {
										SearchHits searchHits1 = parsedTopHits.getHits();
										if (searchHits1 != null) {
											SearchHit[] searchHits = searchHits1.getHits();
											if (searchHits != null && searchHits.length > 0) {
												Map<String, Object> sMap = searchHits[0].getSourceAsMap();
												if (sMap != null) {
													String ci_id = (String) sMap.get("ci_id");
													if (ciIdStr.equals(ci_id)) {
														map.put("timeSlot", timeSlot);
														map.put("ciId", ciIdStr);
														if (valAgg.getClass().equals(ParsedSum.class)) {
															map.put(selectMethod + "Val",
																	((ParsedSum) valAgg).getValue());
														} else if (valAgg.getClass().equals(ParsedMax.class)) {
															map.put(selectMethod + "Val",
																	((ParsedMax) valAgg).getValue());
														} else if (valAgg.getClass().equals(ParsedMin.class)) {
															map.put(selectMethod + "Val",
																	((ParsedMin) valAgg).getValue());
														} else if (valAgg.getClass().equals(ParsedAvg.class)) {
															map.put(selectMethod + "Val",
																	((ParsedAvg) valAgg).getValue());
														}
														map.put("ciName", sMap.get(ci_name));
														map.put(ciId, sMap.get(ci_id));
														map.put("kpiName", sMap.get("kpi_name"));
														ciList.add(map);
													}

												}
											}
										}
									}
								}
							} else {
								map.put("timeSlot", timeSlot);
								map.put("ciId", ciIdStr);
								map.put(selectMethod + "Val", 0);
								ciList.add(map);
							}
						}
						returnList.add(ciList);
					}
				} else if ("kpi".equals(type)) {
					for (String kpiIdStr : kpiIdsList) {
						List<Map<String, Object>> kpiList = new ArrayList<Map<String, Object>>();
						Map<String, Aggregation> aggrMap = searchResponse.getAggregations().asMap();
						ParsedDateHistogram occTimeData = (ParsedDateHistogram) aggrMap.get(occ_time);
						@SuppressWarnings("unchecked")
						List<ParsedDateHistogram.ParsedBucket> buckets = (List<ParsedDateHistogram.ParsedBucket>) occTimeData
								.getBuckets();
						for (ParsedDateHistogram.ParsedBucket parsedBucket : buckets) {
							Map<String, Object> map = new HashMap<String, Object>();
							String timeSlot = parsedBucket.getKeyAsString();
							Map<String, Aggregation> ciIdMap = parsedBucket.getAggregations().asMap();
							ParsedStringTerms kpiName = (ParsedStringTerms) ciIdMap.get(kpi_name);
							List<ParsedStringTerms.ParsedBucket> buckets2 = (List<ParsedStringTerms.ParsedBucket>) kpiName
									.getBuckets();
							if (buckets2 != null && buckets2.size() > 0) {
								for (ParsedStringTerms.ParsedBucket parsedBucket2 : buckets2) {
									Aggregations aggregations = parsedBucket2.getAggregations();
									Aggregation valAgg = aggregations.get(val);
									ParsedTopHits parsedTopHits = aggregations.get(ci_name);
									if (parsedTopHits != null) {
										SearchHits searchHits1 = parsedTopHits.getHits();
										if (searchHits1 != null) {
											SearchHit[] searchHits = searchHits1.getHits();
											if (searchHits != null && searchHits.length > 0) {
												Map<String, Object> sMap = searchHits[0].getSourceAsMap();
												if (sMap != null) {
													String kpi_id = (String) sMap.get("kpi_id");
													if (kpiIdStr.equals(kpi_id)) {
														map.put("timeSlot", timeSlot);
														map.put("kpiId", kpiIdStr);
														if (valAgg.getClass().equals(ParsedSum.class)) {
															map.put(selectMethod + "Val",
																	((ParsedSum) valAgg).getValue());
														} else if (valAgg.getClass().equals(ParsedMax.class)) {
															map.put(selectMethod + "Val",
																	((ParsedMax) valAgg).getValue());
														} else if (valAgg.getClass().equals(ParsedMin.class)) {
															map.put(selectMethod + "Val",
																	((ParsedMin) valAgg).getValue());
														} else if (valAgg.getClass().equals(ParsedAvg.class)) {
															map.put(selectMethod + "Val",
																	((ParsedAvg) valAgg).getValue());
														}
														map.put("ciName", sMap.get(ci_name));
														map.put(ciId, sMap.get(ci_id));
														map.put("kpiName", sMap.get("kpi_name"));
														kpiList.add(map);
													}

												}
											}
										}
									}
								}
							} else {
								map.put("timeSlot", timeSlot);
								map.put("kpiId", kpiIdStr);
								map.put(selectMethod + "Val", 0);
								kpiList.add(map);
							}
						}
						returnList.add(kpiList);
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			TopHitsAggregationBuilder ciName = AggregationBuilders.topHits(ci_name).size(1);
			DateHistogramAggregationBuilder occTime = AggregationBuilders.dateHistogram(occ_time).field(occ_time)
					.format("yyyy-MM-dd HH:mm:ss").interval(interval).minDocCount(0)
					.extendedBounds(new ExtendedBounds(startTime, endTime));
			if ("ci".equals(type)) {
				TermsAggregationBuilder ci = AggregationBuilders.terms(ci_name).field(ci_name);
				ci.subAggregation(ciName);
				occTime.subAggregation(ci);
			} else if ("kpi".equals(type)) {
				TermsAggregationBuilder kpi = AggregationBuilders.terms(kpi_name).field(kpi_name);
				kpi.subAggregation(ciName);
				occTime.subAggregation(kpi);
			}
			searchSourceBuilder.aggregation(occTime);
			searchRequest.source(searchSourceBuilder);
			try {
				SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
				if ("ci".equals(type)) {
					for (String ciIdStr : ciIdsList) {
						List<Map<String, Object>> ciList = new ArrayList<Map<String, Object>>();
						Map<String, Aggregation> aggrMap = searchResponse.getAggregations().asMap();
						ParsedDateHistogram occTimeData = (ParsedDateHistogram) aggrMap.get(occ_time);
						@SuppressWarnings("unchecked")
						List<ParsedDateHistogram.ParsedBucket> buckets = (List<ParsedDateHistogram.ParsedBucket>) occTimeData
								.getBuckets();
						for (ParsedDateHistogram.ParsedBucket parsedBucket : buckets) {
							Map<String, Object> map = new HashMap<String, Object>();
							String timeSlot = parsedBucket.getKeyAsString();
							Map<String, Aggregation> ciIdMap = parsedBucket.getAggregations().asMap();
							ParsedStringTerms ciName1 = (ParsedStringTerms) ciIdMap.get(ci_name);
							List<ParsedStringTerms.ParsedBucket> buckets2 = (List<ParsedStringTerms.ParsedBucket>) ciName1
									.getBuckets();
							if (buckets2 != null && buckets2.size() > 0) {
								for (ParsedStringTerms.ParsedBucket parsedBucket2 : buckets2) {
									Aggregations aggregations = parsedBucket2.getAggregations();
									Aggregation valAgg = aggregations.get(val);
									ParsedTopHits parsedTopHits = aggregations.get(ci_name);
									if (parsedTopHits != null) {
										SearchHits searchHits1 = parsedTopHits.getHits();
										if (searchHits1 != null) {
											SearchHit[] searchHits = searchHits1.getHits();
											if (searchHits != null && searchHits.length > 0) {
												Map<String, Object> sMap = searchHits[0].getSourceAsMap();
												if (sMap != null) {
													String ci_id = (String) sMap.get("ci_id");
													if (ciIdStr.equals(ci_id)) {
														map.put("timeSlot", timeSlot);
														map.put("ciId", ciIdStr);
														if (valAgg.getClass().equals(ParsedSum.class)) {
															map.put(selectMethod + "Val",
																	((ParsedSum) valAgg).getValue());
														} else if (valAgg.getClass().equals(ParsedMax.class)) {
															map.put(selectMethod + "Val",
																	((ParsedMax) valAgg).getValue());
														} else if (valAgg.getClass().equals(ParsedMin.class)) {
															map.put(selectMethod + "Val",
																	((ParsedMin) valAgg).getValue());
														} else if (valAgg.getClass().equals(ParsedAvg.class)) {
															map.put(selectMethod + "Val",
																	((ParsedAvg) valAgg).getValue());
														}
														map.put("ciName", sMap.get(ci_name));
														map.put(ciId, sMap.get(ci_id));
														map.put("kpiName", sMap.get("kpi_name"));
														ciList.add(map);
													}

												}
											}
										}
									}
								}
							} else {
								map.put("timeSlot", timeSlot);
								map.put("ciId", ciIdStr);
								map.put(selectMethod + "Val", 0);
								ciList.add(map);
							}
						}
						returnList.add(ciList);
					}
				} else if ("kpi".equals(type)) {
					for (String kpiIdStr : kpiIdsList) {
						List<Map<String, Object>> kpiList = new ArrayList<Map<String, Object>>();
						Map<String, Aggregation> aggrMap = searchResponse.getAggregations().asMap();
						ParsedDateHistogram occTimeData = (ParsedDateHistogram) aggrMap.get(occ_time);
						@SuppressWarnings("unchecked")
						List<ParsedDateHistogram.ParsedBucket> buckets = (List<ParsedDateHistogram.ParsedBucket>) occTimeData
								.getBuckets();
						for (ParsedDateHistogram.ParsedBucket parsedBucket : buckets) {
							Map<String, Object> map = new HashMap<String, Object>();
							String timeSlot = parsedBucket.getKeyAsString();
							Map<String, Aggregation> ciIdMap = parsedBucket.getAggregations().asMap();
							ParsedStringTerms kpiName = (ParsedStringTerms) ciIdMap.get(kpi_name);
							List<ParsedStringTerms.ParsedBucket> buckets2 = (List<ParsedStringTerms.ParsedBucket>) kpiName
									.getBuckets();
							if (buckets2 != null && buckets2.size() > 0) {
								for (ParsedStringTerms.ParsedBucket parsedBucket2 : buckets2) {
									Aggregations aggregations = parsedBucket2.getAggregations();
									Aggregation valAgg = aggregations.get(val);
									ParsedTopHits parsedTopHits = aggregations.get(ci_name);
									if (parsedTopHits != null) {
										SearchHits searchHits1 = parsedTopHits.getHits();
										if (searchHits1 != null) {
											SearchHit[] searchHits = searchHits1.getHits();
											if (searchHits != null && searchHits.length > 0) {
												Map<String, Object> sMap = searchHits[0].getSourceAsMap();
												if (sMap != null) {
													String kpi_id = (String) sMap.get("kpi_id");
													if (kpiIdStr.equals(kpi_id)) {
														map.put("timeSlot", timeSlot);
														map.put("kpiId", kpiIdStr);
														if (valAgg.getClass().equals(ParsedSum.class)) {
															map.put(selectMethod + "Val",
																	((ParsedSum) valAgg).getValue());
														} else if (valAgg.getClass().equals(ParsedMax.class)) {
															map.put(selectMethod + "Val",
																	((ParsedMax) valAgg).getValue());
														} else if (valAgg.getClass().equals(ParsedMin.class)) {
															map.put(selectMethod + "Val",
																	((ParsedMin) valAgg).getValue());
														} else if (valAgg.getClass().equals(ParsedAvg.class)) {
															map.put(selectMethod + "Val",
																	((ParsedAvg) valAgg).getValue());
														}
														map.put("ciName", sMap.get(ci_name));
														map.put(ciId, sMap.get(ci_id));
														map.put("kpiName", sMap.get("kpi_name"));
														kpiList.add(map);
													}

												}
											}
										}
									}
								}
							} else {
								map.put("timeSlot", timeSlot);
								map.put("kpiId", kpiIdStr);
								map.put(selectMethod + "Val", 0);
								kpiList.add(map);
							}
						}
						returnList.add(kpiList);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return returnList;
	}
	/**
	 * 性能阈值设置变化策略使用
	 *
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String queryPerformanceBykpiIdAndciId(String kpiId,String ciId,String startTime,String endTime,String valueMethod) {
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		criterias.put("ci_id", ciId);
		criterias.put("kpi_id", kpiId);
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		// 指定查询的库表
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		if("MAX".equals(valueMethod)) {
			searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
            .aggregation(AggregationBuilders.terms("ci_id").field("ci_id").size(2147483647)
	           .subAggregation(AggregationBuilders.max("max_val").field("val"))
	           .subAggregation(AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC)));
		}else if("MIN".equals(valueMethod)) {
			searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
            .aggregation(AggregationBuilders.terms("ci_id").field("ci_id").size(2147483647)
	           .subAggregation(AggregationBuilders.min("min_val").field("val"))
	           .subAggregation(AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC)));
		}else if("AVG".equals(valueMethod)) {
			searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
            .aggregation(AggregationBuilders.terms("ci_id").field("ci_id").size(2147483647)
	           .subAggregation(AggregationBuilders.avg("avg_val").field("val"))
	           .subAggregation(AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC)));
		}else if("SUM".equals(valueMethod)) {
			searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
            .aggregation(AggregationBuilders.terms("ci_id").field("ci_id").size(2147483647)
	           .subAggregation(AggregationBuilders.sum("sum_val").field("val"))
	           .subAggregation(AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC)));
		}
		
        searchRequest.source(searchSourceBuilder);
		try {
			SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
			Map<String, Aggregation> aggrMap = response.getAggregations().asMap();
			ParsedStringTerms terms = (ParsedStringTerms) aggrMap.get("ci_id");
			List<ParsedStringTerms.ParsedBucket> buckets = (List<ParsedStringTerms.ParsedBucket>) terms.getBuckets();
			for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
				
				if("MAX".equals(valueMethod)) {
					Max max = parsedBucket.getAggregations().get("max_val");
					Double maxVal = max.getValue();
					return String.valueOf(maxVal);
				}else if("MIN".equals(valueMethod)) {
					Min min = parsedBucket.getAggregations().get("min_val");
					Double minVal = min.getValue();
					return String.valueOf(minVal);
				}else if("AVG".equals(valueMethod)) {
					Avg avg = parsedBucket.getAggregations().get("avg_val");
					Double avgVal = avg.getValue();
					return String.valueOf(avgVal);
				}else if("SUM".equals(valueMethod)) {
					Sum sum = parsedBucket.getAggregations().get("sum_val");
					Double sumVal = sum.getValue();
					return String.valueOf(sumVal);
				}				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 性能阈值设置变化策略使用
	 *
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String queryPerformanceBykpiIdAndciIdNew(String kpiId,String ciId,String startTime,String endTime,String valueMethod,String ciCode,String kpiName) {
		List<String> tables=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		//criterias.put("ci_id", ciId);
		//criterias.put("kpi_id", kpiId);
		criterias.put("ci_name", ciCode);
		criterias.put("kpi_name", kpiName);
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		// 指定查询的库表
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		if("MAX".equals(valueMethod)) {
			searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
            .aggregation(AggregationBuilders.terms("ci_name").field("ci_name").size(2147483647)
	           .subAggregation(AggregationBuilders.max("max_val").field("val"))
	           .subAggregation(AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC)));
		}else if("MIN".equals(valueMethod)) {
			searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
            .aggregation(AggregationBuilders.terms("ci_name").field("ci_name").size(2147483647)
	           .subAggregation(AggregationBuilders.min("min_val").field("val"))
	           .subAggregation(AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC)));
		}else if("AVG".equals(valueMethod)) {
			searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
            .aggregation(AggregationBuilders.terms("ci_name").field("ci_name").size(2147483647)
	           .subAggregation(AggregationBuilders.avg("avg_val").field("val"))
	           .subAggregation(AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC)));
		}else if("SUM".equals(valueMethod)) {
			searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
            .aggregation(AggregationBuilders.terms("ci_name").field("ci_name").size(2147483647)
	           .subAggregation(AggregationBuilders.sum("sum_val").field("val"))
	           .subAggregation(AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC)));
		}
		
        searchRequest.source(searchSourceBuilder);
		try {
			SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
			Map<String, Aggregation> aggrMap = response.getAggregations().asMap();
			ParsedStringTerms terms = (ParsedStringTerms) aggrMap.get("ci_name");
			List<ParsedStringTerms.ParsedBucket> buckets = (List<ParsedStringTerms.ParsedBucket>) terms.getBuckets();
			for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
				
				if("MAX".equals(valueMethod)) {
					Max max = parsedBucket.getAggregations().get("max_val");
					Double maxVal = max.getValue();
					return String.valueOf(maxVal);
				}else if("MIN".equals(valueMethod)) {
					Min min = parsedBucket.getAggregations().get("min_val");
					Double minVal = min.getValue();
					return String.valueOf(minVal);
				}else if("AVG".equals(valueMethod)) {
					Avg avg = parsedBucket.getAggregations().get("avg_val");
					Double avgVal = avg.getValue();
					return String.valueOf(avgVal);
				}else if("SUM".equals(valueMethod)) {
					Sum sum = parsedBucket.getAggregations().get("sum_val");
					Double sumVal = sum.getValue();
					return String.valueOf(sumVal);
				}				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 场景中的指标板组件（添加时间组件时有时间范围，不添加时间组件时没有时间范围）
	 *
	 * @throws ParseException
	 * @throws IOException
	 */
	public static List<Map<String,Object>> getIndicatorBoardByKpiIds(String ciId,String kpiIds,String startTime,String endTime,String domainId,String ciCode,String kpiNames) {
		List<String> tables=new ArrayList<String>();
		List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		//criterias.put("ci_id", ciId);
		criterias.put("ci_name", ciCode);
		//criterias.put("kpi_id", kpiIds);
		criterias.put("kpi_name", kpiNames);
		if(domainId!=null) {
			criterias.put("domain_id", domainId);
		}
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		// 指定查询的库表
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
        .aggregation(AggregationBuilders.terms("kpi_name").field("kpi_name").size(2147483647)
           .subAggregation(AggregationBuilders.max("max_val").field("val"))
           .subAggregation(AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC)));
		searchRequest.source(searchSourceBuilder);
		try {
			SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
			Map<String, Aggregation> aggrMap = response.getAggregations().asMap();
			ParsedStringTerms terms = (ParsedStringTerms) aggrMap.get("kpi_name");
			List<ParsedStringTerms.ParsedBucket> buckets = (List<ParsedStringTerms.ParsedBucket>) terms.getBuckets();
			for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
				Aggregations aggs = parsedBucket.getAggregations();
				TopHits topHits = aggs.get("current_val");
				SearchHits searchHits = topHits.getHits();
				SearchHit[] searchHits1 = searchHits.getHits();
				if (searchHits1 != null && searchHits1.length > 0) {
					Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
					Map<String, Object> hitNap = formatHumpName(sMap);
					returnList.add(hitNap);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public static Map<String,Object> selectPerformanceAllDataByPaging(String index){
		List<Map<String, Object>> res = null;
		RestHighLevelClient client = EsTcpClient.getClient();
		SearchRequest searchRequest = new SearchRequest(index);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.size(1);
		searchSourceBuilder.from(10);
		searchSourceBuilder.query(QueryBuilders.matchAllQuery()).sort("occ_time", SortOrder.ASC);
		searchRequest.source(searchSourceBuilder);
		try {
			SearchResponse searchResponse=client.search(searchRequest, RequestOptions.DEFAULT);
			SearchHits searchHits = searchResponse.getHits();
			SearchHit[] searchHits1 = searchHits.getHits();
			if (searchHits1 != null && searchHits1.length > 0) {
				Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
				Map<String, Object> hitNap = formatHumpName(sMap);
				return hitNap;
			};
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *查询表盘图数据(临时)
	 *
	 * @throws ParseException
	 * @throws IOException
	 */
	public static Map<String,Object> selectDialDiagram(String selectMethod,String ciId,String kpiId,String startTime,String endTime,String domainId,String ciTypeId,String kpiName,String ciCodes) {
		List<String> tables=new ArrayList<String>();
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(">=occ_time", startTime);
		criterias.put("<=occ_time", endTime);
		//criterias.put("ci_id", ciId);
		//criterias.put("kpi_id", kpiId);
		criterias.put("kpi_name", kpiName);
		//criterias.put("ci_type_id", ciTypeId);
		criterias.put("ci_name", ciCodes);
		if(domainId!=null) {
			criterias.put("domain_id", domainId);
		}
		try {
			//根据startTime和endTime截取日期字段进行ES跨表查询
			long nd = 1000 * 24 * 60 * 60;			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date startTimeDate=sdf1.parse(startTime);
			Date endTimeDate=sdf1.parse(endTime);
			String startTimeStr=sdf2.format(startTimeDate);
			String endTimeStr=sdf2.format(endTimeDate);
			if(startTimeStr.equals(endTimeStr)) {
				String table=iom_pmv_performance+"-"+startTimeStr;
				tables.add(table);
			}else {
				long diff = sdf2.parse(endTime).getTime() - sdf2.parse(startTime).getTime();
				// 计算差多少天
			    long day = diff / nd;
			    int dayInt=(int)day;
			    for(int i=1;i<=dayInt;i++) {
			    	Calendar calendar = Calendar.getInstance();  
		            calendar.setTime(startTimeDate);  
		            calendar.add(Calendar.DAY_OF_MONTH, +i);  
		            Date date = calendar.getTime();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		            String time=sdf3.format(date);
		            if(time.equals(endTimeStr)) {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+endTimeStr);
			            	break;
		            	}else {
		            		tables.add(iom_pmv_performance+"-"+endTimeStr);
		            		break;
		            	}			            	
		            }else {
		            	if(i==1) {
		            		tables.add(iom_pmv_performance+"-"+startTimeStr);
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}else {
			            	tables.add(iom_pmv_performance+"-"+time);
		            	}
		            	
		            }
			    }
			}
		} catch (Exception e) {
           e.printStackTrace();
		}
		RestHighLevelClient client = EsTcpClient.getClient();
		List<String> tableList=new ArrayList<String>();
		if(tables!=null && tables.size()>0) {
			for(String tableDate:tables) {
				boolean flag=EsUtil.existIndex(tableDate);
				if(flag) {
					tableList.add(tableDate);
				}
			}
		}
		String [] datesArray = tableList.toArray(new String[tableList.size()]);
		SearchRequest searchRequest = new SearchRequest(datesArray);
		// 指定查询的库表
				SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
				if("max".equals(selectMethod)) {
					searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
		            .aggregation(AggregationBuilders.terms("ci_name").field("ci_name").order(BucketOrder.aggregation("max_val", false)).size(2147483647)
			           .subAggregation(AggregationBuilders.max("max_val").field("val"))
			           .subAggregation(AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC)));
				}else if("min".equals(selectMethod)) {
					searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
		            .aggregation(AggregationBuilders.terms("ci_name").field("ci_name").order(BucketOrder.aggregation("min_val", false)).size(2147483647)
			           .subAggregation(AggregationBuilders.min("min_val").field("val"))
			           .subAggregation(AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC)));
				}else if("avg".equals(selectMethod)) {
					searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
		            .aggregation(AggregationBuilders.terms("ci_name").field("ci_name").order(BucketOrder.aggregation("avg_val", false)).size(2147483647)
			           .subAggregation(AggregationBuilders.avg("avg_val").field("val"))
			           .subAggregation(AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC)));
				}else if("sum".equals(selectMethod)) {
					searchSourceBuilder.query(EsUtil.builder(criterias)).size(0)
		            .aggregation(AggregationBuilders.terms("ci_name").field("ci_name").order(BucketOrder.aggregation("sum_val", false)).size(2147483647)
			           .subAggregation(AggregationBuilders.sum("sum_val").field("val"))
			           .subAggregation(AggregationBuilders.topHits("current_val").size(1).sort("occ_time", SortOrder.DESC)));
				}
				
		        searchRequest.source(searchSourceBuilder);
		        try {
					SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
					Map<String, Aggregation> aggrMap = response.getAggregations().asMap();
					ParsedStringTerms terms = (ParsedStringTerms) aggrMap.get("ci_name");
					List<ParsedStringTerms.ParsedBucket> buckets = (List<ParsedStringTerms.ParsedBucket>) terms.getBuckets();
					for (ParsedStringTerms.ParsedBucket parsedBucket : buckets) {
						
						if("max".equals(selectMethod)) {
							Max max = parsedBucket.getAggregations().get("max_val");
							Double maxVal = max.getValue();
							//return String.valueOf(maxVal);
							TopHits topHits = parsedBucket.getAggregations().get("current_val");
							SearchHits searchHits = topHits.getHits();
							SearchHit[] searchHits1 = searchHits.getHits();
							if (searchHits1 != null && searchHits1.length > 0) {
								Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
								Map<String, Object> hitNap = formatHumpName(sMap);
								hitNap.put("dataVal", String.valueOf(maxVal));
								return hitNap;
							}
						}else if("min".equals(selectMethod)) {
							Min min = parsedBucket.getAggregations().get("min_val");
							Double minVal = min.getValue();
							//return String.valueOf(minVal);
							TopHits topHits = parsedBucket.getAggregations().get("current_val");
							SearchHits searchHits = topHits.getHits();
							SearchHit[] searchHits1 = searchHits.getHits();
							if (searchHits1 != null && searchHits1.length > 0) {
								Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
								Map<String, Object> hitNap = formatHumpName(sMap);
								hitNap.put("dataVal", String.valueOf(minVal));
								return hitNap;
							}
						}else if("avg".equals(selectMethod)) {
							Avg avg = parsedBucket.getAggregations().get("avg_val");
							Double avgVal = avg.getValue();
							//return String.valueOf(avgVal);
							TopHits topHits = parsedBucket.getAggregations().get("current_val");
							SearchHits searchHits = topHits.getHits();
							SearchHit[] searchHits1 = searchHits.getHits();
							if (searchHits1 != null && searchHits1.length > 0) {
								Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
								Map<String, Object> hitNap = formatHumpName(sMap);
								BigDecimal   b   =   new   BigDecimal(avgVal); 
								double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
								hitNap.put("dataVal", String.valueOf(f1));
								return hitNap;
							}
						}else if("sum".equals(selectMethod)) {
							Sum sum = parsedBucket.getAggregations().get("sum_val");
							Double sumVal = sum.getValue();
							//return String.valueOf(sumVal);
							TopHits topHits = parsedBucket.getAggregations().get("current_val");
							SearchHits searchHits = topHits.getHits();
							SearchHit[] searchHits1 = searchHits.getHits();
							if (searchHits1 != null && searchHits1.length > 0) {
								Map<String, Object> sMap = searchHits1[0].getSourceAsMap();
								Map<String, Object> hitNap = formatHumpName(sMap);
								hitNap.put("dataVal", String.valueOf(sumVal));
								return hitNap;
							}
						}				
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
	}
	
}
