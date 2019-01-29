package com.pirobot.cmp.admin.controller;



import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pirobot.cmp.api.result.BaseResult;
import com.pirobot.cmp.api.result.YunsouDoc;
import com.pirobot.cmp.api.result.YunsouModel;
import com.pirobot.cmp.util.Constants;
import com.pirobot.cmp.util.QAProcesser;
import com.pirobot.framework.web.Page;

@Controller    
@RequestMapping("/console/yunsou") 
public class YunsouController  
{

      
      @RequestMapping(value="/add.action",method=RequestMethod.POST)  
  	  public @ResponseBody BaseResult add(String question,String answer) {
    	
    	BaseResult result = new BaseResult();
    	QAProcesser.getInstance().addQA(question, answer);
  	    return result;

  	}
  	

  	 @RequestMapping(value="/list.action")  
  	  public ModelAndView list(YunsouModel model ,Page page) {  
  		
	    if(StringUtils.isNotBlank(model.question))
	    {
	    	QAProcesser.getInstance().searchQA(model.question);
	    }
	    else
	    {
	  		QAProcesser.getInstance().listQA(page.getCountPage()-1, page.pageSize);
	    }
	    //TODO
//        List<YunsouModel> modelList = new ArrayList<>();
//        for(int i = 0; i < yunsouResult.getDataList().size(); i++)
//    	{
//        	YunsouDoc doc = yunsouResult.getDataList().get(i);
//    		com.qcloud.Utilities.Json.JSONObject docMetaJson = new com.qcloud.Utilities.Json.JSONObject(doc.doc_meta);
//    		YunsouModel model = new YunsouModel();
//    		model.question = docMetaJson.getString("question");;
//    		model.answer = docMetaJson.getString("answer");
//    		model.id = docMetaJson.getLong("id");
//    		model.discreted = docMetaJson.getString("discretedQuestion90");
//    		
//    		modelList.add(model);
//    	}
//        page.setCount(yunsouResult.getCount());
//        page.setDataList(modelList);
        
  		ModelAndView modelView = new ModelAndView();
  		modelView.setViewName("yunsou/manage");
  	    modelView.addObject("page", page);
  	    modelView.addObject("model", model);
  	    return modelView;
  		  
  	}
        
 
  	 @RequestMapping(value="/delete.action",method=RequestMethod.POST)  
 	  public @ResponseBody BaseResult delete(long id) {
  		  BaseResult result = new BaseResult();  		 
  		  QAProcesser.getInstance().delQA(id + "");
  		  return result;
  	 }
}