/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.cmp.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pirobot.cmp.api.result.BaseResult;
import com.pirobot.cmp.model.Wifi;
import com.pirobot.cmp.service.WifiService;
@Controller    
@RequestMapping("/cgi/wifi")  
public class WifiController  {
 
	@Autowired
	private WifiService wifiServiceImpl;

	 
	
	@RequestMapping(value="/save.api")  
	public @ResponseBody BaseResult save(@RequestBody List<Wifi> dataList) {
		BaseResult result = new BaseResult();	
		if(dataList!=null && !dataList.isEmpty()){
			wifiServiceImpl.save(dataList);
		}
		return result;
	}

	@RequestMapping(value="/list.api")  
	public @ResponseBody BaseResult list(String deviceId){
		BaseResult result = new BaseResult();	
		result.dataList = wifiServiceImpl.get(deviceId);
		return result;
	 }

}
