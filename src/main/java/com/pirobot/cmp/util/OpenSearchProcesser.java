package com.pirobot.cmp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.opensearch.CloudsearchClient;
import com.aliyun.opensearch.CloudsearchDoc;
import com.aliyun.opensearch.CloudsearchIndex;
import com.aliyun.opensearch.CloudsearchSearch;
import com.aliyun.opensearch.object.KeyTypeEnum;

public class OpenSearchProcesser{
	static Log logger = LogFactory.getLog(OpenSearchProcesser.class);
	String accesskey= "mX5GgMHT7fJ3EGCl";
	String secret = "AD5AgZdRPH411hf2SjLVo3lPjoBrIS";
	String host = "http://opensearch-cn-hangzhou.aliyuncs.com";
	CloudsearchClient client;
	CloudsearchIndex app;
	CloudsearchDoc doc;
	CloudsearchSearch search;
	private static OpenSearchProcesser openSearchProcesser = null;
	
	public enum OpType
	{
		ADD("add"),DELETE("delete"),UPDATE("update");
		private String typeName;
		private OpType(String typeName)
		{
			this.typeName = typeName;
		}
		@Override
        public String toString() {
			return typeName;
		}
	}
	private OpenSearchProcesser() {
		try{
			Map<String, Object> opts = new HashMap<String, Object>();
			opts.put("debug", true);
			client = new CloudsearchClient(accesskey, secret, host, opts, KeyTypeEnum.ALIYUN);
		}catch(Exception e){}
	}
	public static OpenSearchProcesser instance()
	{
		if(openSearchProcesser == null)
		{
			openSearchProcesser = new OpenSearchProcesser();
		}
		return openSearchProcesser;
	}
	
	public String manipulate(String indexName, String tableName, OpType opType, JSONArray objectArr)
	{
		String retStr = "";
		try{
			doc = new CloudsearchDoc(indexName, client);
			JSONArray docArr = new JSONArray();
			for(Object object : objectArr)
			{
				Map<String, Object> fields = new HashMap<String, Object>();
				JSONObject docObj = (JSONObject)object;
				
				Set entrySet = docObj.entrySet();
				for(Object obj : entrySet)
				{
					Entry<String, Object> entry = (Entry<String, Object>)(obj);
					fields.put(entry.getKey(), entry.getValue());
				}
				logger.info(fields);
				switch(opType)
				{
				case ADD:
					doc.add(fields);
					break;
				case UPDATE:
					doc.update(fields);
					break;
				case DELETE:
					doc.remove(fields);
					break;
				}
				
			}
			retStr = doc.push(tableName);
			logger.info(retStr);
		}catch(Exception e){
			logger.error(doc.getDebugInfo(), e);
		}
		return retStr;
	}
	
	public String search(String indexName, String tableName, String queryStr, List<String> fetchFields, String filterStr, int start, int hitsNum)
	{
		search = new CloudsearchSearch(client);
		JSONObject retInfos = new JSONObject();
		try {
			search.addIndex(indexName);
			search.setQueryString(queryStr);
			search.setFormat("json");	
			if(fetchFields != null && fetchFields.size() > 0)
				search.addFetchFields(fetchFields);
			if(!StringUtils.isEmpty(filterStr))
				search.addFilter(filterStr);
			if(start > -1)
				search.setStartHit(start);
			if(hitsNum > 0)
				search.setHits(hitsNum);			
			String searchResult = search.search();
			JSONObject searchResultObject = JSONObject.parseObject(searchResult);
			JSONObject resultObj = searchResultObject.getJSONObject("result");
			logger.info("本次搜索字符串：" + queryStr + "，过滤字符串：" + filterStr + "，搜索结果耗时 = " + resultObj.getFloatValue("searchtime") + "秒,返回结果数量 = " + resultObj.getIntValue("num") 
			+ "条,总数量 = " + resultObj.getIntValue("total") + "条,可显示数量 = " + resultObj.getIntValue("viewtotal"));
			JSONArray retDocArr = new JSONArray();
			if(searchResultObject.getString("status").equals("OK"))
			{
				JSONArray retInfoArray = resultObj.getJSONArray("items");
				for (Object object : retInfoArray) {					
					JSONObject retInfoObject = (JSONObject)object;
					retInfoObject.remove("index_name");				
					retDocArr.add(retInfoObject);
				}
			}
			retInfos.put("totalCount", resultObj.getIntValue("viewtotal"));
			retInfos.put("result", retDocArr.toJSONString());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return retInfos.toJSONString();
	}
	public String getDebugInfo(String indexName, String requestId)
	{
		app = new CloudsearchIndex(indexName, client);
		return app.getDebugInfo();
	}
	public static void main(String args[])
	{
		JSONArray docs = new JSONArray();
		JSONObject doc = new JSONObject();
		doc.put("id", System.currentTimeMillis() + "");
		doc.put("who", "system");
		doc.put("question", "请问你叫什么名字");
		doc.put("answer", "小派");
		doc.put("discretedquestion90", "请问你叫什么名字  我是一个小白 地方");
		doc.put("discretedquestion80", "地方派 胜负");
		doc.put("listtag", "1");
		docs.add(doc);
		OpenSearchProcesser.instance().manipulate("pirobot", "pirobot", OpType.ADD, docs);
		
//		JSONArray docs = new JSONArray();
//		JSONObject doc = new JSONObject();
//		doc.put("id", "1481875928476");
//		docs.add(doc);
//		OpenSearchProcesser.instance().manipulate("pirobot", "pirobot", OpType.DELETE, docs);
//
//		JSONArray docs = new JSONArray();
//		JSONObject doc = new JSONObject();
//		doc.put("id", "1481875928476");
//		doc.put("question", "请问你叫什么");
//		doc.put("answer", "小白");
//		docs.add(doc);
//		OpenSearchProcesser.instance().manipulate("pirobot", "pirobot", OpType.UPDATE, docs);
		
//		ArrayList<String> fetchFields = new ArrayList<String>();//{"id", "question", "answer"};
//		fetchFields.add("id");
//		fetchFields.add("question");
//		fetchFields.add("answer");
//		System.out.println(OpenSearchProcesser.instance().search("pirobot", "pirobot", "listtag:'1'", fetchFields, "", 0, 20));
		
		
	}
}
