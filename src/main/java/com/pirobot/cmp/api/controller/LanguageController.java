/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.cmp.api.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.pirobot.cmp.api.result.SimilarityResult;
import com.pirobot.cmp.util.QAProcesser;
import com.pirobot.cmp.util.StringUtil;

@Controller    
@RequestMapping("/cgi/language") 
public class LanguageController  
{
      
      @RequestMapping(value="/semantic/compare.api")  
  	  public @ResponseBody SimilarityResult compare(String answer,String criterion){

  		SimilarityResult result = new SimilarityResult();
  		String answerTxt = StringUtil.convertNumInStr(answer);
		String criterionTxt = StringUtil.convertNumInStr(criterion);
		
		List<String> sentenceList = new ArrayList<String>();
		sentenceList.add(criterionTxt);
		JSONObject retObj = JSONObject.parseObject(QAProcesser.getInstance().compareSentences(answerTxt, sentenceList));
		
		result.pinyin = Double.parseDouble(retObj.getString("pronounceSimiliarity"));
		result.samentic = Double.parseDouble(retObj.getString("samenticSimiliarity"));
	
        return result;
  	}
  	
     
}