/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */
package com.pirobot.framework.web;
import org.springframework.core.convert.converter.Converter;


 
public class EmptyStringToNullConverter implements  Converter<String,String>  {

	@Override
	public String convert(String arg) {
		if(arg!=null && arg.length()==0){
			return null;
		}
		return arg;
	}

  
}
