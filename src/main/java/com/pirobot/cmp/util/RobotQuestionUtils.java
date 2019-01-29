/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.cmp.util;

import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pirobot.cmp.api.result.AnwerResult;
import com.pirobot.cmp.api.result.XunfeiResult;
import com.pirobot.cmp.model.User;
 
public class RobotQuestionUtils {
	protected final static Logger logger = Logger.getLogger(RobotQuestionUtils.class);

	public static  AnwerResult question(final String deviceId,final String sender,final String content,final String action) throws Exception{
		 final ExecutorService  executor   = Executors.newFixedThreadPool(4);
		 AnwerResult anwerResult = new AnwerResult();
		 TreeMap<String, String>  anwerMap = new TreeMap<>();
		 if((action.equals("a") || action.equals("0"))){
			 executor.execute(new Runnable() {
					public void run() {
						String result = getResultFromICS(sender, content);
						
						if(StringUtils.isNotBlank(result))
						anwerMap.put("0", result);
					}
			});
		 }
		
		 if((action.equals("a") || action.equals("1"))){
			 executor.execute(new Runnable() {
					public void run() {
						String result = getResultFromLocalAction(content);
						if(StringUtils.isNotBlank(result))
						anwerMap.put("1", result);
					}
			});
		 }
		 
		 if((action.equals("a") || action.equals("2"))){
			 executor.execute(new Runnable() {
					public void run() {
						String result = getResultFromXunfei(content);
						anwerMap.put("2", result);
					}
			});
		 }
			 
		 
		 if((action.equals("a") || action.equals("3"))){
			 final User user = new User();
			 user.setUid(Long.parseLong(sender));
			 executor.execute(new Runnable() {
					public void run() {
						String result = getResultFromTuling(user,content);
						anwerMap.put("3", result);
					}
			});
		 }
		 executor.shutdown();
		 
		 while(!anwerMap.containsKey("0") && !anwerMap.containsKey("1")){
			 if(executor.isTerminated()){
				 if(anwerMap.isEmpty()){
					 anwerResult.data = ConfigManager.getInstance().getStringValue("DEF_RESPONSE_TXT");
					 anwerResult.source=("-1");
					 return anwerResult;
				 }
				 
				 break;
			 }
		 }
		
		 for(String key:anwerMap.keySet()){
			 
			    if(StringUtils.isNotBlank(anwerResult.data =anwerMap.get(key))){
			    	anwerResult.data =anwerMap.get(key);
					anwerResult.source=key;
					break ;
			    }
			
		 }
		
		 
		 return anwerResult;
	}
	
	
	private static String getResultFromICS(String sender,String content)
	{
		String retInfo = "";
		String matchedStr =  matchScriptSearch(content);
	    long t = System.currentTimeMillis();
		JSONObject retObj = JSONObject.parseObject(QAProcesser.getInstance().searchQA(matchedStr));
		logger.warn("调用智能客服接口完毕,耗时"+(System.currentTimeMillis()-t)+"毫秒,data:" + retObj);
		
		JSONArray retArr = retObj.getJSONArray("result");
		double maxSim = 0.0f;
		for(Object obj : retArr)
		{
			JSONObject answerObj = (JSONObject)obj;
			if(answerObj.getDouble("similiarity") > maxSim)
			{
				maxSim = answerObj.getDouble("similiarity");
				retInfo = answerObj.getString("answer");
			}
		}
		//如果是剧本ID检查执行剧本的固定语句格式
        if(retInfo!=null &&retInfo.startsWith("SID") && content.equals(matchedStr)){
        	retInfo = null;
        }
		 
		return retInfo;
	}
	
	private static String getResultFromLocalAction(String content)
	{
		long t = System.currentTimeMillis();
		String matchedInfo = CustomActionsManager.getInstance().getMatchedRule(content);
		logger.warn("调用本地定制接口完毕,耗时"+(System.currentTimeMillis()-t)+"毫秒,data:" +  matchedInfo);

		return matchedInfo;
	}
	
	private  static String getResultFromXunfei(String content)
	{
		long t = System.currentTimeMillis();
		String result = new XunfeiService().understandText(content);
		logger.warn("调用讯飞接口完毕,耗时"+(System.currentTimeMillis()-t)+"毫秒,data:" + result);
		XunfeiResult xfResult =  JSON.parseObject(result,XunfeiResult.class);
		if(xfResult!=null&&xfResult.isSuccess()){
			return result;
		}
		return null;
	}
	
	private static String getResultFromTuling(User user,String content)
	{
		
		long t = System.currentTimeMillis();
		String tulingResult = TulingRobot.getResponse(user, content);
		
		 
		logger.warn("调用图灵接口完毕,耗时"+(System.currentTimeMillis()-t)+"毫秒,data:" + tulingResult);
		return tulingResult;
	}
	
	
	private static String matchScriptSearch(String content){
		 
		Pattern p=Pattern.compile(ConfigManager.getInstance().getStringValue("EXCUTE_SCRIPT_PATTERN"));  
		Matcher m=p.matcher(content);  
		while(m.find()){  
		   return m.group(1);
		}  
		return content;
	}
	
	public static void main(String[] ags){
		System.out.println(matchScriptSearch("我是小白，我就打你"));
	}
}
