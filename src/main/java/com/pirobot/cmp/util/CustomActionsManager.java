package com.pirobot.cmp.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONObject;

public class CustomActionsManager {
	protected final Log logger = LogFactory.getLog(CustomActionsManager.class);
	private Map<String, String> wordActionMap = new HashMap<String, String>();
	private static CustomActionsManager customActionsManager = null;
	private boolean lastIsAction = false;
	private CustomActionsManager(){}
	
	public static CustomActionsManager getInstance()
	{
		if(customActionsManager == null)
			customActionsManager = new CustomActionsManager();
		return customActionsManager;
	}
	
	public boolean importRule(String ruleFileName)
	{
		try
		{
			InputStream fis =getClass().getClassLoader().getResourceAsStream(ruleFileName);
	        SAXReader saxReader = new SAXReader();  
            //生成文档对应实体  
            Document doc = saxReader.read(fis);  
            //获取指定路径下的元素列表,这里指获取所有的Rules下的OneRule元素  
            List elementRuleList = doc.selectNodes("//Rules/Actions");  

	        for(Iterator iter = elementRuleList.iterator(); iter.hasNext(); ){ 
	            Element actionsElement = (Element)iter.next();  
	            String matchedInfo = actionsElement.attributeValue("matchedInfo");
	            String[] matchedWords = matchedInfo.split("#");
	            for (String word : matchedWords) {
	            	wordActionMap.put(word, actionsElement.asXML());
				}
	        }  
		}catch(Exception e){
			e.printStackTrace();
			//Log.e("Error", "Fail to import rule:" + e.getMessage());
			return false;
		}
		return true;
	}
	public void initRuleSamentic()
	{
	}
	public String getMatchedRule(String sentence)
	{
		String ruleString = "";
		String tmpString = sentence.replaceAll("，|。|？|、", "");
		if(wordActionMap.containsKey(tmpString))
		{
			return wordActionMap.get(tmpString);
		}
		List<String> keywordList = new ArrayList<String>(wordActionMap.keySet());
		JSONObject retObj = JSONObject.parseObject(QAProcesser.getInstance().compareSentences(sentence, keywordList));
		String[] samenticSimiliarities = retObj.getString("samenticSimiliarity").split(",");
		String[] pronounceSimiliarities = retObj.getString("pronounceSimiliarity").split(",");
		int len = samenticSimiliarities.length;
		double maxPronounceSimiliatiry = 0.0f;
		double maxSamenticSimiliarity = 0.0f;
		double pronounceSimiliarity = 0.0f;
		double samenticSimiliarity = 0.0f;
		String matchedKey = "";
		for(int i = 0; i < len; i++)
		{
			String keyword = keywordList.get(i);
			pronounceSimiliarity = Double.parseDouble(pronounceSimiliarities[i]);
			samenticSimiliarity = Double.parseDouble(samenticSimiliarities[i]);
			if(lastIsAction && pronounceSimiliarity > 0.8f)
				pronounceSimiliarity = 1.0f;
			if(Math.abs(pronounceSimiliarity - 1.0f) < 0.00001f || Math.abs(samenticSimiliarity - 1.0f) < 0.00001f)
			{
				logger.info(sentence + "<->" + keyword + ":" + pronounceSimiliarity + "__" + samenticSimiliarity);
				lastIsAction = true;
				return wordActionMap.get(keyword);
			}
			if(pronounceSimiliarity < 0.8f && samenticSimiliarity < 0.9f)
				continue;
			if(pronounceSimiliarity + samenticSimiliarity > maxPronounceSimiliatiry + maxSamenticSimiliarity)
			{
				maxPronounceSimiliatiry = pronounceSimiliarity;
				maxSamenticSimiliarity = samenticSimiliarity;
				ruleString = wordActionMap.get(keyword);
				matchedKey = keyword;
			}
		}
		
		lastIsAction = !ruleString.isEmpty();
		if(!matchedKey.isEmpty())
			logger.info(sentence + "<->" + matchedKey + ":" + maxPronounceSimiliatiry + "__" + maxSamenticSimiliarity);
		return ruleString;
	}
	
	public static void main(String[] args)
	{
		CustomActionsManager.getInstance().importRule("customactions.xml");
		System.out.println("匹配结果" + CustomActionsManager.getInstance().getMatchedRule("前进"));
	}
}
