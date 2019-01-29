package com.pirobot.cmp.util;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pirobot.cmp.util.OpenSearchProcesser.OpType;
import com.qcloud.Utilities.MD5;

public class QAProcesser {
	protected static final Log logger = LogFactory.getLog(QAProcesser.class);
	private static final long serialVersionUID = 1L;
	private String qaSrvUrl = "";
	private String userName = "";
	private String password = "";
	private String token = "";
	private static QAProcesser qaProcesser = null;
	private QAProcesser(){
		qaSrvUrl = ConfigManager.getInstance().getStringValue("ICS_ADDR");
		userName = ConfigManager.getInstance().getStringValue("ICS_USER");
		password = ConfigManager.getInstance().getStringValue("ICS_PWD");
	}
	public static QAProcesser getInstance()
	{
		if(qaProcesser == null)
			qaProcesser = new QAProcesser();
		return qaProcesser;
	}
	
	public void login()
	{
		try {
			String requesturl = qaSrvUrl + "login/?" + "name=" + userName + "&password=" + MD5.stringToMD5(password);
			CloseableHttpClient httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).build();             
            HttpGet httpGet = new HttpGet();  
            httpGet.setURI(new URI(requesturl)); 
            httpGet.setConfig(requestConfig);
            
            HttpResponse response = httpClient.execute(httpGet);  
            if(response.getStatusLine().getStatusCode()==200){
            	JSONObject result = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), HTTP.UTF_8));
            	logger.info("登录结果：" + result);
            	int retCode = result.getInteger("retCode");
            	if(retCode == 0)
            		token = result.getString("token");            	
            }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void addQA(String question, String answer) {
		try {
			String requesturl = qaSrvUrl + "add/?" + "name=" + userName + "&token=" + token + "&q=" + URLEncoder.encode(question) + "&a=" + URLEncoder.encode(answer);
			CloseableHttpClient httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).build();             
            HttpGet httpGet = new HttpGet();  
            httpGet.setURI(new URI(requesturl)); 
            httpGet.setConfig(requestConfig);
            
            HttpResponse response = httpClient.execute(httpGet);  
            if(response.getStatusLine().getStatusCode()==200){
            	JSONObject result = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), HTTP.UTF_8));
            	logger.info("问答添加结果：" + result);
            	int retCode = result.getInteger("retCode");
            	if(retCode == 1)
            		logger.error("问答添加失败");           	
            }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}	
	}
    
 	public void delQA(String id) {
 		try {
			String requesturl = qaSrvUrl + "del/?" + "name=" + userName + "&token=" + token + "&id=" + id;
			CloseableHttpClient httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).build();             
            HttpGet httpGet = new HttpGet();  
            httpGet.setURI(new URI(requesturl)); 
            httpGet.setConfig(requestConfig);
            
            HttpResponse response = httpClient.execute(httpGet);  
            if(response.getStatusLine().getStatusCode()==200){
            	JSONObject result = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), HTTP.UTF_8));
            	logger.info("问答删除结果：" + result); 	
            }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}	
	}
 	
	public String searchQA(String question) {
		JSONObject result = new JSONObject();
		try {
			String requesturl = qaSrvUrl + "search/?" + "name=" + userName + "&token=" + token + "&q=" + URLEncoder.encode(question);
			CloseableHttpClient httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).build();             
            HttpGet httpGet = new HttpGet();  
            httpGet.setURI(new URI(requesturl)); 
            httpGet.setConfig(requestConfig);
            
            HttpResponse response = httpClient.execute(httpGet);  
            if(response.getStatusLine().getStatusCode()==200){
            	result = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), HTTP.UTF_8));
            	logger.info("问题查询结果：" + result); 	
            	return result.toJSONString();
            }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}	
		return result.toJSONString();
	}
	public String listQA(int pageId, int pageSize)
	{
		JSONObject result = new JSONObject();
		try {
			String requesturl = qaSrvUrl + "list/?" + "name=" + userName + "&token=" + token + "&pageId=" + pageId + "&pageSize=" + pageSize;
			CloseableHttpClient httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).build();             
            HttpGet httpGet = new HttpGet();  
            httpGet.setURI(new URI(requesturl)); 
            httpGet.setConfig(requestConfig);
            
            HttpResponse response = httpClient.execute(httpGet);  
            if(response.getStatusLine().getStatusCode()==200){
            	result = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), HTTP.UTF_8));
            	logger.info("问答列表查询结果：" + result); 	
            	return result.toJSONString();
            }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}	
		return result.toJSONString();
	}
	public String compareSentences(String comparedSentence, List<String> sentenceList)
	{
		JSONObject result = new JSONObject();
		try {
			String requesturl = qaSrvUrl + "sentenceCmp/?" + "name=" + userName + "&token=" + token 
							  + "&s1=" + comparedSentence + "&s2=" + URLEncoder.encode(StringUtils.join(sentenceList.toArray(), "##"));
			CloseableHttpClient httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).build();             
            HttpGet httpGet = new HttpGet();  
            httpGet.setURI(new URI(requesturl)); 
            httpGet.setConfig(requestConfig);
            
            HttpResponse response = httpClient.execute(httpGet);  
            if(response.getStatusLine().getStatusCode() == 200){
            	result = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), HTTP.UTF_8));
            	logger.info("语句比较结果：" + result); 	
            	return result.toJSONString();
            }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}	
		return result.toJSONString();
	}
}
